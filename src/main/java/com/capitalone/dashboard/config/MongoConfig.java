package com.capitalone.dashboard.config;

import com.capitalone.dashboard.repository.RepositoryPackage;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
@EnableMongoRepositories(basePackageClasses = RepositoryPackage.class)
public class MongoConfig extends AbstractMongoClientConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoConfig.class);

    @Value("${dbname:dashboarddb}")
    private String databaseName;
    @Value("${dbhost:localhost}")
    private String host;
    @Value("${dbport:27017}")
    private int port;
    @Value("${dbreplicaset:false}")
    private String dbreplicaset;
    @Value("#{'${dbhostport:localhost:27017}'.split(',')}")
    private List<String> hostport;
    @Value("${dbusername:}")
    private String userName;
    @Value("${dbpassword:}")
    private String password;
    @Value("${dbssl:false}")
    private String dbssl;
    @Value("${dbconnecttimeout:30000}")
    private int dbConnectTimeout;
    @Value("${dbsockettimeout:900000}")
    private int dbSocketTimeout;
    @Value("${sslInvalidHostNameAllowed:false}")
    private String sslInvalidHostNameAllowed;

    @Override
    protected String getDatabaseName() {
        return databaseName;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {

        MongoClient client;
        LOGGER.info("ReplicaSet: " + dbreplicaset);
        if (Boolean.parseBoolean(dbreplicaset)) {
            List<ServerAddress> serverAddressList = new ArrayList<>();
            for (String h : hostport) {
                String myHost = h.substring(0, h.indexOf(":"));
                int myPort = Integer.parseInt(h.substring(h.indexOf(":") + 1));
                ServerAddress serverAddress = new ServerAddress(myHost, myPort);
                serverAddressList.add(serverAddress);
            }

            for (ServerAddress s : serverAddressList) {
                LOGGER.info("Initializing Mongo Client server ReplicaSet at: {}", s);
            }

            if (StringUtils.isEmpty(userName)) {
                LOGGER.info("Initializing Mongo Client server ReplicaSet as true at: {} with creds as empty" + serverAddressList);
                client = MongoClients.create(MongoClientSettings.builder()
                        .applyToClusterSettings(builder -> builder.hosts(serverAddressList))
                        .build());
            } else {
                LOGGER.info("Initializing Mongo Client server ReplicaSet as true at: {} with creds" + serverAddressList);
                client = getMongoClient(serverAddressList);
            }
        } else {
            ServerAddress serverAddr = new ServerAddress(host, port);
            LOGGER.info("Initializing Mongo Client server at: {}", serverAddr);
            List<ServerAddress> addresses = new ArrayList<ServerAddress>();
            addresses.add(serverAddr);
            if (StringUtils.isEmpty(userName)) {
                client = MongoClients.create(MongoClientSettings.builder()
                        .applyToClusterSettings(builder -> builder.hosts(addresses)).build());
            } else {
                client = getMongoClient(addresses);
            }

        }
        LOGGER.info("Connecting to Mongo: {}", client);
        return client;
    }

    private MongoClient getMongoClient(List<ServerAddress> hostList) {
        MongoClient client;
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(userName, databaseName,
                password.toCharArray());
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder().credential(mongoCredential)
                .applyToClusterSettings(builder -> builder.hosts(hostList))
                .applyToConnectionPoolSettings(builder -> builder.maxConnectionIdleTime(60000, TimeUnit.MILLISECONDS))
                .applyToSslSettings(builder -> {
                    builder.enabled(Boolean.parseBoolean(dbssl));
                    builder.invalidHostNameAllowed(Boolean.parseBoolean(sslInvalidHostNameAllowed));
                })
                .applyToSocketSettings(builder -> {
                    builder.connectTimeout(dbConnectTimeout, TimeUnit.MILLISECONDS);
                    builder.readTimeout(dbSocketTimeout, TimeUnit.MILLISECONDS);
                })
                .readPreference(ReadPreference.secondaryPreferred())
                .retryWrites(false)
                .applyToClusterSettings(builder -> builder.serverSelectionTimeout(30000, TimeUnit.MILLISECONDS))
                .build();
        client = MongoClients.create(mongoClientSettings);
        LOGGER.info("Initializing Mongo Client server ReplicaSet as true at: {} with creds" + hostList + " : Cluster Settings = " + mongoClientSettings.getClusterSettings() +
                " SSL Settings = " + mongoClientSettings.getSslSettings() + " ConnectionPool Settings = " + mongoClientSettings.getConnectionPoolSettings() + " Socket Settings = " + mongoClientSettings.getSocketSettings());
        return client;
    }

    @Override
    protected String getMappingBasePackage() {
        return com.capitalone.dashboard.model.Application.class.getPackage().getName();
    }

    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongoClient(), getDatabaseName());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
