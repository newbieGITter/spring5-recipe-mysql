package com.saahas.demo.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.saahas.demo.commands.IngredientCommand;
import com.saahas.demo.converters.IngredientCommandToIngredient;
import com.saahas.demo.converters.IngredientToIngredientCommand;
import com.saahas.demo.domain.Ingredient;
import com.saahas.demo.domain.Recipe;
import com.saahas.demo.repository.RecipeRepository;
import com.saahas.demo.repository.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

	private RecipeRepository recipeRepository;
	private UnitOfMeasureRepository uomRepository;
	private IngredientToIngredientCommand ingredientToIngredientCommand;
	private IngredientCommandToIngredient ingredientCommandToIngredient;

	public IngredientServiceImpl(RecipeRepository recipeRepository, UnitOfMeasureRepository uomRepository,
			IngredientToIngredientCommand ingredientToIngredientCommand, IngredientCommandToIngredient ingredientCommandToIngredient) {
		this.recipeRepository = recipeRepository;
		this.uomRepository = uomRepository;
		this.ingredientToIngredientCommand = ingredientToIngredientCommand;
		this.ingredientCommandToIngredient = ingredientCommandToIngredient;
	}

	@Override
	public IngredientCommand findByRecipeIdAndIngredientId(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (!recipeOptional.isPresent()) {
			log.error("recipe id not found. Id: " + recipeId);
		}
		
		Recipe recipe = recipeOptional.get();
		
		Optional<IngredientCommand> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId))
										.map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
		
		if (!ingredientOptional.isPresent()) {
			log.error("Ingredient not found with recipe Id: " + recipeId + " and ingredient Id: " + ingredientId);
		}
		
		return ingredientOptional.get();
	}

	@Override
	public IngredientCommand saveOrUpdateRecipeIngredient(IngredientCommand ingredientCommand) {
		Long recipeId = ingredientCommand.getRecipeId();
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()){
			log.error("recipe id not found. Id:" + recipeId);
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();
			
			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ing -> ing.getId().equals(ingredientCommand.getId())).findFirst();
			
			if(!ingredientOptional.isPresent()) {
				Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);
			} else {
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setAmount(ingredientCommand.getAmount());
				ingredientFound.setDescription(ingredientCommand.getDescription());
				ingredientFound.setUom(uomRepository.findById(ingredientCommand.getUom().getId()).orElseThrow(() -> new RuntimeException("UOM Not found!")));
			}
			
			Recipe savedRecipe = recipeRepository.save(recipe);			
			Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            //check by description
            if(!savedIngredientOptional.isPresent()){
                //not totally safe... But best guess
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
			
		}
	}

	@Override
	public void deleteById(Long recipeId, Long ingredientId) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		
		if(!recipeOptional.isPresent()){
			log.error("recipe id not found. Id:" + recipeId);
		} else {
			Recipe recipe = recipeOptional.get();
			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ing -> ing.getId().equals(ingredientId)).findFirst();
			
			if(!ingredientOptional.isPresent()) {
				log.error("Ingredient id not found. Id:" + ingredientId);
			} else {
				Ingredient ingFound = ingredientOptional.get();
				ingFound.setRecipe(null);
				recipe.getIngredients().remove(ingredientOptional.get());
				recipeRepository.save(recipe);
			}
		}
		
	}

}
