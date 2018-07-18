package com.saahas.demo.controller;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.services.ImageService;
import com.saahas.demo.services.RecipeService;

public class ImageControllerTest {

	private ImageController controller;
	
	@Mock
	private ImageService imageService;
	
	@Mock
	private RecipeService recipeService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		controller = new ImageController(imageService, recipeService);
		
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void testGetImageForm() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		
		Mockito.when(recipeService.getRecipeCommandById(1L)).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/1/image"))
				.andExpect(status().isOk())
				.andExpect(model().attributeExists("recipe"));
	
		Mockito.verify(recipeService, Mockito.times(1)).getRecipeCommandById(1L);
	}
	
	@Test
	public void testRenderImageFromDB() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);
		Byte[] imageBytes = getImageBytes();
		recipeCommand.setImage(imageBytes);
		
		Mockito.when(recipeService.getRecipeCommandById(1L)).thenReturn(recipeCommand);
		
		
		MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeimage"))
					.andExpect(status().isOk())
					.andReturn().getResponse();
		
		byte[] reponseBytes = response.getContentAsByteArray();

        assertEquals(imageBytes.length, reponseBytes.length);
		
	}

	private Byte[] getImageBytes() {
		String imageFileStr = "This is an image file";
		
		Byte[] bytesBoxed = new Byte[imageFileStr.getBytes().length];
		int i = 0;
		
		for(byte primByte: imageFileStr.getBytes()){
			bytesBoxed[i++] = primByte;
		}
		return bytesBoxed;
	}
	
}
