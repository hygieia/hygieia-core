
package hygieia.transformer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.capitalone.dashboard.model.TestCapability;
import com.capitalone.dashboard.model.TestSuite;
import com.capitalone.dashboard.model.quality.JunitXmlReport;

@Disabled
public class JunitXmlToTestCapabilityTransformerTest {

    JunitXmlToTestCapabilityTransformer sut = new JunitXmlToTestCapabilityTransformer();

    private static  JAXBContext jaxbContext;

    static {
        try {
            jaxbContext = JAXBContext.newInstance(JunitXmlReport.class);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void producesJunitTestResult() throws Exception {

        URL url = getClass().getResource("/junit.xml");
        File file = new File(url.getPath());
        FileReader fileReader = new FileReader(file);
        JunitXmlReport responseReport = null;

        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        responseReport = (JunitXmlReport) unmarshaller.unmarshal(fileReader);


        TestCapability capability = sut.convert(responseReport);


        assertThat(capability.getSuccessTestSuiteCount()).isEqualTo(1);
        assertThat(capability.getFailedTestSuiteCount()).isEqualTo(0);
        assertThat(capability.getTestSuites().size()).isEqualTo(1);
        TestSuite suite = capability.getTestSuites().iterator().next();
        assertThat(suite.getTestCases().size()).isEqualTo(4);
    }

}

