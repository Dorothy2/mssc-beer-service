package guru.sfg.brewery.model.events;

import org.springframework.stereotype.Component;

import guru.sfg.brewery.model.BeerDto;
import lombok.RequiredArgsConstructor;

@Component
public class NewInventoryEvent extends BeerEvent {

	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}
	

}

