package com.saahas.demo.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.saahas.demo.commands.NotesCommand;
import com.saahas.demo.domain.Notes;
import com.saahas.demo.domain.Recipe;


public class NotesToNotesCommandTest {

	private static final String RECIPE_NOTES = "This is a recipe note";
	private static final Long ID_VALUE = 1L;
	private NotesToNotesCommand converter;
	
	@Before
	public void setup() {
		converter = new NotesToNotesCommand();
	}
	
	@Test
	public void testWithNull() {
		Assert.assertNull(converter.convert(null));
	}
	
	@Test
	public void testWithEmptyObject() {
		NotesCommand notesCommand = converter.convert(new Notes());
		
		Assert.assertNotNull(notesCommand);
		Assert.assertNull(notesCommand.getId());
	}
	
	@Test
	public void testWithAllFieldsSet() {
		Notes notes= prepareNotesWithAllFieldsSet();
		
		NotesCommand notesCommand = converter.convert(notes);
		
		Assert.assertNotNull(notesCommand);
		Assert.assertEquals(ID_VALUE, notesCommand.getId());
		Assert.assertEquals(RECIPE_NOTES, notesCommand.getRecipeNotes());
	}

	private Notes prepareNotesWithAllFieldsSet() {
		Recipe recipe = new Recipe();
		recipe.setId(ID_VALUE);
		
		Notes notes = new Notes();
		notes.setId(ID_VALUE);
		notes.setRecipeNotes(RECIPE_NOTES);
		notes.setRecipe(recipe);
		
		return notes;
	}
}
