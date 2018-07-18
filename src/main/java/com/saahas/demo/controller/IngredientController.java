package com.saahas.demo.controller;

import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.saahas.demo.commands.IngredientCommand;
import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.commands.UnitOfMeasureCommand;
import com.saahas.demo.services.IngredientService;
import com.saahas.demo.services.RecipeService;
import com.saahas.demo.services.UnitOfMeasureService;

@Controller
public class IngredientController {

	private RecipeService recipeService;
	private IngredientService ingredientService;
	private UnitOfMeasureService uomService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService uomService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}
	
	@GetMapping("/recipe/{recipeId}/ingredients")
	public String getIngredients(@PathVariable String recipeId, Model model) throws NumberFormatException, Exception {
		model.addAttribute("recipe", recipeService.getRecipeCommandById(Long.valueOf(recipeId)));
		
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		
		return "recipe/ingredient/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable String recipeId, Model model) throws NumberFormatException, Exception{
		RecipeCommand recipeCommand = recipeService.getRecipeCommandById(Long.valueOf(recipeId));
		Set<UnitOfMeasureCommand> uomList = uomService.listAllUoms();
		
		IngredientCommand ingCommand = new IngredientCommand();
		ingCommand.setRecipeId(Long.valueOf(recipeId));
		
		ingCommand.setUom(new UnitOfMeasureCommand());

		model.addAttribute("ingredient", ingCommand);
		model.addAttribute("uomList", uomList);
		
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model){
		model.addAttribute("ingredient", ingredientService.findByRecipeIdAndIngredientId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		
		model.addAttribute("uomList", uomService.listAllUoms());
		
		return "recipe/ingredient/ingredientform";
	}
	
	@PostMapping("/recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand) {
		IngredientCommand savedIngredient = ingredientService.saveOrUpdateRecipeIngredient(ingredientCommand);
		
		return "redirect:/recipe/" + savedIngredient.getRecipeId() + "/ingredient/" + savedIngredient.getId() + "/show";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		ingredientService.deleteById(Long.valueOf(recipeId), Long.valueOf(ingredientId));
		
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
	
}
