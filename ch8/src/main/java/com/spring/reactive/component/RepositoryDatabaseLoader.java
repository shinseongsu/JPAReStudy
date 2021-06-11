package com.spring.reactive.component;

import com.spring.reactive.dto.Item;
import com.spring.reactive.repository.BlockingItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

@Component
public class RepositoryDatabaseLoader {

//    @Bean
//    public CommandLineRunner initialize(BlockingItemRepository repository) {
//        return args -> {
//            repository.save(new Item("Alf alarm clock", 19.99));
//            repository.save(new Item("Smurf TV tray", 24.99));
//        };
//    }

    @Bean
    public CommandLineRunner initialize(MongoOperations mongo) {
        return args -> {
            mongo.save(new Item("Alf alarm clock", 19.99));
            mongo.save(new Item("Smurf TV tray", 24.99));
        };
    }

}
