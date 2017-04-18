package com.rakuten.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.servlet.ModelAndView;

import com.rakuten.entities.Category;
import com.rakuten.services.CategoryService;

public class CategoryControllerTest {

	private static final long HOME_CATEGORY_ID = 10L;
	private static final long COMPUTER_CATEGORY_ID = 11L;

	private static final Category HOME_CATEGORY = Category.builder().id(HOME_CATEGORY_ID).build();

	private static final Category COMPUTER_CATEGORY = Category.builder().id(COMPUTER_CATEGORY_ID).build();

	@Mock
	private CategoryService service;

	@InjectMocks
	private CategoryController controller;

	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void category() throws Exception {
		List<Category> categories = Arrays.asList(HOME_CATEGORY);
		when(service.findAll()).thenReturn(categories);
		ModelAndView modelAndView = controller.category();
		assertThat(modelAndView.getViewName(), equalTo("/category/category"));
		assertThat(modelAndView.getModel().get("categories"), equalTo(categories));
	}
	
	@Test
	public void save() throws Exception {
		List<Category> categories = Arrays.asList(HOME_CATEGORY);
		when(service.findAll()).thenReturn(categories);
		ModelAndView modelAndView = controller.save(COMPUTER_CATEGORY);
		verify(service).save(COMPUTER_CATEGORY);
		assertThat(modelAndView.getViewName(), equalTo("/category/category"));
		assertThat(modelAndView.getModel().get("categories"), equalTo(categories));
	}

	@Test
	public void delete() {
		List<Category> categories = Arrays.asList(HOME_CATEGORY);
		when(service.findAll()).thenReturn(categories);
		ModelAndView modelAndView = controller.delete(HOME_CATEGORY_ID);
		verify(service).delete(HOME_CATEGORY_ID);
		assertThat(modelAndView.getViewName(), equalTo("/category/category"));
		assertThat(modelAndView.getModel().get("categories"), equalTo(categories));
	}

	@Test
	public void edit() {
		List<Category> categories = Arrays.asList(HOME_CATEGORY);
		when(service.findAllExcept(COMPUTER_CATEGORY)).thenReturn(categories);
		ModelAndView modelAndView = controller.edit(COMPUTER_CATEGORY);
		assertThat(modelAndView.getViewName(), equalTo("/category/edit"));
		assertThat(modelAndView.getModel().get("categories"), equalTo(categories));
	}

	@Test
	public void editWithId() {
		List<Category> categories = Arrays.asList(HOME_CATEGORY);
		when(service.findAllExcept(COMPUTER_CATEGORY)).thenReturn(categories);
		when(service.find(COMPUTER_CATEGORY_ID)).thenReturn(COMPUTER_CATEGORY);
		ModelAndView modelAndView = controller.edit(COMPUTER_CATEGORY_ID);
		assertThat(modelAndView.getViewName(), equalTo("/category/edit"));
		assertThat(modelAndView.getModel().get("categories"), equalTo(categories));
	}

}
