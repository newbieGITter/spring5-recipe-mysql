package com.saahas.demo.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.converters.RecipeCommandToRecipe;
import com.saahas.demo.converters.RecipeToRecipeCommand;
import com.saahas.demo.domain.Recipe;
import com.saahas.demo.repository.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepository;
	private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;
	
    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

	@Override
	public Set<Recipe> getRecipes() {
		Set<Recipe> recipes = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}

	@Override
	public Recipe getRecipeById(long id) throws Exception {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		
		if(!recipeOptional.isPresent()){
			throw new Exception("Recipe not found!");
		}
		return recipeOptional.get();
	}

	@Override
	public RecipeCommand getRecipeCommandById(Long id) throws Exception {
		return recipeToRecipeCommand.convert(getRecipeById(id));
	}
	
	@Override
    @Transactional 
    public RecipeCommand saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

	public void deleteRecipeById(long id) throws Exception {
		recipeRepository.delete(getRecipeById(id));
	}


}
