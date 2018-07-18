package com.saahas.demo.services;

import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.saahas.demo.domain.Recipe;
import com.saahas.demo.repository.RecipeRepository;

public class ImageServiceImplTest {
	
	@Mock
	private RecipeRepository recipeRepository;
	
	private ImageService imageService;
	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		imageService = new ImageServiceImpl(recipeRepository);
	}
	
	@Test
	public void testSaveImageFile() throws IOException {
		MultipartFile multipartFile = new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Spring Boot File Upload".getBytes());
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		Mockito.when(recipeRepository.findById(1L)).thenReturn(recipeOptional);
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		imageService.saveImageFile(recipe.getId(), multipartFile);
		
        verify(recipeRepository, times(1)).save(argumentCaptor.capture());
        Recipe savedRecipe = argumentCaptor.getValue();
        assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);

	}

}
