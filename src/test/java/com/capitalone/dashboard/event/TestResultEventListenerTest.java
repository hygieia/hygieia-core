package com.capitalone.dashboard.event;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Performance;
import com.capitalone.dashboard.model.PerformanceType;
import com.capitalone.dashboard.model.TestCapability;
import com.capitalone.dashboard.model.TestCase;
import com.capitalone.dashboard.model.TestCaseStatus;
import com.capitalone.dashboard.model.TestCaseStep;
import com.capitalone.dashboard.model.TestResult;
import com.capitalone.dashboard.model.TestSuite;
import com.capitalone.dashboard.model.TestSuiteType;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.PerformanceRepository;

@ExtendWith(MockitoExtension.class)
public class TestResultEventListenerTest {

	// Mockito.mock is required rather than previous iteration @Mock of parameter to assure Interface is mocked
    private CollectorRepository collectorRepository = Mockito.mock(CollectorRepository.class);;
    
    private CollectorItemRepository collectorItemRepository = Mockito.mock(CollectorItemRepository.class);
    
    private PerformanceRepository performanceRepository = Mockito.mock(PerformanceRepository.class);
    
    private TestResultEventListener testResultEventListener = Mockito.mock(TestResultEventListener.class);
    
    private static final String COLLECTOR_NAME = "PerfTools";


    @Test
    public void test_testResultListener(){
        TestResult testResult = getTestResult(getTestResultCollectorItem());
        CollectorItem perfCollItem = getNewPerfCollectorItem(getPerfToolsCollector());
        when(collectorItemRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(getTestResultCollectorItem()));
        when(collectorRepository.save(any(Collector.class))).thenReturn(getPerfToolsCollector());
        when(collectorItemRepository.save(any(CollectorItem.class))).thenReturn(getNewPerfCollectorItem(getPerfToolsCollector()));
        when(performanceRepository.save(any(Performance.class))).thenReturn(getPerformanceDoc());
   //     setupData();
        doNothing().when(testResultEventListener).onAfterSave(new AfterSaveEvent<>(testResult, null, ""));
        Performance performance = new Performance();
        performance.setUrl(null);
        performance.setType(PerformanceType.ApplicationPerformance);
        when(testResultEventListener.createPerformanceDoc(testResult,
                getTestCapabilities().iterator().next(), perfCollItem)).thenReturn(performance);
        verify(performanceRepository, never()).save(performance);
        assertThat(performance.getUrl(), is(testResult.getUrl()));
        assertThat(performance.getType(), is(PerformanceType.ApplicationPerformance));
    }

    private CollectorItem getTestResultCollectorItem() {
        CollectorItem collectorItem = new CollectorItem();
        collectorItem.setId(ObjectId.get());
        collectorItem.setCollector(new Collector("Test Collector", CollectorType.Test));
        collectorItem.setNiceName("Test Collector");
        collectorItem.getOptions().put("jobName", "TestResultEventListenerTest");
        return collectorItem;
    }

    private TestResult getTestResult(CollectorItem collectorItem) {
        TestResult testResult = new TestResult();
        testResult.setId(ObjectId.get());
        testResult.setType(TestSuiteType.Performance);
        testResult.setCollectorItemId(collectorItem.getId());
        testResult.setTestCapabilities(getTestCapabilities());
        return testResult;
    }

    private Collector getPerfToolsCollector() {
        Collector collector = new Collector(COLLECTOR_NAME, CollectorType.AppPerformance);
        collector.setId(ObjectId.get());
        return collector;
    }

    private void setupData() {
        when(collectorItemRepository.findById(any(ObjectId.class))).thenReturn(Optional.of(getTestResultCollectorItem()));
        when(collectorRepository.save(any(Collector.class))).thenReturn(getPerfToolsCollector());
        when(collectorItemRepository.save(any(CollectorItem.class))).thenReturn(getNewPerfCollectorItem(getPerfToolsCollector()));
        when(performanceRepository.save(any(Performance.class))).thenReturn(getPerformanceDoc());
    }

    public CollectorItem getNewPerfCollectorItem(Collector perfToolsCollector) {
        CollectorItem newPerfCollItem = new CollectorItem();
        newPerfCollItem.setId(ObjectId.get());
        newPerfCollItem.setCollector(perfToolsCollector);
        newPerfCollItem.setCollectorId(perfToolsCollector.getId());
        return newPerfCollItem;
    }

