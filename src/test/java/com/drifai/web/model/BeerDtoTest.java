package com.drifai.web.model;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;

import guru.sfg.common.web.model.BeerDto;
import guru.sfg.common.web.model.BeerStyleEnum;


@Slf4j
@JsonTest
class BeerDtoTest extends BaseTest {
	
	

	@Test
	void testSerializeDto() throws JsonProcessingException {
		BeerDto beerDto = getDto();
		String jsonString = objectMapper.writeValueAsString(beerDto);
		
		log.debug("Beer DTO serialize: " + jsonString);
	}
	
	@Test
	void testDeserializeDto() throws IOException {
		String jsonString = "{\"beerId\":\"3306d39a-0299-4344-a65f-5f1c0cc3b0db\",\"version\":null,\"createdDate\":\"2020-07-09T13:35:05-0400\",\"lastModifiedDate\":\"2020-07-09T13:35:05-0400\",\"beerName\":\"BeerName\",\"beerStyle\":\"ALE\",\"upc\":123123123123,\"price\":12.99,\"quantityOnHand\":null}";
	    BeerDto beerDto = objectMapper.readValue(jsonString, BeerDto.class);
	    
	    log.debug("Beer DTO de-serialize: " + beerDto.toString());
	}

}
