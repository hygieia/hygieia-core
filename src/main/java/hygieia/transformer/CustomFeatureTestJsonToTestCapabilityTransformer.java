package hygieia.transformer;

import com.capitalone.dashboard.model.TestCapability;
import com.capitalone.dashboard.model.TestCase;
import com.capitalone.dashboard.model.TestCaseStatus;
import com.capitalone.dashboard.model.TestSuite;
import com.capitalone.dashboard.model.TestSuiteType;
import com.capitalone.dashboard.model.quality.CustomFeatureTestReport;

import java.util.ArrayList;
import java.util.List;

/**
 * Transforms a Cucumber result JSON string into a TestResult
 */

public class CustomFeatureTestJsonToTestCapabilityTransformer {



    public CustomFeatureTestJsonToTestCapabilityTransformer() {
    }

    private TestSuite parseFeatureAsTestSuite(CustomFeatureTestReport.Feature featureElement) {
        TestSuite suite = new TestSuite();
        suite.setType(TestSuiteType.Functional);

        for (CustomFeatureTestReport.CustomFeatureTestCase customFeatureTestCase : featureElement.getTestCases()) {
            TestCase testCase = parseCustomFeatureTestCase(customFeatureTestCase);
            suite.getTestCases().add(testCase);
        }

        suite.setSuccessTestCaseCount(featureElement.getTestCasesPassedCount());
        suite.setFailedTestCaseCount(featureElement.getTestCasesFailedCount());
        suite.setSkippedTestCaseCount(featureElement.getTestCasesSkippedCount());
        suite.setTotalTestCaseCount(featureElement.getTestCasesCount());
        suite.setUnknownStatusCount(0);

        switch (featureElement.getTestRunStatus()) {
            case "PASS":
                suite.setStatus(TestCaseStatus.Success);
                break;
            case "FAIL":
                suite.setStatus(TestCaseStatus.Failure);
                break;
            default:
                suite.setStatus(TestCaseStatus.Unknown);
                break;
        }
        return suite;
    }

    private TestCase parseCustomFeatureTestCase(CustomFeatureTestReport.CustomFeatureTestCase customFeatureTestCase) {
        TestCase testCase = new TestCase();
        testCase.setId(customFeatureTestCase.getTestCaseId());

        switch (customFeatureTestCase.getStatus()) {
                case "PASS":
                    testCase.setStatus(TestCaseStatus.Success);
                    break;
                case "FAIL":
                    testCase.setStatus(TestCaseStatus.Failure);
                    break;
                default:
                    testCase.setStatus(TestCaseStatus.Unknown);
                    break;
        }

        return testCase;
    }

    public List<TestCapability> convert(CustomFeatureTestReport customFeatureTestReport) {
        List<CustomFeatureTestReport.Feature> features = customFeatureTestReport.getFeatures();
        List<TestCapability> capabilities = new ArrayList<>();

        for (CustomFeatureTestReport.Feature feature : features) {
            TestSuite testSuite = this.parseFeatureAsTestSuite(feature);
            TestCapability cap = this.processTestSuite(testSuite, feature.getTestId());
            capabilities.add(cap);
        }

         return capabilities;
    }

    private TestCapability processTestSuite(TestSuite testSuite, String testRequestId) {
        TestCapability cap = new TestCapability();
        cap.setType(TestSuiteType.Functional);
        // Report doesn't attach timestamp, so set as time Hygieia converts the test capability
        cap.setTimestamp(System.currentTimeMillis());
        cap.setExecutionId(testRequestId);
        cap.getTestSuites().add(testSuite);
        long duration = 0;

        if (testSuite.getStatus() == TestCaseStatus.Success) {
            cap.setStatus(TestCaseStatus.Success);
            cap.setFailedTestSuiteCount(0);
            cap.setSuccessTestSuiteCount(1);
        } else {
            cap.setStatus(TestCaseStatus.Failure);
            cap.setFailedTestSuiteCount(1);
            cap.setSuccessTestSuiteCount(0);
        }

        cap.setSkippedTestSuiteCount(0);
        cap.setUnknownStatusTestSuiteCount(0);
        cap.setTotalTestSuiteCount(1);
        cap.setDuration(duration);
        return cap;
    }
}

