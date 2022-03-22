package com.capitalone.dashboard.config;

import com.capitalone.dashboard.repository.RepositoryPackage;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@EnableMongoRepositories(basePackageClasses = RepositoryPackage.class)
public class MongoConfig extends AbstractMongoConfiguration {
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
    public MongoClient mongo() throws Exception {

        MongoClient client;
        LOGGER.info("ReplicaSet" + dbreplicaset);

        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.maxConnectionIdleTime(60000);
        builder.sslEnabled(Boolean.parseBoolean(dbssl));
        builder.serverSelectionTimeout(30000);          // MongoDB default 30 seconds
        builder.connectTimeout(dbConnectTimeout);       // MongoDB default varies, may be 10 seconds
        builder.socketTimeout(dbSocketTimeout);         // MongoDB default is 0, means no timeout
        /* By default, the driver ensures that the hostname included in the server’s SSL certificate(s)
         * matches the hostname(s) provided when constructing a MongoClient().
         * sslInvalidHostNameAllowed property helps to toggle the hostname verification, assigned false by default.
         * To toggle, add sslInvalidHostNameAllowed=true in application.properties
         *
         */
        builder.sslInvalidHostNameAllowed(Boolean.parseBoolean(sslInvalidHostNameAllowed));
        MongoClientOptions opts = builder.build();

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
                client = new MongoClient(serverAddressList);
            } else {
                MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(
                        userName, databaseName, password.toCharArray());
                client = new MongoClient(serverAddressList, Collections.singletonList(mongoCredential), opts);
            }
        } else {
            ServerAddress serverAddr = new ServerAddress(host, port);
            LOGGER.info("Initializing Mongo Client server at: {}", serverAddr);
            if (StringUtils.isEmpty(userName)) {
                client = new MongoClient(serverAddr);
            } else {
                MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(
                        userName, databaseName, password.toCharArray());
                client = new MongoClient(serverAddr, Collections.singletonList(mongoCredential), opts);
            }

        }
        LOGGER.info("Connecting to Mongo: {}", client);
        return client;
    }

    @Override
    protected String getMappingBasePackage() {
        return com.capitalone.dashboard.model.Application.class.getPackage().getName();
    }

    @Override
    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), getDatabaseName());
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
