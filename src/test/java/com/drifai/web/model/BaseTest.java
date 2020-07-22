package com.drifai.web.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

import guru.sfg.common.web.model.BeerDto;
import guru.sfg.common.web.model.BeerStyleEnum;

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
                //.beerStyle(BeerStyleEnum.ALE)
                .beerStyle("ALE")
                .id(UUID.randomUUID())
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .price(new BigDecimal("12.99"))
                .upc("123123123123")
                .build();
    }
}