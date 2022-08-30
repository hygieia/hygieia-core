package com.capitalone.dashboard.testutil;

import java.net.InetSocketAddress;

import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.capitalone.dashboard.config.MongoConfig;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;

import de.bwaldvogel.mongo.MongoServer;
import de.bwaldvogel.mongo.backend.memory.MemoryBackend;

public class EmbeddedMongoConfig extends MongoConfig {

    @Bean(destroyMethod = "shutdown")
	public MongoServer mongoServer() {
		MongoServer mongoServer = new MongoServer(new MemoryBackend());
		mongoServer.bind();
		return mongoServer;
	}
    
    @Override
    @Bean
    protected MongoClientSettings mongoClientSettings() {
        InetSocketAddress serverAddress = mongoServer().getLocalAddress();
        String connectionString = String.format("mongodb://%s:%s", serverAddress.getHostName(), serverAddress.getPort());

        return MongoClientSettings.builder()
          .applyConnectionString(new ConnectionString(connectionString))
          .build();
    }
    
    @Override
    protected String getDatabaseName() {
        return "test-db";
    }

    @Bean
    public EmbeddedMongoRule embeddedMongoRule(MongoTemplate mongoTemplate) {
        return new EmbeddedMongoRule(mongoTemplate);
    }
}
