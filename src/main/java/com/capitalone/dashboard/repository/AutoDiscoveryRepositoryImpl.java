package com.capitalone.dashboard.repository;


import com.capitalone.dashboard.model.AutoDiscovery;
import com.capitalone.dashboard.model.AutoDiscoveryStatusType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AutoDiscoveryRepositoryImpl {

    private final MongoTemplate template;

    @Autowired
    public AutoDiscoveryRepositoryImpl(MongoTemplate template) {
        this.template = template;
    }

    public List<AutoDiscovery> findAllAutoDiscoveriesWithStatusNew(){
        Criteria cd = new Criteria();
        cd.orOperator(Criteria.where("codeRepoEntries.status").is(AutoDiscoveryStatusType.NEW),
                Criteria.where("buildEntries.status").is(AutoDiscoveryStatusType.NEW),
                Criteria.where("staticCodeEntries.status").is(AutoDiscoveryStatusType.NEW),
                Criteria.where("libraryScanEntries.status").is(AutoDiscoveryStatusType.NEW),
                Criteria.where("artifactEntries.status").is(AutoDiscoveryStatusType.NEW),
                Criteria.where("securityScanEntries.status").is(AutoDiscoveryStatusType.NEW),
                Criteria.where("functionalTestEntries.status").is(AutoDiscoveryStatusType.NEW),
                Criteria.where("featureEntries.status").is(AutoDiscoveryStatusType.NEW),
                Criteria.where("deploymentEntries.status").is(AutoDiscoveryStatusType.NEW));
        List<AutoDiscovery> items =  template.find(new Query(cd), AutoDiscovery.class);
        return items;
    }

}