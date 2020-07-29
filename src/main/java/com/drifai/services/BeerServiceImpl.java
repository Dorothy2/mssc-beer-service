package com.drifai.services;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.drifai.domain.Beer;
import com.drifai.mappers.BeerMapper;
import com.drifai.repositories.BeerRepository;
import com.drifai.web.controller.NotFoundException;

import guru.sfg.brewery.model.BeerDto;
import guru.sfg.brewery.model.BeerPagedList;
import guru.sfg.brewery.model.BeerStyleEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service("beerService")
public class BeerServiceImpl implements BeerService {

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	
	@Override
	@Cacheable(cacheNames = "beerUPCCache", key="#upc", condition = "#includeInventory == false")
	public BeerDto getByUPC(String upc, boolean includeInventory) throws NotFoundException {
		if(includeInventory) {
			return beerMapper.beerToBeerDtoWithInventory(beerRepository.findByUpc(upc).orElseThrow(NotFoundException::new ));
		} else {
			return beerMapper.beerToBeerDto(beerRepository.findByUpc(upc).orElseThrow(NotFoundException::new));
		}
	}


	@Override
	@Cacheable(cacheNames = "beerCache", key = "#beerId", condition = "#includeInventory == false")
	public BeerDto getById(UUID beerId, boolean includeInventory) throws NotFoundException {
		if(includeInventory) {
			return beerMapper.beerToBeerDtoWithInventory(beerRepository.findById(beerId).orElseThrow(NotFoundException::new ));
		} else {
			return beerMapper.beerToBeerDto(beerRepository.findById(beerId).orElseThrow(NotFoundException::new));
		}
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
	    //beer.setBeerStyle(beerDto.getBeerStyle().name());
	    beer.setBeerStyle(beerDto.getBeerStyle());
	    beer.setPrice(beerDto.getPrice());
	    beer.setUpc(beerDto.getUpc());
	    
	    return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}
	
	 @Override
	 @Cacheable(cacheNames = "beerListCache", condition = "#includeInventory == false")
     public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, boolean includeInventory, PageRequest pageRequest) {

        BeerPagedList beerPagedList;
        Page<Beer> beerPage;
        
        log.debug("I was called.");

        if (!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search both
            beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);
        } else if (!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)) {
            //search beer_service name
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);
        } else if (StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)) {
            //search beer_service style
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }
        if(includeInventory) {
        	beerPagedList = new BeerPagedList(beerPage
	                .getContent()
	                .stream()
	                .map(beerMapper::beerToBeerDtoWithInventory)
	                .collect(Collectors.toList()),
	                PageRequest
	                        .of(beerPage.getPageable().getPageNumber(),
	                                beerPage.getPageable().getPageSize()),
	                beerPage.getTotalElements());
        } else {
        	beerPagedList = new BeerPagedList(beerPage
	                .getContent()
	                .stream()
	                .map(beerMapper::beerToBeerDto)
	                .collect(Collectors.toList()),
	                PageRequest
	                        .of(beerPage.getPageable().getPageNumber(),
	                                beerPage.getPageable().getPageSize()),
	                beerPage.getTotalElements());
        }

        

        return beerPagedList;
    }
}
