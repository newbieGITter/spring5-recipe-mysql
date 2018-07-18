package com.saahas.demo.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.saahas.demo.commands.CategoryCommand;
import com.saahas.demo.domain.Category;

public class CategoryCommandToCategoryTest {

	private static final Long ID_VALUE = new Long(1L);
	private CategoryCommandToCategory converter;
	
	@Before
	public void setup(){
		converter = new CategoryCommandToCategory();
	}
	
	@Test
	public void testForNull() {
		Assert.assertNull(converter.convert(null));
	}

	@Test
	public void setInDomainObject() {
		Assert.assertNotNull(converter.convert(new CategoryCommand()));
	}
	
	@Test
	public void setAllFieldsInDomainObject() {
		CategoryCommand command = new CategoryCommand();
		command.setId(ID_VALUE);
		command.setDescription("command description");
		
		Category category = converter.convert(command);
		
		Assert.assertEquals(ID_VALUE, category.getId());
		Assert.assertEquals("command description", category.getDescription());
		
	}
}
