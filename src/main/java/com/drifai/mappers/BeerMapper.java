package com.drifai.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

import com.drifai.domain.Beer;
import guru.sfg.common.web.model.BeerDto;

@Mapper(uses = DateMapper.class)
@DecoratedWith(BeerMapperDecorator.class)
public interface BeerMapper {
	
	BeerDto beerToBeerDtoWithInventory(Beer beer);
	
	BeerDto beerToBeerDto(Beer beer);
	
	Beer beerDtoToBeer(BeerDto dto);

}
