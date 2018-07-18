package com.saahas.demo.converters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.saahas.demo.commands.CategoryCommand;
import com.saahas.demo.domain.Category;

public class CategoryToCategoryCommandTest {

	private static final String CATEGORY_DESCRIPTION = "category description";
	private static final Long ID_VALUE = 1L;
	private CategoryToCategoryCommand converter;
	
	@Before
	public void setup() {
		converter = new CategoryToCategoryCommand();
	}
	
	@Test
	public void testForNull() {
		Assert.assertNull(converter.convert(null));
	}
	
	@Test
	public void testForEmptyObject() {
		CategoryCommand categoryCommand = converter.convert(new Category());
		
		Assert.assertNotNull(categoryCommand);
		Assert.assertNull(categoryCommand.getId());
		Assert.assertNull(categoryCommand.getDescription());
	}
	
	@Test
	public void testWithRealObject() {
		Category category = prepareCategoryObject();
		
		CategoryCommand categoryCommand = converter.convert(category);
		
		Assert.assertNotNull(categoryCommand);
		Assert.assertEquals(ID_VALUE, categoryCommand.getId());
		Assert.assertEquals(CATEGORY_DESCRIPTION, categoryCommand.getDescription());
	}

	private Category prepareCategoryObject() {
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(CATEGORY_DESCRIPTION);
		
		return category;
	}
}
