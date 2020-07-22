package com.drifai.web.model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import guru.sfg.common.web.model.BeerDto;
import guru.sfg.common.web.model.BeerStyleEnum;


import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("snake")
@Slf4j
@JsonTest
class BeerDtoSnakeTest extends BaseTest {
	
	

	@Test
	void testSerializeDto() throws JsonProcessingException {
		BeerDto beerDto = getDto();
		String jsonString = objectMapper.writeValueAsString(beerDto);
		
		log.debug("Beer DTO serialize: " + jsonString);
	}
	
	@Test
	void testDeserializeDto() throws IOException {
		String jsonString = "{\"id\":\"3306d39a-0299-4344-a65f-5f1c0cc3b0db\",\"version\":null,\"createdDate\":\"2020-07-09T07:38:54.7506416-04:00\",\"lastModifiedDate\":\"2020-07-09T07:38:54.7646048-04:00\",\"beerName\":\"BeerName\",\"beerStyle\":\"ALE\",\"upc\":123123123123,\"price\":12.99,\"quantityOnHand\":null}";
	    BeerDto beerDto = objectMapper.readValue(jsonString, BeerDto.class);
	    
	    log.debug("Beer DTO de-serialize: " + beerDto.toString());
	}

}
