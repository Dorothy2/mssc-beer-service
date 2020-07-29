package com.drifai.services;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import com.drifai.web.controller.NotFoundException;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;

public interface BeerService {

	BeerDto getById(UUID beerId, boolean includeInventory) throws NotFoundException;
	
	BeerDto getByUPC(String upc, boolean includeInventory) throws NotFoundException;

	BeerDto saveNewBeer(@Valid BeerDto beerDto);

	BeerDto updateBeer(@Valid UUID beerId, BeerDto beerDto) throws NotFoundException;

	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, boolean includeInventory, PageRequest pageRequest);

}
