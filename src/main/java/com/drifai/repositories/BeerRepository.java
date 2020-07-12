package com.drifai.repositories;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.drifai.domain.Beer;
import com.drifai.web.model.BeerStyleEnum;

/**
 * 
 * @author drifai
 * 
 * Implementation generated by Spring Data
 *
 */
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {
	 	Page<Beer> findAllByBeerName(String beerName, Pageable pageable);

	    Page<Beer> findAllByBeerStyle(BeerStyleEnum beerStyle, Pageable pageable);

	    Page<Beer> findAllByBeerNameAndBeerStyle(String beerName, BeerStyleEnum beerStyle, Pageable pageable);
}
