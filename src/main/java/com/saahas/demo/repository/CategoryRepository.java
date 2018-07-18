package com.saahas.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.saahas.demo.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

	Optional<Category> findByDescription(String description);
}
