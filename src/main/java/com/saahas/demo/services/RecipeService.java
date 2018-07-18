package com.saahas.demo.services;

import java.util.Set;

import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.domain.Recipe;

public interface RecipeService {

	Set<Recipe> getRecipes();

	Recipe getRecipeById(long id) throws Exception;

	RecipeCommand saveRecipeCommand(RecipeCommand command);

	RecipeCommand getRecipeCommandById(Long id) throws Exception;
	
	void deleteRecipeById(long id) throws Exception;
}
