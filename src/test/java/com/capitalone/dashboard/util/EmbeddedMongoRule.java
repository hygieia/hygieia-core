package com.capitalone.dashboard.util;

import org.junit.rules.ExternalResource;
import org.springframework.data.mongodb.core.MongoTemplate;

public class EmbeddedMongoRule extends ExternalResource {
    
    private MongoTemplate mongoTemplate;

    public EmbeddedMongoRule(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    protected void after() {
        mongoTemplate.getDb().drop();
    }
  
}
