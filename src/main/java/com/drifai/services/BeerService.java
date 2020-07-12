package com.drifai.services;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import com.drifai.web.controller.NotFoundException;
import com.drifai.web.model.BeerDto;
import com.drifai.web.model.BeerPagedList;
import com.drifai.web.model.BeerStyleEnum;

public interface BeerService {

	BeerDto getById(UUID beerId, boolean includeInventory) throws NotFoundException;

	BeerDto saveNewBeer(@Valid BeerDto beerDto);

	BeerDto updateBeer(@Valid UUID beerId, BeerDto beerDto) throws NotFoundException;

	BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, boolean includeInventory, PageRequest pageRequest);

}
