package guru.sfg.common.events;

import com.drifai.web.model.BeerDto;

import lombok.RequiredArgsConstructor;

public class NewInventoryEvent extends BeerEvent {

	public NewInventoryEvent(BeerDto beerDto) {
		super(beerDto);
	}
	

}

