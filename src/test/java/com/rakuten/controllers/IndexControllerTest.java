package com.rakuten.controllers;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

public class IndexControllerTest {

	@InjectMocks
	private IndexController indexController;


	@Before
	public void setup() {
		initMocks(this);
	}

	@Test
	public void index() throws Exception {
		assertThat(indexController.index(), equalTo("/index"));
	}

	@Test
	public void login() throws Exception {
		assertThat(indexController.login(), equalTo("/login"));
	}

}
