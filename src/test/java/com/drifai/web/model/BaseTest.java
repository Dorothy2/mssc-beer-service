package com.drifai.web.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.drifai.domain.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {
	@Autowired
	ObjectMapper objectMapper;
	
    BeerDto getDto(){
        return  BeerDto.builder()
        		.id(UUID.randomUUID())
                .beerName("BeerName")
                .beerStyle(BeerStyleEnum.ALE)
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .myLocalDate(LocalDate.now())
                .price(new BigDecimal("12.99"))
                .upc(123123123123L)
                .build();
    }
}