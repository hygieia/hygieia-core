package com.capitalone.dashboard.model.quality;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "testsuites")
public class JunitXmlReportV2 implements QualityVisitee {

    @XmlElement(required = true)
    protected List<TestSuite> testsuite;

    public List<TestSuite> getTestsuite() {
        if (testsuite == null) {
            testsuite = new ArrayList<>();
        }
        return this.testsuite;
    }

    @Override
    public void accept(QualityVisitor visitor) {
        visitor.visit(this);
    }

    // TESTSUITE
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class TestSuite {

        @XmlElement(required = true)
        protected Properties properties;
        protected List<Testcase> testcase;
        @XmlElement(name = "system-out", required = true)
        protected String systemOut;
        @XmlElement(name = "system-err", required = true)
        protected String systemErr;
        @XmlAttribute(name = "name", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String name;
        @XmlAttribute(name = "timestamp", required = true)
        protected XMLGregorianCalendar timestamp;
        @XmlAttribute(name = "hostname", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        protected String hostname;
        @XmlAttribute(name = "tests", required = true)
        protected int tests;
        @XmlAttribute(name = "failures", required = true)
        protected int failures;
        @XmlAttribute(name = "errors", required = true)
        protected int errors;
        @XmlAttribute(name = "time", required = true)
        protected BigDecimal time;
        @XmlAttribute(name = "skipped")
        protected String skipped;

        @XmlAttribute(name = "skips")
        protected String skips;

        /**
         * Gets the value of the properties property.
         *
         * @return possible object is
         * {@link TestSuite.Properties }
         */
        public TestSuite.Properties getProperties() {
            return properties;
        }

        /**
         * Sets the value of the properties property.
         *
         * @param value allowed object is
         *              {@link TestSuite.Properties }
         */
        public void setProperties(TestSuite.Properties value) {
            this.properties = value;
        }

        /**
         * Gets the value of the testcase property.
         * <p>
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the testcase property.
         * <p>
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getTestcase().add(newItem);
         * </pre>
         * <p>
         * <p>
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link TestSuite.Testcase }
         */
        public List<TestSuite.Testcase> getTestcase() {
            if (testcase == null) {
                testcase = new ArrayList<>();
            }
            return this.testcase;
        }

        /**
         * Gets the value of the systemOut property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getSystemOut() {
            return systemOut;
        }

        /**
         * Sets the value of the systemOut property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setSystemOut(String value) {
            this.systemOut = value;
        }

        /**
         * Gets the value of the systemErr property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getSystemErr() {
            return systemErr;
        }

        /**
         * Sets the value of the systemErr property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setSystemErr(String value) {
            this.systemErr = value;
        }

        /**
         * Gets the value of the name property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the timestamp property.
         *
         * @return possible object is
         * {@link XMLGregorianCalendar }
         */
        public XMLGregorianCalendar getTimestamp() {
            return timestamp;
        }

        /**
         * Sets the value of the timestamp property.
         *
         * @param value allowed object is
         *              {@link XMLGregorianCalendar }
         */
        public void setTimestamp(XMLGregorianCalendar value) {
            this.timestamp = value;
        }

        /**
         * Gets the value of the hostname property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getHostname() {
            return hostname;
        }

        /**
         * Sets the value of the hostname property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setHostname(String value) {
            this.hostname = value;
        }

        /**
         * Gets the value of the tests property.
         */
        public int getTests() {
            return tests;
        }

        /**
         * Sets the value of the tests property.
         */
        public void setTests(int value) {
            this.tests = value;
        }

        /**
         * Gets the value of the failures property.
         */
        public int getFailures() {
            return failures;
        }

        /**
         * Sets the value of the failures property.
         */
        public void setFailures(int value) {
            this.failures = value;
        }

        /**
         * Gets the value of the errors property.
         */
        public int getErrors() {
            return errors;
        }

        /**
         * Sets the value of the errors property.
         */
        public void setErrors(int value) {
            this.errors = value;
        }

        /**
         * Gets the value of the time property.
         *
         * @return possible object is
         * {@link BigDecimal }
         */
        public BigDecimal getTime() {
            return time;
        }

        /**
         * Sets the value of the time property.
         *
         * @param value allowed object is
         *              {@link BigDecimal }
         */
        public void setTime(BigDecimal value) {
            this.time = value;
        }

        public String getSkipped() {
            return skipped;
        }

        public void setSkipped(String skipped) {
            this.skipped = skipped;
        }

        public String getSkips() {
            return skips;
        }

        public void setSkips(String skips) {
            this.skips = skips;
        }

        // PROPERTIES FIELD
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"property"})
        public static class Properties {

            protected List<TestSuite.Properties.Property> property;

            public List<TestSuite.Properties.Property> getProperty() {
                if (property == null) {
                    property = new ArrayList<>();
                }
                return this.property;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "")
            public static class Property {

                @XmlAttribute(name = "name", required = true)
                @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
                protected String name;
                @XmlAttribute(name = "value", required = true)
                protected String value;

                /**
                 * Gets the value of the name property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getName() {
                    return name;
                }

                /**
                 * Sets the value of the name property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setName(String value) {
                    this.name = value;
                }

                /**
                 * Gets the value of the value property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setValue(String value) {
                    this.value = value;
                }

            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {"error", "failure"})
        public static class Testcase {

            protected TestSuite.Testcase.Error error;
            protected TestSuite.Testcase.Failure failure;
            @XmlAttribute(name = "name", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String name;
            @XmlAttribute(name = "classname", required = true)
            @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
            @XmlSchemaType(name = "token")
            protected String classname;
            @XmlAttribute(name = "time", required = true)
            protected BigDecimal time;
            @XmlAttribute(name = "skipped", required = true)
            protected String skipped;

            /**
             * Gets the value of the error property.
             *
             * @return possible object is
             * {@link TestSuite.Testcase.Error }
             */
            public TestSuite.Testcase.Error getError() {
                return error;
            }

            /**
             * Sets the value of the error property.
             *
             * @param value allowed object is
             *              {@link TestSuite.Testcase.Error }
             */
            public void setError(TestSuite.Testcase.Error value) {
                this.error = value;
            }

            /**
             * Gets the value of the failure property.
             *
             * @return possible object is
             * {@link TestSuite.Testcase.Failure }
             */
            public TestSuite.Testcase.Failure getFailure() {
                return failure;
            }

            /**
             * Sets the value of the failure property.
             *
             * @param value allowed object is
             *              {@link TestSuite.Testcase.Failure }
             */
            public void setFailure(TestSuite.Testcase.Failure value) {
                this.failure = value;
            }

            /**
             * Gets the value of the name property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the classname property.
             *
             * @return possible object is
             * {@link String }
             */
            public String getClassname() {
                return classname;
            }

            /**
             * Sets the value of the classname property.
             *
             * @param value allowed object is
             *              {@link String }
             */
            public void setClassname(String value) {
                this.classname = value;
            }

            /**
             * Gets the value of the time property.
             *
             * @return possible object is
             * {@link BigDecimal }
             */
            public BigDecimal getTime() {
                return time;
            }

            /**
             * Sets the value of the time property.
             *
             * @param value allowed object is
             *              {@link BigDecimal }
             */
            public void setTime(BigDecimal value) {
                this.time = value;
            }


            public String getSkipped() {
                return skipped;
            }

            public void setSkipped(String skipped) {
                this.skipped = skipped;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"value"})
            public static class Error {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "message")
                protected String message;
                @XmlAttribute(name = "type", required = true)
                protected String type;

                /**
                 * Gets the value of the value property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the message property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getMessage() {
                    return message;
                }

                /**
                 * Sets the value of the message property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setMessage(String value) {
                    this.message = value;
                }

                /**
                 * Gets the value of the type property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Sets the value of the type property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setType(String value) {
                    this.type = value;
                }

            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {"value"})
            public static class Failure {

                @XmlValue
                protected String value;
                @XmlAttribute(name = "message")
                protected String message;
                @XmlAttribute(name = "type", required = true)
                protected String type;

                /**
                 * Gets the value of the value property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getValue() {
                    return value;
                }

                /**
                 * Sets the value of the value property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setValue(String value) {
                    this.value = value;
                }

                /**
                 * Gets the value of the message property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getMessage() {
                    return message;
                }

                /**
                 * Sets the value of the message property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setMessage(String value) {
                    this.message = value;
                }

                /**
                 * Gets the value of the type property.
                 *
                 * @return possible object is
                 * {@link String }
                 */
                public String getType() {
                    return type;
                }

                /**
                 * Sets the value of the type property.
                 *
                 * @param value allowed object is
                 *              {@link String }
                 */
                public void setType(String value) {
                    this.type = value;
                }

            }
        }
    }
}
