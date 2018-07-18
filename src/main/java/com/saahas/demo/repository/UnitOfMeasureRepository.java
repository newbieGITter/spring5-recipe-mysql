package com.saahas.demo.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.saahas.demo.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findByDescription(String description);
}
