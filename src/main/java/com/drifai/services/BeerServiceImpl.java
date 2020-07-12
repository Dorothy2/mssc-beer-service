package com.drifai.services;

import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.drifai.domain.Beer;
import com.drifai.mappers.BeerMapper;
import com.drifai.repositories.BeerRepository;
import com.drifai.web.controller.NotFoundException;
import com.drifai.web.model.BeerDto;
import com.drifai.web.model.BeerPagedList;
import com.drifai.web.model.BeerStyleEnum;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service("beerService")
public class BeerServiceImpl implements BeerService {

	private final BeerRepository beerRepository;
	private final BeerMapper beerMapper;
	@Override
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
	    beer.setBeerStyle(beerDto.getBeerStyle().name());
	    beer.setPrice(beerDto.getPrice());
	    beer.setUpc(beerDto.getUpc());
	    
	    return beerMapper.beerToBeerDto(beerRepository.save(beer));
	}
	
	 @Override
	    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, boolean includeInventory, PageRequest pageRequest) {

	        BeerPagedList beerPagedList;
	        Page<Beer> beerPage;

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
