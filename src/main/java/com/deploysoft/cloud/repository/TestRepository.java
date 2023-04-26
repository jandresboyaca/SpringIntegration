package com.deploysoft.cloud.repository;

import com.deploysoft.cloud.repository.entity.Item;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Item, String> {
}
