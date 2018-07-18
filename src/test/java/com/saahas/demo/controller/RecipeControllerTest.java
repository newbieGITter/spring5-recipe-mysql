	package com.saahas.demo.controller;

import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.domain.Recipe;
import com.saahas.demo.services.RecipeService;

public class RecipeControllerTest {
	
	private RecipeController controller;
	
	@Mock 
	private RecipeService recipeService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new RecipeController(recipeService);
		
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testGetRecipeById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1l);
		Mockito.when(recipeService.getRecipeById(1l)).thenReturn(recipe);

		mockMvc.perform(get("/recipe/1/show"))
					.andExpect(status().isOk())
					.andExpect(view().name("recipe/show"))
					.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));;
	}
	
	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		Mockito.when(recipeService.saveRecipeCommand(Mockito.any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe")
						.contentType(MediaType.APPLICATION_FORM_URLENCODED)
						.param("id", "")
						.param("description", "some string"))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/recipe/1/show"));
		
	}
	
	@Test
	public void testUpdateRecipe() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		Mockito.when(recipeService.getRecipeCommandById(1L)).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/update"))
						.andExpect(status().isOk())
						.andExpect(view().name("recipe/recipeform"))
						.andExpect(MockMvcResultMatchers.model().attributeExists("recipe"));
		
	}
	
	@Test
	public void testDeleteRecipe() throws Exception {
		mockMvc.perform(get("/recipe/1/delete"))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		
		Mockito.verify(recipeService, times(1)).deleteRecipeById(Mockito.anyLong());
	}

}
