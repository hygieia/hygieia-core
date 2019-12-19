
package hygieia.transformer;

import com.capitalone.dashboard.model.TestCapability;
import com.capitalone.dashboard.model.TestSuite;
import com.capitalone.dashboard.model.quality.JunitXmlReport;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.FileReader;
import java.net.URL;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

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


        assertThat(capability.getSuccessTestSuiteCount(), is(equalTo(1)));
        assertThat(capability.getFailedTestSuiteCount(), is(equalTo(0)));
        assertThat(capability.getTestSuites().size(), is(equalTo(1)));
        TestSuite suite = capability.getTestSuites().iterator().next();
        assertThat(suite.getTestCases().size(), is(equalTo(4)));
    }

}

