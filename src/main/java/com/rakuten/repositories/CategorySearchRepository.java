package com.rakuten.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rakuten.entities.Category;

@Transactional(readOnly = true)
public interface CategorySearchRepository extends JpaRepository<Category, Long> {

	@Query("select c from Category c where c.id <> :id and c.owner is null")
	List<Category> findOwnerCategoriesExcept(@Param("id")Long id);
	
	@Query("select c from Category c where c.owner is null")
	List<Category> findOwnerCategories();

	
}
