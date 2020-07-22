package com.drifai.events;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

import com.drifai.web.model.BeerDto;

@Data
@RequiredArgsConstructor
@Builder
public class BeerEvent implements Serializable {
	
	private static final long serialVersionUID = 5290417671032203893L;
	
	private final BeerDto beerDto;
}
