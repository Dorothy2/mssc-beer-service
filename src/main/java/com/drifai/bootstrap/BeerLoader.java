package com.drifai.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.drifai.domain.Beer;
import com.drifai.repositories.BeerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BeerLoader implements CommandLineRunner {
	private final BeerRepository beerRepository;
	
	public BeerLoader(BeerRepository beerRepository) {
		this.beerRepository = beerRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		loadBeerObjects();
	}
	
	private void loadBeerObjects() {
		if(beerRepository.count() == 0) {
			Beer b1 = Beer.builder()
					.beerName("Mango Bobs")
					.beerStyle("IPA")
					.quantityToBrew(200)
					.minOnHand(12)
					.price(new BigDecimal(12.95))
					.upc(337010000001L)
					.build();
			
			Beer b2 = Beer.builder()
					.beerName("Galaxy Cat")
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.minOnHand(12)
					.price(new BigDecimal(11.95))
					.upc(337010000002L)
					.build();
			
			beerRepository.save(b1);
			beerRepository.save(b2);
			
			log.debug("Number of beer records created: " + beerRepository.count());
		}
	}
}
