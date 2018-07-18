package com.saahas.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.services.RecipeService;

@Controller
public class RecipeController {

	private RecipeService recipeService;
	
	@Autowired
	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService; 
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String getRecipeById(@PathVariable long id, Model model) throws Exception{
		model.addAttribute("recipe", recipeService.getRecipeById(id));
		
		return "recipe/show";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) throws NumberFormatException, Exception {
		model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(id)));
		
		return "recipe/recipeform";
	}

	@GetMapping
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeform";
	}
	
	@PostMapping("recipe")
	public String saveOrUpdate(@ModelAttribute RecipeCommand recipeCommand) {
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
				
		return "redirect:/recipe/" + savedCommand.getId() + "/show"; 
	}
	
	@DeleteMapping
	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id, Model model) throws NumberFormatException, Exception {
		recipeService.deleteRecipeById(Long.valueOf(id));
		
        return "redirect:/";
	}
}
