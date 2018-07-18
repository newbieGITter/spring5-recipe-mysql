package com.saahas.demo.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.saahas.demo.commands.NotesCommand;
import com.saahas.demo.domain.Notes;

public class NotesCommandToNotesTest {

	private static final Long ID_VALUE = 1L;
	private static final String RECIPE_NOTES = "This is what we have in recipe";
	private NotesCommandToNotes converter;
	
	@Before
	public void setup() {
		converter = new NotesCommandToNotes();
	}
	
	@Test
	public void testWithNull() {
		Assert.assertNull(converter.convert(null));
	}
	
	@Test
	public void testWithEmptyObject() {
		Notes notes = converter.convert(new NotesCommand());
		
		Assert.assertNotNull(notes);
		Assert.assertNull(notes.getId());
	}
	
	@Test
	public void testWithFieldsSet() {
		NotesCommand notesCommand = prepareNotesWithAllFields();
		
		Notes notes = converter.convert(notesCommand);
		
		Assert.assertNotNull(notes);
		Assert.assertEquals(ID_VALUE, notes.getId());
		Assert.assertEquals(RECIPE_NOTES, notes.getRecipeNotes());
	}

	private NotesCommand prepareNotesWithAllFields() {
		NotesCommand notesCommand = new NotesCommand();
		notesCommand.setId(ID_VALUE);
		notesCommand.setRecipeNotes(RECIPE_NOTES);
		
		return notesCommand;
	}
}
