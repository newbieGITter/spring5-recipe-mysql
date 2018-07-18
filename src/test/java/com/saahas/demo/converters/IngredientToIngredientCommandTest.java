package com.saahas.demo.converters;

import java.math.BigDecimal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.saahas.demo.commands.IngredientCommand;
import com.saahas.demo.domain.Ingredient;
import com.saahas.demo.domain.Recipe;
import com.saahas.demo.domain.UnitOfMeasure;

public class IngredientToIngredientCommandTest {

	private static final String INGREDIENT_DESCRIPTION = "Sugar";
	private static final Long ID_VALUE = 1L;
	private IngredientToIngredientCommand converter;
	
	@Before
	public void setup() {
		converter = new IngredientToIngredientCommand(new UnitOfMeasureToUnitOfMeasureCommand());
	}
	
	@Test
	public void testWithNull() {
		Assert.assertNull(converter.convert(null));
	}
	
	@Test
	public void testWithEmptyObject() {
		Ingredient ingredient = new Ingredient();
		
		Assert.assertNotNull(converter.convert(ingredient));
		Assert.assertNull(ingredient.getId());
	}
	
	@Test
	public void testWithNoRecipeAndUomObject() {
		Ingredient ingredient = prepareIngredientWithoutRecipe();
		
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		
		Assert.assertNotNull(ingredientCommand);
		Assert.assertNull(ingredientCommand.getUom());
		Assert.assertNull(ingredientCommand.getRecipeId());
	}
	
	@Test
	public void testWithAllFieldsSet() {
		Ingredient ingredient = prepareIngredientWithAllFields();
		
		IngredientCommand ingredientCommand = converter.convert(ingredient);
		
		Assert.assertNotNull(ingredientCommand);
		Assert.assertEquals(ID_VALUE, ingredientCommand.getId());
		Assert.assertEquals(INGREDIENT_DESCRIPTION, ingredientCommand.getDescription());
		Assert.assertEquals(ID_VALUE, ingredientCommand.getRecipeId());
		Assert.assertEquals(ID_VALUE, ingredientCommand.getUom().getId());
		
	}

	private Ingredient prepareIngredientWithAllFields() {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		UnitOfMeasure uom = new UnitOfMeasure();
		uom.setId(1L);
		
		Ingredient ingredient = this.prepareIngredientWithoutRecipe();
		ingredient.setRecipe(recipe);
		ingredient.setUom(uom);
		
		return ingredient;
	}

	private Ingredient prepareIngredientWithoutRecipe() {
		Ingredient ingredient = new Ingredient();
		ingredient.setId(ID_VALUE);
		ingredient.setAmount(new BigDecimal(10.00));
		ingredient.setDescription(INGREDIENT_DESCRIPTION);
		ingredient.setUom(null);
		ingredient.setRecipe(null);
		
		return ingredient;
	}
	
}
