package io.ascending.training.init;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import io.ascending.training.mongo.eventListener.UserCascadeSaveMongoEventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;

@Configuration
@EnableMongoRepositories(basePackages = {"io.ascending.training.mongo"})
class SpringDataMongoConfig {
    //Configuration for the mongo client (mongodb server) to use you can specify database name
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(System.getProperty("mongodb.url"));
    }

    @Bean
    public MongoClientFactoryBean mongo() {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        mongo.setHost("localhost");
        //port default 27017, not necessarily to set
//        mongo.setPort(27017);
        return mongo;
    }

    // MongoClient is teh entry point to the MongoDB driver API
    // We can connect to a specific MongoDB instance with additional information, such as database name , an optional username and password
    @Bean
    public MongoDatabaseFactory mongoDatabaseFactory() {
        return new SimpleMongoClientDatabaseFactory(mongoClient(), System.getProperty("mongodb.dbName"));
    }

    //Register MongoTemplate to do MongoOperation
    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDatabaseFactory());
    }

    //Register MongoOperation  why autowired, MongoTemple is an implementation of MongoOperations
    @Autowired
    public MongoOperations mongoOperations() {
        return mongoTemplate();
    }

    //Register UserCascadeSaveMongoEventListener
    @Bean
    public UserCascadeSaveMongoEventListener userCascadeSaveMongoEventListener() {
        return new UserCascadeSaveMongoEventListener();
    }

}