    public Collection<TestCapability> getTestCapabilities() {
        TestCapability testCapability = new TestCapability();
        testCapability.setDescription("TestResultEventListenerTest");
        testCapability.setDuration(1L);

        TestSuite suite = new TestSuite();
        suite.setDescription("TestResultEventListenerTest");
        suite.setDuration(1L);
        suite.setType(TestSuiteType.Performance);

        List<TestCase> testCases = new ArrayList<>();
        TestCase testCaseAvgRespTime = new TestCase();
        testCaseAvgRespTime.setDescription("KPI : Avg response times");
        testCaseAvgRespTime.setStatus(TestCaseStatus.Success);
        List<TestCaseStep> testCaseStepsAvgRespTime = new ArrayList<>();
        TestCaseStep testCaseStepTargetRespTime = new TestCaseStep();
        TestCaseStep testCaseStepActualRespTime = new TestCaseStep();
        testCaseStepTargetRespTime.setId("Target Response Time");
        testCaseStepTargetRespTime.setDescription("100.0");
        testCaseStepActualRespTime.setId("Actual Response Time");
        testCaseStepActualRespTime.setDescription("72.41");
        testCaseStepsAvgRespTime.add(testCaseStepTargetRespTime);
        testCaseStepsAvgRespTime.add(testCaseStepActualRespTime);
        testCaseAvgRespTime.setTestSteps(testCaseStepsAvgRespTime);
        testCases.add(testCaseAvgRespTime);

        TestCase testCaseTxnPerSec = new TestCase();
        testCaseTxnPerSec.setDescription("KPI : Transaction Per Second");
        testCaseTxnPerSec.setStatus(TestCaseStatus.Failure);
        List<TestCaseStep> testCaseStepsTxnRespTime = new ArrayList<>();
        TestCaseStep testCaseStepTargetTxnPerSec = new TestCaseStep();
        TestCaseStep testCaseStepActualTxnPerSec = new TestCaseStep();
        testCaseStepTargetTxnPerSec.setId("Target Transactions per sec");
        testCaseStepTargetTxnPerSec.setDescription("33.0");
        testCaseStepActualTxnPerSec.setId("Actual Transactions per sec");
        testCaseStepActualTxnPerSec.setDescription("4.75");
        testCaseStepsTxnRespTime.add(testCaseStepTargetTxnPerSec);
        testCaseStepsTxnRespTime.add(testCaseStepActualTxnPerSec);
        testCaseTxnPerSec.setTestSteps(testCaseStepsTxnRespTime);
        testCases.add(testCaseTxnPerSec);

        TestCase testCaseErrRate = new TestCase();
        testCaseErrRate.setDescription("KPI : Error Rate Threshold");
        testCaseErrRate.setStatus(TestCaseStatus.Success);
        List<TestCaseStep> testCaseStepsErrRate = new ArrayList<>();
        TestCaseStep testCaseStepTargetErrRate = new TestCaseStep();
        TestCaseStep testCaseStepActualErrRate = new TestCaseStep();
        testCaseStepTargetErrRate.setId("Target Error Rate Threshold");
        testCaseStepTargetErrRate.setDescription("1.0");
        testCaseStepActualErrRate.setId("Actual Error Rate");
        testCaseStepActualErrRate.setDescription("0.0");
        testCaseStepsErrRate.add(testCaseStepTargetErrRate);
        testCaseStepsErrRate.add(testCaseStepActualErrRate);
        testCaseErrRate.setTestSteps(testCaseStepsErrRate);
        testCases.add(testCaseErrRate);

        suite.setTestCases(testCases);
        testCapability.getTestSuites().add(suite);

        List<TestCapability> testCapabilities = new ArrayList<>();
        testCapabilities.add(testCapability);
        return testCapabilities;
    }

    public Performance getPerformanceDoc() {
        Performance performance = new Performance();
        performance.setType(PerformanceType.ApplicationPerformance);
        performance.setUrl(getTestResult(getTestResultCollectorItem()).getUrl());
        return performance;
    }
}