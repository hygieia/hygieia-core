package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.AuditReport;
import com.capitalone.dashboard.model.AuditType;
import com.capitalone.dashboard.testutil.FongoConfig;
import com.capitalone.dashboard.util.LoadTestData;
import com.github.fakemongo.junit.FongoRule;
import com.google.common.collect.Lists;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {FongoConfig.class})
@DirtiesContext
public class AuditReportRepositoryTest {

    @Rule
    public FongoRule fongoRule = new FongoRule();

    @Autowired
    private AuditReportRepository auditReportRepository;

    @Test
    public void findAllByOptionNameValueAndCollectorIdsInReturns0() throws IOException {
        auditReportRepository.deleteAll();
        LoadTestData.loadAuditReports(auditReportRepository);
        Iterable<AuditReport> items = auditReportRepository.findByAuditTypeAndAuditResponseHttpStatusCode(AuditType.CONTAINER_SCAN, false);
        List<AuditReport> itemList = Lists.newArrayList(items);
        assertEquals(itemList.size(),1);
    }

}