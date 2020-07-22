package com.drifai.events;

import com.drifai.web.model.BeerDto;

public class NewInventoryEvent extends BeerEvent {

	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}
}
