package com.drifai.services;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;

import com.drifai.web.controller.NotFoundException;
import com.drifai.web.model.BeerDto;

public interface BeerService {

	BeerDto getById(UUID beerId);

	BeerDto saveNewBeer(@Valid BeerDto beerDto);

	BeerDto updateNewBeer(@Valid UUID beerId, BeerDto beerDto);

}
