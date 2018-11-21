package com.capitalone.dashboard.event;

import com.capitalone.dashboard.model.TestResult;
import com.capitalone.dashboard.model.TestCapability;
import com.capitalone.dashboard.model.TestSuiteType;
import com.capitalone.dashboard.model.TestCase;
import com.capitalone.dashboard.model.TestCaseStatus;
import com.capitalone.dashboard.model.TestCaseStep;
import com.capitalone.dashboard.model.TestSuite;
import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.CollectorItem;
import com.capitalone.dashboard.model.CollectorType;
import com.capitalone.dashboard.model.Performance;
import com.capitalone.dashboard.model.PerformanceType;
import com.capitalone.dashboard.repository.CollectorItemRepository;
import com.capitalone.dashboard.repository.CollectorRepository;
import com.capitalone.dashboard.repository.PerformanceRepository;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestResultEventListenerTest {

    @Mock
    private CollectorRepository collectorRepository;
    @Mock
    private CollectorItemRepository collectorItemRepository;
    @Mock
    private PerformanceRepository performanceRepository;
    @InjectMocks
    private TestResultEventListener testResultEventListener;
    private static final String COLLECTOR_NAME = "PerfTools";


    @Test
    public void test_testResultListener(){
        TestResult testResult = getTestResult(getTestResultCollectorItem());
        CollectorItem perfCItem = getNewPerfCollectorItem(getPerfToolsCollector());
        setupData();
        testResultEventListener.onAfterSave(new AfterSaveEvent<>(testResult, null, ""));
        Performance performance = testResultEventListener.createPerformanceDoc(testResult,
                getTestCapabilities().iterator().next(), perfCItem);
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
        when(collectorItemRepository.findOne(Matchers.any(ObjectId.class))).thenReturn(getTestResultCollectorItem());
        when(collectorRepository.save(Matchers.any(Collector.class))).thenReturn(getPerfToolsCollector());
        when(collectorItemRepository.save(Matchers.any(CollectorItem.class))).thenReturn(getNewPerfCollectorItem(getPerfToolsCollector()));
        when(performanceRepository.save(Matchers.any(Performance.class))).thenReturn(getPerformanceDoc());
    }

    public CollectorItem getNewPerfCollectorItem(Collector perfToolsCollector) {
        CollectorItem newPerfCItem = new CollectorItem();
        newPerfCItem.setId(ObjectId.get());
        newPerfCItem.setCollector(perfToolsCollector);
        newPerfCItem.setCollectorId(perfToolsCollector.getId());
        return newPerfCItem;
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
        TestCase testCase1 = new TestCase();
        testCase1.setDescription("KPI : Avg response times");
        testCase1.setStatus(TestCaseStatus.Success);
        List<TestCaseStep> testCaseSteps1 = new ArrayList<>();
        TestCaseStep testCaseStep1 = new TestCaseStep();
        TestCaseStep testCaseStep2 = new TestCaseStep();
        testCaseStep1.setId("Target Response Time");
        testCaseStep1.setDescription("100.0");
        testCaseStep2.setId("Actual Response Time");
        testCaseStep2.setDescription("72.41");
        testCaseSteps1.add(testCaseStep1);
        testCaseSteps1.add(testCaseStep2);
        testCase1.setTestSteps(testCaseSteps1);
        testCases.add(testCase1);

        TestCase testCase2 = new TestCase();
        testCase2.setDescription("KPI : Transaction Per Second");
        testCase2.setStatus(TestCaseStatus.Failure);
        List<TestCaseStep> testCaseSteps2 = new ArrayList<>();
        TestCaseStep testCaseStep3 = new TestCaseStep();
        TestCaseStep testCaseStep4 = new TestCaseStep();
        testCaseStep3.setId("Target Transactions per sec");
        testCaseStep3.setDescription("33.0");
        testCaseStep4.setId("Actual Transactions per sec");
        testCaseStep4.setDescription("4.75");
        testCaseSteps2.add(testCaseStep3);
        testCaseSteps2.add(testCaseStep4);
        testCase2.setTestSteps(testCaseSteps2);
        testCases.add(testCase2);

        TestCase testCase3 = new TestCase();
        testCase3.setDescription("KPI : Error Rate Threshold");
        testCase3.setStatus(TestCaseStatus.Success);
        List<TestCaseStep> testCaseSteps3 = new ArrayList<>();
        TestCaseStep testCaseStep5 = new TestCaseStep();
        TestCaseStep testCaseStep6 = new TestCaseStep();
        testCaseStep5.setId("Target Error Rate Threshold");
        testCaseStep5.setDescription("1.0");
        testCaseStep6.setId("Actual Error Rate");
        testCaseStep6.setDescription("0.0");
        testCaseSteps3.add(testCaseStep5);
        testCaseSteps3.add(testCaseStep6);
        testCase3.setTestSteps(testCaseSteps3);
        testCases.add(testCase3);

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