package com.saahas.demo.controller;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.saahas.demo.commands.IngredientCommand;
import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.services.IngredientService;
import com.saahas.demo.services.RecipeService;
import com.saahas.demo.services.UnitOfMeasureService;

public class IngredientControllerTest {

	@Mock
	private RecipeService recipeService;
	@Mock
	private IngredientService ingredientService;
	@Mock
	private UnitOfMeasureService uomService;
	
	private MockMvc mockMvc;
	private IngredientController controller;
	
	@Before
	public void setup() {
        MockitoAnnotations.initMocks(this);
		controller = new IngredientController(recipeService, ingredientService, uomService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testListIngredient() throws Exception {
		//Given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		Mockito.when(recipeService.getRecipeCommandById(Mockito.anyLong())).thenReturn(recipeCommand);
		
		// When
		mockMvc.perform(get("/recipe/1/ingredients"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
		
		// Then
        Mockito.verify(recipeService, times(1)).getRecipeCommandById(Mockito.anyLong());
	}
	
	@Test
	public void testShowIngredient() throws Exception {
		//Given
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		Mockito.when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingredientCommand);
		
		// When
		mockMvc.perform(get("/recipe/1/ingredient/1/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
		
		// Then
        Mockito.verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong());
	}
	
	@Test
	public void testNewIngredient() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		Mockito.when(recipeService.getRecipeCommandById(1L)).thenReturn(recipeCommand);
		Mockito.when(uomService.listAllUoms()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/1/ingredient/new"))
					.andExpect(status().isOk())
					.andExpect(view().name("recipe/ingredient/ingredientform"))
					.andExpect(model().attributeExists("ingredient"))
					.andExpect(model().attributeExists("uomList"));
		
		Mockito.verify(recipeService, times(1)).getRecipeCommandById(1L);
		
	}
	
	@Test
	public void testUpdateRecipeIngredient() throws Exception {
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(1L);
		Mockito.when(ingredientService.findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(ingredientCommand);
		Mockito.when(uomService.listAllUoms()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/1/ingredient/1/update"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("ingredient"))
				.andExpect(model().attributeExists("uomList"))
				.andExpect(view().name("recipe/ingredient/ingredientform"));
		
		Mockito.verify(ingredientService, times(1)).findByRecipeIdAndIngredientId(Mockito.anyLong(), Mockito.anyLong());
	}
	
	@Test
	public void testDeleteRecipeIngredient() throws Exception {
		mockMvc.perform(get("/recipe/1/ingredient/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/recipe/1/ingredients"));
		
		Mockito.verify(ingredientService, times(1)).deleteById(Mockito.anyLong(), Mockito.anyLong());
	}
}
