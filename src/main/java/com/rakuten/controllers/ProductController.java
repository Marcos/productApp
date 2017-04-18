package com.rakuten.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rakuten.entities.Product;
import com.rakuten.services.ProductService;
import com.rakuten.services.exceptions.CurrencyNotFoundException;

@Controller
public class ProductController {

	@Autowired
	private ProductService service;

	@RequestMapping("/product")
	public ModelAndView product() {
		ModelAndView mv = new ModelAndView("/product/product");
		mv.addObject("products", service.findAll());
		return mv;
	}

	@RequestMapping("/product/edit")
	public ModelAndView edit(Product product) {
		ModelAndView mv = new ModelAndView("/product/edit");
		mv.addObject("product", product);
		mv.addObject("categories", service.findAvailableCategories());
		mv.addObject("currencies", service.getAvailableCurrencies());
		return mv;
	}

	@GetMapping("/product/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) throws CurrencyNotFoundException {
		return edit(service.find(id));
	}

	@GetMapping("/product/delete/{id}")
	public ModelAndView delete(@PathVariable("id") Long id) {
		service.delete(id);
		return product();
	}

	@PostMapping("/product/save")
	public ModelAndView save(@Valid Product product) throws CurrencyNotFoundException {
		service.save(product);
		return product();
	}

}
