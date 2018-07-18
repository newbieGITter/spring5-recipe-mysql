package com.saahas.demo.services;

import com.saahas.demo.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId);
    
    IngredientCommand saveOrUpdateRecipeIngredient(IngredientCommand ingredientCommand);

	void deleteById(Long recipeId, Long ingredientId);

}
