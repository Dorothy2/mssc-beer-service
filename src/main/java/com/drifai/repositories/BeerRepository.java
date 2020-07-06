package com.drifai.repositories;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.drifai.domain.Beer;

public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
