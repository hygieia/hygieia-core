package com.capitalone.dashboard.util;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GithubRepoMatcherTest {


    @Test
    public void test_parse_regex_AtoM(){
        assertTrue(GithubRepoMatcher.repoNameMatcher("https://github.com/test/AtoMtest","[a-mA-M]"));
    }

    @Test
    public void test_parse_regex_MtoZ(){
        assertTrue(GithubRepoMatcher.repoNameMatcher("https://github.com/test/MtoZtest","[m-zM-Z]"));
    }

    @Test
    public void test_not_match_regex_AtoM(){
        assertFalse(GithubRepoMatcher.repoNameMatcher("https://github.com/test/StoZtest","[a-mA-M]"));
    }

    @Test
    public void test_not_match_regex_MtoZ(){
        assertFalse(GithubRepoMatcher.repoNameMatcher("https://github.com/test/AtoMtest","[m-zM-Z]"));
    }

    @Test
    public void test_match_not_alphabet(){
        assertTrue(GithubRepoMatcher.repoNameMatcher("https://github.com/test/1toMtest","[a-mA-M]"));
    }

    @Test
    public void test_not_match_not_alphabet(){
        assertFalse(GithubRepoMatcher.repoNameMatcher("https://github.com/test/1toMtest","[m-zM-Z]"));
    }


    @Test
    public void test_orgName_parse_regex_MtoZ(){
        assertTrue(GithubRepoMatcher.orgNameMatcher("https://github.com/test/MtoZtest","[m-zM-Z]"));
    }

}
