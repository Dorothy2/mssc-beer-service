package guru.sfg.common.events;

import org.springframework.stereotype.Component;

import guru.sfg.common.web.model.BeerDto;

import lombok.RequiredArgsConstructor;

@Component
public class NewInventoryEvent extends BeerEvent {

	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}
	

}

