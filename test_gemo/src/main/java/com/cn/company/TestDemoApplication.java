package com.cn.company;

import com.cn.company.config.FilePathConfig;
import com.cn.company.cqrs.c.repository.SingletonEntityFactoryImpl;
import com.cn.company.cqrs.c.service.disruptor.CoreSnapshotFactory;
import com.cn.company.cqrs.c.service.disruptor.ProcessCoreCommandEventHandler;
import com.cn.company.cqrs.c.service.disruptor.SnapshotJsonUtil;
import com.cn.company.init.InitProcessor;
import com.highto.framework.ddd.SingletonEntityRepository;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"com.cn.company"})
@EntityScan(basePackages = {"com.cn.company",
        "org.axonframework.eventsourcing.eventstore.jpa",
        "org.axonframework.eventhandling.saga.repository.jpa",
        "org.axonframework.eventhandling.tokenstore.jpa"})
@EnableJpaRepositories(basePackages = {"com.cn.company.plan"})
@EnableAutoConfiguration(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TestDemoApplication {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SnapshotJsonUtil snapshotJsonUtil;

    @Autowired
    private CoreSnapshotFactory coreSnapshotFactory;

    @Autowired
    private FilePathConfig filePathConfig;

    @Bean
    public HttpClient httpClient() {
        HttpClient client = new HttpClient();
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean
    public HttpClient sslHttpClient() {

        HttpClient client = new HttpClient(new SslContextFactory());
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return client;
    }

    @Bean
    public ProcessCoreCommandEventHandler processCoreCommandEventHandler() {
        return new ProcessCoreCommandEventHandler(coreSnapshotFactory, snapshotJsonUtil, filePathConfig);
    }

    @Bean
    public SingletonEntityRepository singletonEntityRepository() {
        SingletonEntityRepository singletonEntityRepository = new SingletonEntityRepository();
        singletonEntityRepository.setEntityFactory(new SingletonEntityFactoryImpl());
        return singletonEntityRepository;
    }

    @Bean
    public InitProcessor initProcessor() {
        InitProcessor initProcessor = new InitProcessor(httpClient(), sslHttpClient(), snapshotJsonUtil,
                processCoreCommandEventHandler(), singletonEntityRepository(), applicationContext);
        initProcessor.init();
        return initProcessor;
    }

    public static void main(String[] args) {
        SpringApplication.run(TestDemoApplication.class, args);
    }
}
