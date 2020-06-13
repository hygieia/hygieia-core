package hygieia.transformer;

import com.capitalone.dashboard.model.TestCapability;
import com.capitalone.dashboard.model.TestCase;
import com.capitalone.dashboard.model.TestCaseStatus;
import com.capitalone.dashboard.model.TestSuite;
import com.capitalone.dashboard.model.TestSuiteType;
import com.capitalone.dashboard.model.quality.JunitXmlReport;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class JunitXmlToTestCapabilityTransformer {


    private TestSuite parseFeatureAsTestSuite(JunitXmlReport testJunit) {
        TestSuite suite = new TestSuite();
        suite.setType(TestSuiteType.Unit);
        suite.setDescription(testJunit.getName());

        long duration = 0;

        int testCaseTotalCount = testJunit.getTestcase().size();
        int testCaseSkippedCount = 0, testCaseSuccessCount = 0, testCaseFailCount = 0, testCaseUnknownCount = 0;

        for (JunitXmlReport.Testcase testCase : testJunit.getTestcase()) {
           TestCase testCaseJunit = parseScenarioAsTestCase(testCase);
            duration += testCase.getTime().longValue();

            switch (testCaseJunit.getStatus()) {
                case Success:
                    testCaseSuccessCount++;
                    break;
                case Failure:
                    testCaseFailCount++;
                    break;
                case Skipped:
                    testCaseSkippedCount++;
                    break;
                default:
                    testCaseUnknownCount++;
                    break;
            }

            suite.getTestCases().add(testCaseJunit);
        }
        suite.setSuccessTestCaseCount(testCaseSuccessCount);
        suite.setFailedTestCaseCount(testCaseFailCount);
        suite.setSkippedTestCaseCount(testCaseSkippedCount);
        suite.setTotalTestCaseCount(testCaseTotalCount);
        suite.setUnknownStatusCount(testCaseUnknownCount);
        suite.setDuration(duration);

        if (testCaseFailCount > 0) {
            suite.setStatus(TestCaseStatus.Failure);
        } else if (testCaseSkippedCount > 0) {
            suite.setStatus(TestCaseStatus.Skipped);
        } else if (testCaseSuccessCount > 0) {
            suite.setStatus(TestCaseStatus.Success);
        } else {
            suite.setStatus(TestCaseStatus.Unknown);
        }
        return suite;
    }


    public TestCapability convert(JunitXmlReport testJunit) {
        List<TestSuite> testSuites = new ArrayList<>();
            testSuites.add(this.parseFeatureAsTestSuite(testJunit));
        return this.processTestSuites(testSuites);
    }

    private TestCase parseScenarioAsTestCase(JunitXmlReport.Testcase scenarioElement) {
        TestCase testCase = new TestCase();
        testCase.setDescription( scenarioElement.getName());
        // Parse each step as a TestCase
        int testStepSuccessCount = 0, testStepFailCount = 0, testStepSkippedCount = 0, testStepUnknownCount = 0;
        long testDuration=0;

              testDuration += scenarioElement.getTime().longValue();
        if(scenarioElement.getError() != null){
            testStepFailCount++;
        }else if (StringUtils.isNotBlank(scenarioElement.getSkipped()) && "0".equals(scenarioElement.getTime())){
            testStepSkippedCount++;
        }else if(scenarioElement.getTime().doubleValue() > 0){
            testStepSuccessCount++;
        }else {
            testStepUnknownCount++;
        }
        // Set Duration
        testCase.setDuration(testDuration);
        testCase.setSuccessTestStepCount(testStepSuccessCount);
        testCase.setSkippedTestStepCount(testStepSkippedCount);
        testCase.setFailedTestStepCount(testStepFailCount);
        testCase.setUnknownStatusCount(testStepUnknownCount);
        testCase.setTotalTestStepCount(testCase.getTestSteps().size());
        // Set Status
        if (testStepFailCount > 0) {
            testCase.setStatus(TestCaseStatus.Failure);
        } else if (testStepSkippedCount > 0) {
            testCase.setStatus(TestCaseStatus.Skipped);
        } else if (testStepSuccessCount > 0) {
            testCase.setStatus(TestCaseStatus.Success);
        } else {
            testCase.setStatus(TestCaseStatus.Unknown);
        }
        return testCase;
    }


    private TestCapability processTestSuites(List<TestSuite> testSuites){
        TestCapability cap = new TestCapability();
        cap.setType(TestSuiteType.Unit);

        cap.getTestSuites().addAll(testSuites);
        //add test suites
        long duration = 0;
        int testSuiteSkippedCount = 0, testSuiteSuccessCount = 0, testSuiteFailCount = 0, testSuiteUnknownCount = 0;
        for (TestSuite t : testSuites) {
            duration += t.getDuration();
            switch (t.getStatus()) {
                case Success:
                    testSuiteSuccessCount++;
                    break;
                case Failure:
                    testSuiteFailCount++;
                    break;
                case Skipped:
                    testSuiteSkippedCount++;
                    break;
                default:
                    testSuiteUnknownCount++;
                    break;
            }
        }
        if (testSuiteFailCount > 0) {
            cap.setStatus(TestCaseStatus.Failure);
        } else if (testSuiteSkippedCount > 0) {
            cap.setStatus(TestCaseStatus.Skipped);
        } else if (testSuiteSuccessCount > 0) {
            cap.setStatus(TestCaseStatus.Success);
        } else {
            cap.setStatus(TestCaseStatus.Unknown);
        }
        cap.setFailedTestSuiteCount(testSuiteFailCount);
        cap.setSkippedTestSuiteCount(testSuiteSkippedCount);
        cap.setSuccessTestSuiteCount(testSuiteSuccessCount);
        cap.setUnknownStatusTestSuiteCount(testSuiteUnknownCount);
        cap.setTotalTestSuiteCount(testSuites.size());
        cap.setDuration(duration);
        return cap;
    }

}

