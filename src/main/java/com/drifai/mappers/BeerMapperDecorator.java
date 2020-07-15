package com.drifai.mappers;

import org.springframework.beans.factory.annotation.Autowired;

import com.drifai.domain.Beer;
import com.drifai.services.inventory.BeerInventoryService;
import com.drifai.web.model.BeerDto;

public abstract class BeerMapperDecorator implements BeerMapper{
	@Autowired
	private BeerInventoryService beerInventoryService;
	@Autowired
	private BeerMapper mapper;
	
//	@Autowired
//	public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
//		this.beerInventoryService = beerInventoryService;
//	}
//	
//	@Autowired
//	public void setMapper(BeerMapper mapper) {
//		this.mapper = mapper;
//	}
	
	@Override
	public BeerDto beerToBeerDtoWithInventory(Beer beer) {
		BeerDto dto = mapper.beerToBeerDto(beer);		
		dto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beer.getId()));
		
		return dto;
	}
	
	public BeerDto beerToBeerDto(Beer beer) {
		return(mapper.beerToBeerDto(beer));
	}
	
	@Override
	public Beer beerDtoToBeer(BeerDto beerDto) {
		return mapper.beerDtoToBeer(beerDto);
	}

}
