package com.capitalone.dashboard.event;

import com.capitalone.dashboard.model.TestResult;
import com.capitalone.dashboard.model.TestCapability;
import com.capitalone.dashboard.model.TestSuiteType;
import com.capitalone.dashboard.model.TestCase;
import com.capitalone.dashboard.model.TestCaseStatus;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Performance;
import com.capitalone.dashboard.model.PerformanceType;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.PerformanceRepository;
import org.apache.commons.collections.CollectionUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;

@Component
public class TestResultEventListener extends AbstractMongoEventListener<TestResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestResultEventListener.class);

    private static final String STR_RESP_TIME_THRESHOLD = "KPI : Avg response times";
    private static final String STR_ACTUAL_RESP_TIME = "Actual Response Time";
    private static final String STR_TARGET_RESP_TIME = "Target Response Time";
    private static final String STR_TXN_PER_SEC_THRESHOLD = "KPI : Transaction Per Second";
    private static final String STR_ACTUAL_TXN_PER_SEC = "Actual Transactions per sec";
    private static final String STR_TARGET_TXN_PER_SEC = "Target Transactions per sec";
    private static final String STR_ERROR_RATE_THRESHOLD = "KPI : Error Rate Threshold";
    private static final String STR_ACTUAL_ERROR_RATE = "Actual Error Rate";
    private static final String STR_TARGET_ERROR_RATE = "Target Error Rate";
    private static final String STR_CRITICAL = "CRITICAL";
    private static final String STR_OPEN = "OPEN";
    private static final String COLLECTOR_NAME = "PerfTools";
    private static final int SIXTY_SECS = 60;
    private static double HEALTH_GOOD = 1.0;
    // 1.0 is good health, value less than 1.0 considered bad
    private static double HEALTH_CRITICAL = 0.98;
    private static long SEVERITY_CRITICAL = 2;
    private static long SEVERITY_GOOD = 0;

    private static double targetRespTime;
    private static double actualRespTime;
    private static double targetTxnsPerSec;
    private static double actualTxnsPerSec;
    private static double targetErrorRate;
    private static double actualErrorsVal;
    private static double txnHealthPercentVal;
    private static long errRateSeverityVal;
    private static long respTimeSeverityVal;
    private static boolean isResponseTimeGood = false;
    private static boolean isTxnGoodHealth = false;
    private static boolean isErrorRateGood = false;

    private final PerformanceRepository performanceRepository;
    private final CollectorRepository collectorRepository;
    private final CollectorItemRepository collectorItemRepository;

    private enum PERFORMANCE_METRICS {
        averageResponseTime,totalCalls,errorsperMinute,businessTransactionHealthPercent,nodeHealthPercent,violationObject,
        totalErrors,errorRateSeverity,responseTimeSeverity,callsperMinute,targetResponseTime,targetTransactionPerSec,
        targetErrorRateThreshold
    }
    private enum VIOLATION_ATTRIBUTES {
        severity,incidentStatus
    }

    @Autowired
    public TestResultEventListener(CollectorRepository collectorRepository, CollectorItemRepository collectorItemRepository,
                                   PerformanceRepository performanceRepository) {
        this.collectorRepository = collectorRepository;
        this.collectorItemRepository = collectorItemRepository;
        this.performanceRepository = performanceRepository;
    }

    /**
     * Create and save performance object for every test_result type is performance
     * @param event
     */
    @Override
    public void onAfterSave(AfterSaveEvent<TestResult> event) {
        TestResult testResult = event.getSource();
        LOGGER.info("TestResult save event triggered");

        // Ignore anything other than performance tests
        if (!TestSuiteType.Performance.equals(testResult.getType())) {
            return;
        }

        List<TestCapability> testCapabilities = new ArrayList<>(testResult.getTestCapabilities());
        if (CollectionUtils.isEmpty(testCapabilities)){
            return;
        }
        testCapabilities.sort(Comparator.comparing(TestCapability::getTimestamp).reversed());
        TestCapability lastExecutedTestCapabiblity = testCapabilities.iterator().next();
        lastExecutedTestCapabiblity.getTestSuites().forEach(testSuite -> testSuite.getTestCases().forEach(testCase ->
                readPerformanceMetrics(testCase, lastExecutedTestCapabiblity)));

        CollectorItem perfCollectorItem = getPerfCollectorItem(testResult);
        createPerformanceDoc(testResult, lastExecutedTestCapabiblity, perfCollectorItem);
        LOGGER.info("New performance document created from test_result successfully");
        }

    /**
     * Creates new performance object from test result
     * @params testResult, testCapability, perfCollectorItem
     */
    public Performance createPerformanceDoc(TestResult testResult, TestCapability testCapability, CollectorItem perfCollectorItem) {
        Performance performance = new Performance();
        performance.setId(ObjectId.get());
        performance.setCollectorItemId(perfCollectorItem.getId());
        performance.setType(PerformanceType.ApplicationPerformance);
        performance.setUrl(testResult.getUrl());
        performance.setTimestamp(System.currentTimeMillis());
        performance.setTargetAppName(testResult.getTargetAppName());
        performance.setTargetEnvName(testResult.getTargetEnvName());
        performance.setMetrics(getPerfMetrics(testCapability));
        return performanceRepository.save(performance);
    }
    /**
     * Creates new performance collector item
     * @params testResult
     */
    public CollectorItem getPerfCollectorItem(TestResult testResult) {
        CollectorItem collectorItem = new CollectorItem();
        Collector collector = getPerfToolsCollector();
        collectorItem.setId(ObjectId.get());
        collectorItem.setCollectorId(collector.getId());
        collectorItem.setCollector(collector);
        collectorItem.setLastUpdated(System.currentTimeMillis());
        collectorItem.setEnabled(true);
        collectorItem.setPushed(true);
        collectorItem.setDescription(testResult.getDescription());
        return collectorItemRepository.save(collectorItem);
    }

    /**
     * Creates collector if not exists already
     */
    public Collector getPerfToolsCollector() {
        Collector collector = collectorRepository.findByName(COLLECTOR_NAME);
        if (collector != null) {
            collector.setLastExecuted(System.currentTimeMillis());
        }else {
            collector = new Collector(COLLECTOR_NAME, CollectorType.AppPerformance);
            collector.setId(ObjectId.get());
            collector.setEnabled(true);
            collector.setOnline(true);
            collector.setLastExecuted(System.currentTimeMillis());
        }
        return collectorRepository.save(collector);
    }

    /**
     * Get performance test metrics
     * @params testCapability
     */
    public LinkedHashMap<String,Object> getPerfMetrics(TestCapability testCapability) {

        LinkedHashMap<String,Object> metrics = new LinkedHashMap<>();
        long testCapabilitySecs = TimeUnit.MILLISECONDS.toSeconds(testCapability.getDuration());
        // If test execution duration less than a minute, calculate calls per minute from actual test execution seconds
        double callsPerMinuteVal = testCapabilitySecs > SIXTY_SECS ? actualTxnsPerSec * SIXTY_SECS : actualTxnsPerSec * testCapabilitySecs;
        // calculate errors per second from total number of errors of a test execution
        double actualErrorsPerSec = actualErrorsVal/testCapabilitySecs;
        double errorsPerMinuteVal = testCapabilitySecs > SIXTY_SECS ? actualErrorsPerSec * SIXTY_SECS : actualErrorsPerSec * testCapabilitySecs;
        double totalCallsVal = actualTxnsPerSec * testCapabilitySecs;
        txnHealthPercentVal = isTxnGoodHealth ? HEALTH_GOOD : HEALTH_CRITICAL;
        respTimeSeverityVal = isResponseTimeGood ? SEVERITY_GOOD : SEVERITY_CRITICAL;
        errRateSeverityVal = isErrorRateGood ? SEVERITY_GOOD : SEVERITY_CRITICAL;

        metrics.put(PERFORMANCE_METRICS.averageResponseTime.name(), Math.round(actualRespTime));
        metrics.put(PERFORMANCE_METRICS.callsperMinute.name(), Math.round(callsPerMinuteVal));
        metrics.put(PERFORMANCE_METRICS.errorsperMinute.name(), Math.round(errorsPerMinuteVal));
        metrics.put(PERFORMANCE_METRICS.totalCalls.name(), Math.round(totalCallsVal));
        metrics.put(PERFORMANCE_METRICS.totalErrors.name(), Math.round(actualErrorsVal));
        metrics.put(PERFORMANCE_METRICS.businessTransactionHealthPercent.name(), txnHealthPercentVal);
        metrics.put(PERFORMANCE_METRICS.nodeHealthPercent.name(), HEALTH_GOOD);
        metrics.put(PERFORMANCE_METRICS.errorRateSeverity.name(), errRateSeverityVal);
        metrics.put(PERFORMANCE_METRICS.responseTimeSeverity.name(), respTimeSeverityVal);
        metrics.put(PERFORMANCE_METRICS.violationObject.name(), getPerfTestViolation());

        metrics.put(PERFORMANCE_METRICS.targetResponseTime.name(), targetRespTime);
        metrics.put(PERFORMANCE_METRICS.targetTransactionPerSec.name(), targetTxnsPerSec);
        metrics.put(PERFORMANCE_METRICS.targetErrorRateThreshold.name(), targetErrorRate);
        return metrics;
    }

    /**
     * Get performance test violation details
     */
    public LinkedHashMap<Object, Object> getPerfTestViolation() {
        LinkedHashMap<Object, Object> violationObjMap = new LinkedHashMap<>();
        if (!(isResponseTimeGood && isTxnGoodHealth && isErrorRateGood)){
            violationObjMap.put(VIOLATION_ATTRIBUTES.severity, STR_CRITICAL);
            violationObjMap.put(VIOLATION_ATTRIBUTES.incidentStatus, STR_OPEN);
        }
        return violationObjMap;
    }

    /**
     * Reads the test result threshold values to build performance metrics object
     * @params testCase, testCapability
     */
    private void readPerformanceMetrics(TestCase testCase, TestCapability testCapability) {

        long testCapabilityDurationSecs = TimeUnit.MILLISECONDS.toSeconds(testCapability.getDuration());
        if(testCase.getDescription().equalsIgnoreCase(STR_RESP_TIME_THRESHOLD)){
            isResponseTimeGood = testCase.getStatus().name().equalsIgnoreCase(TestCaseStatus.Success.name());
            testCase.getTestSteps().forEach(testCaseStep -> {
                if (testCaseStep.getId().equalsIgnoreCase(STR_TARGET_RESP_TIME)){
                    targetRespTime = new Double(Double.valueOf(testCaseStep.getDescription()));
                }
                if (testCaseStep.getId().equalsIgnoreCase(STR_ACTUAL_RESP_TIME)){
                    actualRespTime = new Double(Double.valueOf(testCaseStep.getDescription()));
                }
            });
        }
        if(testCase.getDescription().equalsIgnoreCase(STR_TXN_PER_SEC_THRESHOLD)){
            isTxnGoodHealth = testCase.getStatus().name().equalsIgnoreCase(TestCaseStatus.Success.name());
            testCase.getTestSteps().forEach(testCaseStep -> {
                if (testCaseStep.getId().equalsIgnoreCase(STR_TARGET_TXN_PER_SEC)) {
                    targetTxnsPerSec = new Double(Double.valueOf(testCaseStep.getDescription()));
                }
                if (testCaseStep.getId().equalsIgnoreCase(STR_ACTUAL_TXN_PER_SEC)) {
                    actualTxnsPerSec = new Double(Double.valueOf(testCaseStep.getDescription()));
                }
            });
        }
        if(testCase.getDescription().equalsIgnoreCase(STR_ERROR_RATE_THRESHOLD)){
            isErrorRateGood = testCase.getStatus().name().equalsIgnoreCase(TestCaseStatus.Success.name());
            testCase.getTestSteps().forEach(testCaseStep -> {
                if (testCaseStep.getId().contains(STR_TARGET_ERROR_RATE)){
                    targetErrorRate = new Double(Double.valueOf(testCaseStep.getDescription()));
                }
                if (testCaseStep.getId().equalsIgnoreCase(STR_ACTUAL_ERROR_RATE)){
                    double actualErrorRate = new Double(Double.valueOf(testCaseStep.getDescription()));
                    // Error rate is percentage of actual errors in total calls
                    actualErrorsVal =  (actualErrorRate / 100) * (testCapabilityDurationSecs * actualTxnsPerSec);
                }
            });
        }
    }
}