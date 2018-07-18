package com.saahas.demo.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.saahas.demo.commands.IngredientCommand;
import com.saahas.demo.converters.IngredientCommandToIngredient;
import com.saahas.demo.converters.IngredientToIngredientCommand;
import com.saahas.demo.converters.UnitOfMeasureCommandToUnitOfMeasure;
import com.saahas.demo.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.saahas.demo.domain.Ingredient;
import com.saahas.demo.domain.Recipe;
import com.saahas.demo.repository.RecipeRepository;
import com.saahas.demo.repository.UnitOfMeasureRepository;

public class IngredientServiceImplTest {

	private IngredientService ingredientService;
	
	@Mock 
	private RecipeRepository recipeRepository;
	
	@Mock
	private UnitOfMeasureRepository uomRepository;
	
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;

    //init converters
    public IngredientServiceImplTest() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
    }
    
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
    	ingredientService = new IngredientServiceImpl(recipeRepository, uomRepository, ingredientToIngredientCommand, ingredientCommandToIngredient);
    }
	@Test
	public void testFindByRecipeIdAndIngredientId() {
		// Given
		Optional<Recipe> recipeOptional = setupRecipeObject();
		Mockito.when(recipeRepository.findById(1L)).thenReturn(recipeOptional);
		
		// When
		IngredientCommand ingCommand = ingredientService.findByRecipeIdAndIngredientId(1L, 3L);

		// Then
		assertEquals(Long.valueOf(3L), ingCommand.getId());
        assertEquals(Long.valueOf(1L), ingCommand.getRecipeId());
        verify(recipeRepository, Mockito.times(1)).findById(Mockito.anyLong());
	}

	private Optional<Recipe> setupRecipeObject() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredient ig1 = new Ingredient();
		ig1.setId(1L);
		
		Ingredient ig2 = new Ingredient();
		ig2.setId(2L);

		Ingredient ig3 = new Ingredient();
		ig3.setId(3L);
		
		recipe.addIngredient(ig1);
		recipe.addIngredient(ig2);
		recipe.addIngredient(ig3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		return recipeOptional;
	}

}
