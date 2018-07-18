package com.saahas.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.saahas.demo.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
