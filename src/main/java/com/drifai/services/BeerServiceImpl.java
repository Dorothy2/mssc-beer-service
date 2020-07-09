package com.drifai.services;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.drifai.domain.Beer;
import com.drifai.mappers.BeerMapper;
import com.drifai.repositories.BeerRepository;
import com.drifai.web.controller.NotFoundException;
import com.drifai.web.model.BeerDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("beerService")
public class BeerServiceImpl implements BeerService {

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	@Override
	public BeerDto getById(UUID beerId) throws NotFoundException {
		return beerMapper.beerToBeerDto(beerRepository.findById(beerId)
				.orElseThrow(NotFoundException::new));
	}

	@Override
	public BeerDto saveNewBeer(@Valid BeerDto beerDto) {
		return beerMapper.beerToBeerDto(beerRepository.save(beerMapper.beerDtoToBeer(beerDto)));
	}

	@Override
	public BeerDto updateBeer(@Valid UUID beerId, BeerDto beerDto) throws NotFoundException {
	    Beer beer = beerRepository.findById(beerId)
	    		.orElseThrow(NotFoundException::new);
	    beer.setBeerName(beerDto.getBeerName());
	    beer.setBeerStyle(beerDto.getBeerStyle());
	    beer.setPrice(beerDto.getPrice());
	    beer.setUpc(beerDto.getUpc());
	    
	    return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}

}
