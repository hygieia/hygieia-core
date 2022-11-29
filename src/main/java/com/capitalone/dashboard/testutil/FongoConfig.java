package com.capitalone.dashboard.testutil;

import org.springframework.context.annotation.Bean;

import com.capitalone.dashboard.config.MongoConfig;
//import com.github.fakemongo.Fongo;
import com.mongodb.client.MongoClient;

public class FongoConfig extends MongoConfig {

    @Override
    @Bean
    public MongoClient mongoClient()  {
    	return super.mongoClient();
    	// return new Fongo(getDatabaseName()).getMongo();
    }
    
    @Override
    protected String getDatabaseName() {
        return "test-db";
    }
}
