package com.capitalone.dashboard.util;

import org.junit.Assert;
import org.junit.Test;

public class HygieiaUtilsTest {
    @Test
    public void checkForEmptyStringValuesTest() {
        boolean result = HygieiaUtils.checkForEmptyStringValues("", "test1", "test2");
        Assert.assertTrue(result);

        result = HygieiaUtils.checkForEmptyStringValues("test0", "test1", "test2");
        Assert.assertFalse(result);

        Assert.assertEquals("https://jenkins.com/job/test/job/youseeme/job/Reference/job/master/",HygieiaUtils.normalizeJobUrl("https://jenkins.com/job/test/job/youseeme/job/Reference/job/master/228/"));
        Assert.assertEquals("https://jenkins.com/job/test/job/youseeme/job/Reference/job/master/",HygieiaUtils.normalizeJobUrl("https://jenkins.com/job/test/job/youseeme/job/Reference/job/master/"));
        Assert.assertEquals("https://jenkins.com/job/test/job/youseeme/job/Reference/job/master",HygieiaUtils.normalizeJobUrl("https://jenkins.com/job/test/job/youseeme/job/Reference/job/master"));

        Assert.assertEquals(true, HygieiaUtils.isNumeric("1252435"));
        Assert.assertEquals(false, HygieiaUtils.isNumeric("zbcxsfgde"));
        Assert.assertEquals(false, HygieiaUtils.isNumeric("a@#%#^^"));
        Assert.assertEquals(false, HygieiaUtils.isNumeric("12as%6"));
    }
}
