package com.saahas.demo.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.saahas.demo.commands.CategoryCommand;
import com.saahas.demo.commands.IngredientCommand;
import com.saahas.demo.commands.NotesCommand;
import com.saahas.demo.commands.RecipeCommand;
import com.saahas.demo.domain.Difficulty;
import com.saahas.demo.domain.Recipe;

public class RecipeCommandToRecipeTest {
	
	private static final String RECIPE_DIRECTIONS = "Directions for cooking Rajma Chawal";
	private static final Long ID_VALUE = 1L;
	private static final String RECIPE_SOURCE = "Source to find recipe";
	private static final String RECIPE_URL = "Url to look for recipe";
	private static final Integer COOK_TIME = 20;
	private static final Integer PREP_TIME = 15;
	private static final String RECIPE_DESCRIPTION = "This is Rajma Chawal";
	private static final Difficulty DIFFICULTY = Difficulty.MODERATE;
	private static final Integer SERVINGS = 2;

	private static final Long INGRED_ID_1 = 1L;
	private static final Long INGRED_ID_2 = 2L;
	private static final Long CAT_ID_1 = 1L;
	private static final Long CAT_ID2 = 2L;
	private static final Long NOTES_ID = 1L;
	
	private RecipeCommandToRecipe converter;
	private CategoryCommandToCategory categoryConveter;
	private IngredientCommandToIngredient ingredientConverter;
	private NotesCommandToNotes notesConverter;
	private UnitOfMeasureCommandToUnitOfMeasure uomConverter;
	
	@Before
	public void setup() {
		categoryConveter = new CategoryCommandToCategory();
		uomConverter = new UnitOfMeasureCommandToUnitOfMeasure();
		ingredientConverter = new IngredientCommandToIngredient(uomConverter);
		notesConverter = new NotesCommandToNotes();
		converter = new RecipeCommandToRecipe(categoryConveter, ingredientConverter, notesConverter);
	}
	
	@Test
	public void testWithNull() {
		Assert.assertNull(converter.convert(null));
	}
	
	@Test
	public void testWithEmptyObject() {
		Recipe recipe = converter.convert(new RecipeCommand());
		Assert.assertNotNull(recipe);
		Assert.assertNull(recipe.getId());
		Assert.assertNull(recipe.getDescription());
	}
	
	@Test
	public void testWithAllFieldsSet() {
		RecipeCommand command = prepareRecipeCommandWithAllFields();
		
		Recipe recipe = converter.convert(command);
		
		Assert.assertNotNull(recipe);
        Assert.assertEquals(ID_VALUE, recipe.getId());
        Assert.assertEquals(COOK_TIME, recipe.getCookTime());
        Assert.assertEquals(PREP_TIME, recipe.getPrepTime());
        Assert.assertEquals(RECIPE_DESCRIPTION, recipe.getDescription());
        Assert.assertEquals(DIFFICULTY, recipe.getDifficulty());
        Assert.assertEquals(RECIPE_DIRECTIONS, recipe.getDirections());
        Assert.assertEquals(SERVINGS, recipe.getServings());
        Assert.assertEquals(RECIPE_SOURCE, recipe.getSource());
        Assert.assertEquals(RECIPE_URL, recipe.getUrl());
        Assert.assertEquals(NOTES_ID, recipe.getNotes().getId());
        Assert.assertEquals(2, recipe.getCategories().size());
        Assert.assertEquals(2, recipe.getIngredients().size());
	}

	private RecipeCommand prepareRecipeCommandWithAllFields() {
		RecipeCommand command = new RecipeCommand();
		command.setId(ID_VALUE);
		command.setDescription(RECIPE_DESCRIPTION);
		command.setDifficulty(Difficulty.MODERATE);
		command.setCookTime(COOK_TIME);
		command.setPrepTime(PREP_TIME);
		command.setServings(SERVINGS);
		command.setDirections(RECIPE_DIRECTIONS);
		command.setSource(RECIPE_SOURCE);
		command.setUrl(RECIPE_URL);
		
		NotesCommand notes = new NotesCommand();
        notes.setId(NOTES_ID);

        command.setNotes(notes);

        CategoryCommand category = new CategoryCommand();
        category.setId(CAT_ID_1);

        CategoryCommand category2 = new CategoryCommand();
        category2.setId(CAT_ID2);

        command.getCategories().add(category);
        command.getCategories().add(category2);

        IngredientCommand ingredient = new IngredientCommand();
        ingredient.setId(INGRED_ID_1);

        IngredientCommand ingredient2 = new IngredientCommand();
        ingredient2.setId(INGRED_ID_2);

        command.getIngredients().add(ingredient);
        command.getIngredients().add(ingredient2);
		
		return command;
	}
	
	

}
