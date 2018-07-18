package com.saahas.demo.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.saahas.demo.commands.UnitOfMeasureCommand;
import com.saahas.demo.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.saahas.demo.repository.UnitOfMeasureRepository;

@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private UnitOfMeasureRepository uomRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand;
	
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository uomRepository, UnitOfMeasureToUnitOfMeasureCommand uomToUomCommand) {
		super();
		this.uomRepository = uomRepository;
		this.uomToUomCommand = uomToUomCommand;
	}

	@Override
	public Set<UnitOfMeasureCommand> listAllUoms() {
		return StreamSupport.stream(uomRepository.findAll().spliterator(), false).map(uomToUomCommand::convert).collect(Collectors.toSet());
	}


}
