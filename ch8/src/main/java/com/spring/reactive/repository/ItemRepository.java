package com.spring.reactive.repository;

import com.spring.reactive.dto.Item;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ItemRepository extends ReactiveCrudRepository<Item, String> {

    public Flux<Item> findByNameContaining(String partialName);

    @Query("{ 'name': ?0, 'age' : ?1}")
    public Flux<Item> findItemsForCustomerMonthlyReport(String name, int age);

    @Query(sort = "{ 'age' : -1 }")
    public Flux<Item> findSortedStuffForWeeklyReport();

    public Flux<Item> findByNameContainingIgnoreCase(String partialName);

    public Flux<Item> findByDescriptionContainingIgnoreCase(String partialName);

    public Flux<Item> findByNameContainingAndDescriptionContainingAllIgnoreCase(String partialName, String partialDesc);

    public Flux<Item> findByNameContainingOrDescriptionContainingAllIgnoreCase(String partialName, String partialDesc);

}
