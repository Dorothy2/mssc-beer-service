package com.drifai.events;

import com.drifai.web.model.BeerDto;

public class BrewBeerEvent extends BeerEvent {

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}
}
