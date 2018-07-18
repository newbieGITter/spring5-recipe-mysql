package com.saahas.demo.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.saahas.demo.commands.IngredientCommand;
import com.saahas.demo.commands.UnitOfMeasureCommand;
import com.saahas.demo.domain.Ingredient;


public class IngredientCommandToIngredientTest {

	private static final String UOM_DESCRIPTION = "1 teaspoon";

	private static final String INGREDIENT_DESCRIPTION = "Ingredient description";

	private static final Long ID_VALUE = 1L;
	
	private IngredientCommandToIngredient converter;
	
	@Before
	public void setup() {
		converter = new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure());
	}
	
	@Test
	public void testForNull() {
		Assert.assertNull(converter.convert(null));
	}
	
	@Test
	public void testForEmptyObject() {
		Ingredient ingredient = converter.convert(new IngredientCommand());
		Assert.assertNotNull(ingredient);
		Assert.assertNull(ingredient.getId());
		Assert.assertNull(ingredient.getDescription());
	}
	
	@Test
	public void testWithNullUom() {
		IngredientCommand command = new IngredientCommand();
		command.setId(ID_VALUE);
		command.setDescription(INGREDIENT_DESCRIPTION);
		command.setRecipeId(1L);
		command.setUom(null);
		
		Ingredient ingredient = converter.convert(command);
		
		Assert.assertNotNull(ingredient);
		Assert.assertEquals(ID_VALUE, ingredient.getId());
		Assert.assertEquals(INGREDIENT_DESCRIPTION, ingredient.getDescription());
		Assert.assertNull(ingredient.getUom());
	}
	
	@Test
	public void testWithAllFieldsSet() {
		IngredientCommand command = new IngredientCommand();
		command.setId(ID_VALUE);
		command.setDescription(INGREDIENT_DESCRIPTION);
		command.setRecipeId(1L);
		command.setUom(prepareUomCommand());
		
		Ingredient ingredient = converter.convert(command);
		
		Assert.assertNotNull(ingredient);
		Assert.assertEquals(ID_VALUE, ingredient.getId());
		Assert.assertEquals(INGREDIENT_DESCRIPTION, ingredient.getDescription());
		Assert.assertNotNull(ingredient.getUom());
		Assert.assertEquals(UOM_DESCRIPTION, ingredient.getUom().getDescription());
		
	}

	private UnitOfMeasureCommand prepareUomCommand() {
		UnitOfMeasureCommand command = new UnitOfMeasureCommand();
		command.setId(ID_VALUE);
		command.setDescription(UOM_DESCRIPTION);
		return command;
	}
}
