package com.rakuten.repositories;

import org.springframework.data.repository.CrudRepository;

import com.rakuten.entities.Category;

public interface CategoryRepository extends CrudRepository<Category, Long>{

}
