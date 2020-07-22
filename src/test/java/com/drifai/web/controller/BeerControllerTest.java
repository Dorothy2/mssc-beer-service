package com.drifai.web.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.snippet.Attributes.key;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.drifai.bootstrap.BeerLoader;
import com.drifai.services.BeerService;
import guru.sfg.common.web.model.BeerDto;
import guru.sfg.common.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(RestDocumentationExtension.class)
// Dev server example
//@AutoConfigureRestDocs(uriScheme="https", uriHost="dev.springframework.guru", uriPort=80)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "com.drifai.web.mappers")
class BeerControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	ObjectMapper objectMapper;
		
	@MockBean
	BeerService beerService;
	
	@Test
    void getBeerById() throws Exception {
		given(beerService.getById(any(), anyBoolean())).willReturn(getValidBeerDTO());
//		(Optional.of(Beer.builder().build()));
		mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
				// just example for restDocs, this query param does not
				// exist on controller
				.param("iscold", "yes")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
			    .andDo(document("v1/beer-get", 
			    		pathParameters(
		                        parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
			    		requestParameters(
			    				parameterWithName("iscold").description("Is beer cold? Query Param")
			    		),
			    		responseFields(
			    				fieldWithPath("beerId").description("id of beer"),
			    				fieldWithPath("version").description("code version"),
			    				fieldWithPath("createdDate").description("date created"),
			    				fieldWithPath("lastModifiedDate").description("date updated"),
			    				fieldWithPath("beerName").description("beer name"),
			    				fieldWithPath("beerStyle").description("beer style"),
			    				fieldWithPath("upc").description("UPC"),
			    				fieldWithPath("price").description("price"),
			    				fieldWithPath("quantityOnHand").description("inventory quantity on hand")
			    		)));
	}
	
	@Test
    void saveNewBeer() throws Exception {

		BeerDto beerDto= getValidBeerDTO();
        
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        
        // set up Mockito
        given(beerService.saveNewBeer(any())).willReturn(getValidBeerDTO());
        
        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(post("/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-post",
                		requestFields(
                				fields.withPath("beerId").ignored(),
                				fields.withPath("version").ignored(),
                				fields.withPath("createdDate").ignored(),
                				fields.withPath("lastModifiedDate").ignored(),
                				fields.withPath("beerName").description("beer name"),
                				fields.withPath("beerStyle").description("beer style"),
                				fields.withPath("upc").description("UPC"),
                				fields.withPath("price").description("price"),
                				fields.withPath("quantityOnHand").ignored()
				)));
    }
	
	 @Test
	    void updateBeerById() throws Exception {
		   // set up Mockito
		 	given(beerService.updateBeer(any(), any())).willReturn(getValidBeerDTO());
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
	        		.beerStyle("PALE ALE")
	        		//.beerStyle(BeerStyleEnum.PALE_ALE)
	        		.price(new BigDecimal(1.25))
	        		.upc(BeerLoader.BEER_1_UPC)
	        		.build();
	        return beerDto;
	 }
	 
	 private static class ConstrainedFields {
		 
		 private final ConstraintDescriptions constraintDescriptions;
		 
		 ConstrainedFields(Class<?> input) {
			 this.constraintDescriptions = new ConstraintDescriptions(input);
		 }
		 
		 //Uses reflection to look at constraints annotations
		 private FieldDescriptor withPath(String path) {
	            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
	                    .collectionToDelimitedString(this.constraintDescriptions
	                            .descriptionsForProperty(path), ". ")));
	        }
	 }

}
