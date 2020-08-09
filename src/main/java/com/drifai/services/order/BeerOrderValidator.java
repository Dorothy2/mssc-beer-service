package com.drifai.services.order;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Component;

import com.drifai.repositories.BeerRepository;

import guru.sfg.brewery.model.BeerOrderDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerOrderValidator {

	private final BeerRepository beerRepository;
	
	public Boolean validateOrder(BeerOrderDto beerOrder) {
			
		AtomicInteger beersNotFound = new AtomicInteger();
		
		beerOrder.getBeerOrderLines().forEach(orderLine -> {
			if(beerRepository.findByUpc(orderLine.getUpc()) == null) {
				log.debug("UPC not found for: " + orderLine.getUpc());
				beersNotFound.incrementAndGet();
			}
		});
		
		return beersNotFound.get() == 0;
	}
}
