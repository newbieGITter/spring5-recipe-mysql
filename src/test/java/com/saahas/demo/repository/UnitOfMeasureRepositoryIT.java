package com.saahas.demo.repository;

import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
 
import com.saahas.demo.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {

	@Autowired
	private UnitOfMeasureRepository uomRepo;
	
	@Test
	public void testFindByDescription() {
		Optional<UnitOfMeasure> uomOptional = uomRepo.findByDescription("Teaspoon");
		
		Assert.assertEquals("Teaspoon", uomOptional.get().getDescription());
	}
	
	@Test
	public void testFindByDescriptionCup() {
		Optional<UnitOfMeasure> uomOptional = uomRepo.findByDescription("Cup");
		
		Assert.assertEquals("Cup", uomOptional.get().getDescription());
	}

}
