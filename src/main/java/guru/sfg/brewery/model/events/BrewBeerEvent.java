package guru.sfg.brewery.model.events;

import org.springframework.stereotype.Component;

import guru.sfg.brewery.model.BeerDto;
import lombok.NoArgsConstructor;

@Component
@NoArgsConstructor
public class BrewBeerEvent extends BeerEvent {

	public BrewBeerEvent(BeerDto beerDto) {
		super(beerDto);
	}

	
}
