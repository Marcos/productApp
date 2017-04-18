package com.rakuten.repositories;

import org.springframework.data.repository.CrudRepository;

import com.rakuten.entities.User;


public interface UserRepository extends CrudRepository<User, Long>{

	public User findByUsername(String username);
	
}
