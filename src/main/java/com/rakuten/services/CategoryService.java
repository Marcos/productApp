package com.rakuten.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rakuten.entities.Category;
import com.rakuten.repositories.CategoryRepository;
import com.rakuten.repositories.CategorySearchRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;

	@Autowired
	private CategorySearchRepository searchRepository;

	public Iterable<Category> findAllExcept(Category category) {
		if (category != null && category.getId() != null)
			return searchRepository.findOwnerCategoriesExcept(category.getId());
		return searchRepository.findOwnerCategories();
	}

	public Iterable<Category> findAll() {
		return repository.findAll();
	}

	public void save(Category category) {
		repository.save(category);
	}

	public Category find(Long id) {
		return repository.findOne(id);
	}

	public void delete(Long id) {
		repository.delete(id);
	}

}
