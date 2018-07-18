package com.saahas.demo.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.saahas.demo.domain.Recipe;
import com.saahas.demo.services.RecipeService;

public class IndexControllerTest {

	private IndexController indexController;
	
	@Mock
	private RecipeService recipeService;
	
	@Mock
	private Model model;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);
	}
	
	@Test
	public void testMockMvc() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(indexController).build();
		
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(view().name("index"));
	}
	
	@Test
	public void test() {
		String viewName = indexController.getIndexPage(model);

        assertEquals("index", viewName);
        Mockito.verify(recipeService, times(1)).getRecipes();
        Mockito.verify(model, times(1)).addAttribute(Mockito.eq("recipes"), Mockito.anySet());
	}
	
	@Test
	public void getIndexPage() {
		Set<Recipe> recipeSet = new HashSet<Recipe>();
		recipeSet.add(new Recipe());
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		recipeSet.add(recipe);
		
		Mockito.when(recipeService.getRecipes()).thenReturn(recipeSet);
		
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		String viewName = indexController.getIndexPage(model);
		
		Assert.assertEquals("index", viewName);
		Mockito.verify(recipeService, times(1)).getRecipes();
		Mockito.verify(model, times(1)).addAttribute(Mockito.eq("recipes"), argumentCaptor.capture());
		Set<Recipe> setInController = argumentCaptor.getValue();
		Assert.assertEquals(2, setInController.size());
		
		
		
		
		
		
	}

}
