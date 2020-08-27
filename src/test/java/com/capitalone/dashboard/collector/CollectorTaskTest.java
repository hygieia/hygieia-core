package com.capitalone.dashboard.collector;

import com.capitalone.dashboard.model.Collector;
import com.capitalone.dashboard.model.RepoBranch;
import com.capitalone.dashboard.repository.BaseCollectorRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.scheduling.TaskScheduler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class CollectorTaskTest {
    @Mock private TaskScheduler taskScheduler;

    private static final String COLLECTOR_NAME = "Test Collector";
    private CollectorTask<Collector> collector;

    @Before
    public void init() {
        collector = new CollectorTaskTest.TestCollectorTask();
    }

    @Test
    public void throttleRequestsTest_ratelimit_exceeded() {
        long startTime = System.currentTimeMillis() - 500;
        int requestCount = 5;
        long waitTime = 500;
        int requestRateLimit = 3;
        long requestRateLimitTimeWindow = 1000;

        CollectorTask<Collector> collectorSpy = Mockito.spy(collector);
        boolean result = collectorSpy.throttleRequests(startTime, requestCount,
                                                waitTime, requestRateLimit,
                                                requestRateLimitTimeWindow);
        assertEquals(true, result);
        verify(collectorSpy, times(1)).sleep(Mockito.anyLong());
    }

    @Test
    public void throttleRequestsTest_ratelimit_not_exceeded() {
        long startTime = System.currentTimeMillis() - 500;
        int requestCount = 2;
        long waitTime = 500;
        int requestRateLimit = 3;
        long requestRateLimitTimeWindow = 1000;

        CollectorTask<Collector> collectorSpy = Mockito.spy(collector);
        boolean result = collectorSpy.throttleRequests(startTime, requestCount,
                waitTime, requestRateLimit,
                requestRateLimitTimeWindow);
        assertEquals(false, result);
        verify(collectorSpy, times(0)).sleep(Mockito.anyLong());
    }

    @Test
    public void throttleRequestsTest_ratelimit_exceeded_with_timeWindow_greaterThan_rateLimitTimeWindow() {
        long startTime = System.currentTimeMillis() - 2000;
        int requestCount = 5;
        long waitTime = 500;
        int requestRateLimit = 3;
        long requestRateLimitTimeWindow = 1000;

        CollectorTask<Collector> collectorSpy = Mockito.spy(collector);
        boolean result = collectorSpy.throttleRequests(startTime, requestCount,
                waitTime, requestRateLimit,
                requestRateLimitTimeWindow);
        assertEquals(true, result);
        verify(collectorSpy, times(0)).sleep(Mockito.anyLong());
    }

    @Test
    public void testRepoBranch() {

        String branch = "master";
        String url0 = "git+https://gh.pages.com/org/repo.git";
        String url1 = "git@gh.pages.com:org/repo";
        RepoBranch rp0 = new RepoBranch(url0, branch, RepoBranch.RepoType.GIT);
        RepoBranch rp1 = new RepoBranch(url1, branch, RepoBranch.RepoType.GIT);
        boolean result = rp0.equals(rp1);
        assertTrue(result);
    }

    @Test
    public void testRepoBranchSSH() {

        String branch = "master";
        String url0 = "ssh://gh.pages.com/org/repo.git";
        String url1 = "git@gh.pages.com:org/repo";
        RepoBranch rp0 = new RepoBranch(url0, branch, RepoBranch.RepoType.GIT);
        RepoBranch rp1 = new RepoBranch(url1, branch, RepoBranch.RepoType.GIT);
        boolean result = rp0.equals(rp1);
        assertTrue(result);
    }

    @Test
    public void testRepoBranchNonZeroPort() {

        String branch = "master";
        String url0 = "https://gh.pages.com:8087/org/repo.git";
        String url1 = "git@gh.pages.com:org/repo";
        RepoBranch rp0 = new RepoBranch(url0, branch, RepoBranch.RepoType.GIT);
        RepoBranch rp1 = new RepoBranch(url1, branch, RepoBranch.RepoType.GIT);
        boolean result = rp0.equals(rp1);
        assertTrue(result);
    }

    @Test
    public void testRepoBranchSpecialCharacter() {

        String branch = "master";
        String url0 = "https://gh.pages.com/org/repo-1.git";
        String url1 = "git@gh.pages.com:org/repo-1";
        RepoBranch rp0 = new RepoBranch(url0, branch, RepoBranch.RepoType.GIT);
        RepoBranch rp1 = new RepoBranch(url1, branch, RepoBranch.RepoType.GIT);
        boolean result = rp0.equals(rp1);
        assertTrue(result);
    }

    @Test
    public void testRepoBranchWhiteSpace() {

        String branch = "master";
        String url0 = "https://gh.pages.com/org one/repo-1.git";
        String url1 = "git@gh.pages.com:org one/repo-1";
        RepoBranch rp0 = new RepoBranch(url0, branch, RepoBranch.RepoType.GIT);
        RepoBranch rp1 = new RepoBranch(url1, branch, RepoBranch.RepoType.GIT);
        boolean result = rp0.equals(rp1);
        assertTrue(result);
    }

    private class TestCollectorTask extends CollectorTask<Collector> {

        public TestCollectorTask() {
            super(taskScheduler, COLLECTOR_NAME);
        }

        @Override
        public Collector getCollector() { return null; }

        @Override
        public BaseCollectorRepository<Collector> getCollectorRepository() { return null; }

        @Override
        public String getCron() { return null; }

        @Override
        public void collect(Collector collector) { }
    }
}