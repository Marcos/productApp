package com.rakuten.repositories;

import org.springframework.data.repository.CrudRepository;

import com.rakuten.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{

}
