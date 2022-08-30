package com.capitalone.dashboard.config;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.capitalone.dashboard.repository.RepositoryPackage;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;

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
    protected boolean autoIndexCreation() {
		return true;
	}

    @Override
    @Bean
    protected MongoClientSettings mongoClientSettings() {
        LOGGER.info("ReplicaSet" + dbreplicaset);

        String connectionString = Boolean.parseBoolean(dbreplicaset)
            ? String.format("mongodb://%s", String.join(",", hostport))
            : String.format("mongodb://%s:%s", host, port);

        MongoClientSettings.Builder settingsBuilder = MongoClientSettings.builder()
            .applyConnectionString(new ConnectionString(connectionString))
            .readPreference(ReadPreference.secondaryPreferred())
            .applyToSslSettings(sslSettings -> {
                sslSettings.enabled(Boolean.parseBoolean(dbssl));
                sslSettings.invalidHostNameAllowed(Boolean.parseBoolean(sslInvalidHostNameAllowed));
            })
            .applyToSocketSettings(socketSettings -> {
                socketSettings.connectTimeout(dbConnectTimeout, TimeUnit.MILLISECONDS);
                socketSettings.readTimeout(dbSocketTimeout, TimeUnit.MILLISECONDS);
            })
            .applyToConnectionPoolSettings(connectionPoolSettings -> {
                connectionPoolSettings.maxConnectionIdleTime(60000, TimeUnit.MILLISECONDS);
            })
            .applyToClusterSettings(clusterSettings -> {
                clusterSettings.serverSelectionTimeout(30000, TimeUnit.MILLISECONDS);
            });

        if (!StringUtils.isEmpty(userName)) {
            settingsBuilder.credential(
                MongoCredential.createScramSha1Credential(userName, databaseName, password.toCharArray())
            );
        }

        return settingsBuilder.build();
    }

    @Override
    protected String getMappingBasePackage() {
        return com.capitalone.dashboard.model.Application.class.getPackage().getName();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
