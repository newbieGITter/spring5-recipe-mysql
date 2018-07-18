package com.saahas.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saahas.demo.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class IndexController {

	private RecipeService recipeService;
	
	@Autowired
	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@RequestMapping({"/", "", "/index"})
	public String getIndexPage(Model model) {
		log.debug("Getting index page");
		
		model.addAttribute("recipes", recipeService.getRecipes());

        return "index";
	}

	
}

