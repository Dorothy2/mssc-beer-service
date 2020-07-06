package com.drifai.mappers;

import org.mapstruct.Mapper;

import com.drifai.domain.Beer;
import com.drifai.web.model.BeerDto;

@Mapper(uses = DateMapper.class)
public interface BeerMapper {
	
	BeerDto beerToBeerDto(Beer beer);
	
	Beer beerDtoToBeer(BeerDto dto);
	

}
