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

import com.rakuten.entities.Category;
import com.rakuten.services.CategoryService;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService service;

	@RequestMapping("/category")
	public ModelAndView category() {
		ModelAndView mv = new ModelAndView("/category/category");
		mv.addObject("categories", service.findAll());
		return mv;
	}

	@RequestMapping("/category/edit")
	public ModelAndView edit(Category category) {
		ModelAndView mv = new ModelAndView("/category/edit");
		mv.addObject("category", category);
		mv.addObject("categories", service.findAllExcept(category));
		return mv;
	}

	@GetMapping("/category/edit/{id}")
	public ModelAndView edit(@PathVariable("id") Long id) {
		return edit(service.find(id));
	}
	
    @GetMapping("/category/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        service.delete(id);
        return category();
    }

	@PostMapping("/category/save")
	public ModelAndView save(@Valid Category category, BindingResult result) {
		if (result.hasErrors()) {
			return edit(category);
		}

		service.save(category);
		return category();
	}

}
