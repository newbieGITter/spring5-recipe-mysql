package com.saahas.demo.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

	private Category category;
	
	@Before
	public void setup() {
		category = new Category();
	}
	
	@Test
	public void getId() {
		Long idValue = 4l;
		
		category.setId(idValue);
		
		Assert.assertNotNull(category);
		Assert.assertEquals("Category id is incorrect", idValue, category.getId());
		
	}
	
}
