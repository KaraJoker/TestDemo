package com.cn.company.config;

import com.mongodb.MongoClientURI;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * MongoDB Secondary Config
 * @since 2019-07-01 17:12
 */
@Configuration
@EnableMongoRepositories(basePackages = "com.cn.company.secondary", mongoTemplateRef = "secondaryMongoTemplate")
public class SecondaryMongoConfig {

    @Bean
    @ConfigurationProperties(prefix="spring.data.mongodb.secondary")
    public MongoProperties secondaryMongoProperties() {
        return new MongoProperties();
    }

    @Bean(name = "secondaryMongoTemplate")
    public MongoTemplate secondaryMongoTemplate() throws Exception {
        return new MongoTemplate(secondaryFactory(secondaryMongoProperties()));
    }

    @Bean
    public MongoDbFactory secondaryFactory(MongoProperties mongoProperties) throws Exception {
        return new SimpleMongoDbFactory(new MongoClientURI(secondaryMongoProperties().getUri()));
    }
}
