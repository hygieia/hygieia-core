package com.capitalone.dashboard.repository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.capitalone.dashboard.model.AuditReport;
import com.capitalone.dashboard.model.AuditType;
import com.capitalone.dashboard.model.EvaluationStatus;

@ExtendWith(MockitoExtension.class)
public class AuditReportRepositoryTest {


    
    private AuditReportRepository auditReportRepository = Mockito.mock(AuditReportRepository.class);

    @Test
    public void findByAuditTypeAndEvaluationStatus() throws IOException {
    	doNothing().when(auditReportRepository).deleteAll();
    	
    	  List<AuditReport> itemList = new ArrayList<AuditReport>();
    	  AuditReport audit = new AuditReport();
    	  audit.setAuditType(AuditType.CONTAINER_SCAN);
    	  audit.setEvaluationStatus(EvaluationStatus.PENDING);
    	  itemList.add(audit);
    	when(auditReportRepository.findByAuditTypeAndEvaluationStatus(AuditType.CONTAINER_SCAN, EvaluationStatus.PENDING)).thenReturn(itemList);
    	
        assertEquals(itemList.size(),1);
    }


}