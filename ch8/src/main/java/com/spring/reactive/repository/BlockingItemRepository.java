package com.spring.reactive.repository;

import com.spring.reactive.dto.Item;
import org.springframework.data.repository.CrudRepository;

public interface BlockingItemRepository extends CrudRepository<Item, String> {
}
