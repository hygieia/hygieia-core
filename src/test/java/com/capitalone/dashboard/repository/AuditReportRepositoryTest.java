package com.capitalone.dashboard.repository;

import com.capitalone.dashboard.model.AuditReport;
import com.capitalone.dashboard.model.AuditType;
import com.capitalone.dashboard.model.EvaluationStatus;
import com.capitalone.dashboard.util.EmbeddedMongoConfig;
import com.capitalone.dashboard.util.EmbeddedMongoRule;
import com.capitalone.dashboard.util.LoadTestData;
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
@ContextConfiguration(classes = {EmbeddedMongoConfig.class})
@DirtiesContext
public class AuditReportRepositoryTest {

    @Autowired
    @Rule
    public EmbeddedMongoRule embeddedMongoRule;

    @Autowired
    private AuditReportRepository auditReportRepository;

    @Test
    public void findByAuditTypeAndEvaluationStatus() throws IOException {
        auditReportRepository.deleteAll();
        LoadTestData.loadAuditReports(auditReportRepository);
        Iterable<AuditReport> items = auditReportRepository.findByAuditTypeAndEvaluationStatus(AuditType.CONTAINER_SCAN, EvaluationStatus.PENDING);
        List<AuditReport> itemList = Lists.newArrayList(items);
        assertEquals(itemList.size(),1);
    }


}