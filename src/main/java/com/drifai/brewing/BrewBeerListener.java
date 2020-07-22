package com.drifai.brewing;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.drifai.config.JmsConfig;
import com.drifai.domain.Beer;
import guru.sfg.common.events.BrewBeerEvent;
import guru.sfg.common.events.NewInventoryEvent;
import com.drifai.repositories.BeerRepository;
import guru.sfg.common.web.model.BeerDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BrewBeerListener {
	
	private final BeerRepository beerRepository;
	private final JmsTemplate jmsTemplate;

	@Transactional
	@JmsListener(destination = JmsConfig.BREWING_REQUEST_QUEUE)
	public void listen(BrewBeerEvent event) {
		BeerDto beerDto = event.getBeerDto();
		
		Beer beer = beerRepository.getOne(beerDto.getId());
		
		// show communication between microservices
		// real-world would have process controls tracked in db
		beerDto.setQuantityOnHand(beer.getQuantityToBrew());
		
		NewInventoryEvent newInventoryEvent = new NewInventoryEvent(beerDto);
		log.debug("Brewed beer " + beer.getId() + " QOH " + beerDto.getQuantityOnHand());
		
		
		jmsTemplate.convertAndSend(JmsConfig.NEW_INVENTORY_QUEUE, newInventoryEvent);
	}
}
