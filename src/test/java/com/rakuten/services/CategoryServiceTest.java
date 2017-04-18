package com.rakuten.services;

import static com.rakuten.services.Tests.toList;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.rakuten.entities.Category;
import com.rakuten.repositories.CategoryRepository;
import com.rakuten.repositories.CategorySearchRepository;

public class CategoryServiceTest {

	private static final long COMPUTER_CATEGORY_ID = 10L;
	private static final long ELETRONIC_CATEGORY_ID = 11L;
	private static final Long HOME_CATEGORY_ID = 12L;
	private static final Long FASHION_CATEGORY_ID = 13L;

	@Mock
	private CategoryRepository repository;

	@Mock
	private CategorySearchRepository searchRepository;

	@InjectMocks
	private CategoryService service;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void findAllExceptWhenCategoryIdIsNotNull() {
		when(searchRepository.findOwnerCategoriesExcept(COMPUTER_CATEGORY_ID))
				.thenReturn(getCategoriesWhenCategoryIdIsNotNull());
		List<Category> categories = toList(service.findAllExcept(createCategory(COMPUTER_CATEGORY_ID)));
		assertThat(categories, hasSize(1));
		assertThat(categories.get(0).getId(), equalTo(ELETRONIC_CATEGORY_ID));
		verify(searchRepository).findOwnerCategoriesExcept(COMPUTER_CATEGORY_ID);
		verify(searchRepository, never()).findOwnerCategories();

	}

	@Test
	public void findAllExceptWhenCategoryIdIsNull() {
		when(searchRepository.findOwnerCategories()).thenReturn(getCategoriesWhenCategoryIdIsNull());
		List<Category> categories = toList(service.findAllExcept(createCategory(null)));
		assertThat(categories, hasSize(2));
		assertThat(categories.get(0).getId(), equalTo(ELETRONIC_CATEGORY_ID));
		assertThat(categories.get(1).getId(), equalTo(COMPUTER_CATEGORY_ID));
		verify(searchRepository).findOwnerCategories();
		verify(searchRepository, never()).findOwnerCategoriesExcept(COMPUTER_CATEGORY_ID);

	}

	@Test
	public void findAllExceptWhenCategoryNull() {
		when(searchRepository.findOwnerCategories()).thenReturn(getCategoriesWhenCategoryIdIsNull());
		List<Category> categories = toList(service.findAllExcept(null));
		assertThat(categories, hasSize(2));
		assertThat(categories.get(0).getId(), equalTo(ELETRONIC_CATEGORY_ID));
		assertThat(categories.get(1).getId(), equalTo(COMPUTER_CATEGORY_ID));
		verify(searchRepository).findOwnerCategories();
		verify(searchRepository, never()).findOwnerCategoriesExcept(COMPUTER_CATEGORY_ID);

	}

	@Test
	public void findAll() {
		when(repository.findAll()).thenReturn(getAllCategories());
		List<Category> categories = toList(service.findAll());
		assertThat(categories, hasSize(2));
		assertThat(categories.get(0).getId(), equalTo(HOME_CATEGORY_ID));
		assertThat(categories.get(1).getId(), equalTo(FASHION_CATEGORY_ID));
		verify(repository).findAll();
	}

	@Test
	public void save() {
		Category category = createCategory(FASHION_CATEGORY_ID);
		service.save(category);
		verify(repository).save(category);
	}

	@Test
	public void find() {
		when(repository.findOne(FASHION_CATEGORY_ID)).thenReturn(createCategory(FASHION_CATEGORY_ID));
		Category category = service.find(FASHION_CATEGORY_ID);
		assertThat(category.getId(), equalTo(FASHION_CATEGORY_ID));
		verify(repository).findOne(FASHION_CATEGORY_ID);
	}

	@Test
	public void delete() {
		service.delete(FASHION_CATEGORY_ID);
		verify(repository).delete(FASHION_CATEGORY_ID);
	}

	private List<Category> getAllCategories() {
		return asList(createCategory(HOME_CATEGORY_ID), createCategory(FASHION_CATEGORY_ID));
	}

	private List<Category> getCategoriesWhenCategoryIdIsNull() {
		return asList(createCategory(ELETRONIC_CATEGORY_ID), createCategory(COMPUTER_CATEGORY_ID));
	}

	private Category createCategory(Long categoryId) {
		return Category.builder().id(categoryId).build();
	}

	private List<Category> getCategoriesWhenCategoryIdIsNotNull() {
		return asList(createCategory(ELETRONIC_CATEGORY_ID));
	}


}
