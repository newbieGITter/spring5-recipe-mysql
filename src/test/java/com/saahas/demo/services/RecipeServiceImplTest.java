package com.saahas.demo.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.converters.RecipeCommandToRecipe;
import com.saahas.demo.converters.RecipeToRecipeCommand;
import com.saahas.demo.domain.Recipe;
import com.saahas.demo.repository.RecipeRepository;

public class RecipeServiceImplTest {

	private RecipeServiceImpl service;
	
	@Mock
	private RecipeRepository recipeRepo;
	
	@Mock
	private RecipeToRecipeCommand recipeToRecipeCommand;

    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		service = new RecipeServiceImpl(recipeRepo, recipeCommandToRecipe, recipeToRecipeCommand);
	}
	
	@Test
	public void testGetRecipeById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		Mockito.when(recipeRepo.findById(1L)).thenReturn(recipeOptional);
		
		Recipe result = service.getRecipeById(1L);
		
		Mockito.verify(recipeRepo, times(1)).findById(Mockito.anyLong());
		Assert.assertEquals(new Long(1L), result.getId());
	}
	
	@Test
    public void testGetRecipeCommandById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepo.findById(Mockito.anyLong())).thenReturn(recipeOptional);

        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        when(recipeToRecipeCommand.convert(Mockito.any(Recipe.class))).thenReturn(recipeCommand);

        RecipeCommand commandById = service.getRecipeCommandById(1L);

        assertNotNull("Null recipe returned", commandById);
        verify(recipeRepo, times(1)).findById(1L);
        verify(recipeRepo, never()).findAll();
    }

	
	@Test
	public void getRecipes() {
		Recipe recipe = new Recipe();
		Set<Recipe> recipeData = new HashSet<>();
		recipeData.add(recipe);
		Mockito.when(service.getRecipes()).thenReturn(recipeData);
		
		Set<Recipe> recipeSet = service.getRecipes();
		
		assertEquals(recipeSet.size(), 1);
		Mockito.verify(recipeRepo, times(1)).findAll();
	}
	
	@Test
	public void testDeleteRecipeById() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        Optional<Recipe> recipeOptional = Optional.of(recipe);
        when(recipeRepo.findById(Mockito.anyLong())).thenReturn(recipeOptional);
        
		service.deleteRecipeById(2L);
		
		Mockito.verify(recipeRepo, times(1)).delete(Mockito.any(Recipe.class));
	}

}
