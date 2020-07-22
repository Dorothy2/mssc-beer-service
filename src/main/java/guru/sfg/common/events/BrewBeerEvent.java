package guru.sfg.common.events;

import org.springframework.stereotype.Component;

import guru.sfg.common.web.model.BeerDto;

import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}

	
}
