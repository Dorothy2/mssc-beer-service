package com.drifai.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.drifai.web.model.BeerDto;
import com.drifai.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
    void getBeerById() throws Exception {
		mockMvc.perform(get("/api/v1/beer/" + UUID.randomUUID().toString())
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
	
	@Test
    void saveNewBeer() throws Exception {

		BeerDto beerDto= getValidBeerDTO();
        
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }
	
	 @Test
	    void updateBeerById() throws Exception {
		    // mock a beer object which will be used to update with randomUUID
		 	BeerDto beerDto= getValidBeerDTO();
	        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

	        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
	                .contentType(MediaType.APPLICATION_JSON)
	                .content(beerDtoJson))
	                .andExpect(status().isNoContent());
	    }
	 
	 @ExceptionHandler(ConstraintViolationException.class)
	 public ResponseEntity<List<String>> validationErrorHandler(ConstraintViolationException e) {
		 List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
		 e.getConstraintViolations().forEach(constraintViolation -> {
			 errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
		 });
		 return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
	 }
	 
	 private BeerDto getValidBeerDTO() {
		// mock a beer Dto
	        BeerDto beerDto = BeerDto.builder()
	        		.beerName("Beer 1")
	        		.beerStyle(BeerStyleEnum.PALE_ALE)
	        		.price(new BigDecimal(1.25))
	        		.upc(12345L)
	        		.build();
	        return beerDto;
	 }

}
