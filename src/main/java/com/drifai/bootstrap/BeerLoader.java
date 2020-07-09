package com.drifai.bootstrap;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.drifai.domain.Beer;
import com.drifai.domain.BeerStyleEnum;
import com.drifai.repositories.BeerRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BeerLoader implements CommandLineRunner {
	private final BeerRepository beerRepository;
	
	public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";
	
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
					.beerStyle(BeerStyleEnum.IPA)
					.quantityToBrew(200)
					.minOnHand(12)
					.price(new BigDecimal(12.95))
					.upc(BEER_1_UPC)
					.build();
			
			Beer b2 = Beer.builder()
					.beerName("Galaxy Cat")
					.beerStyle(BeerStyleEnum.PALE_ALE)
					.quantityToBrew(200)
					.minOnHand(12)
					.price(new BigDecimal(11.95))
					.upc(BEER_2_UPC)
					.build();
			
			Beer b3 = Beer.builder()
					.beerName("No Hammers on the Bar")
					.beerStyle(BeerStyleEnum.ALE)
					.quantityToBrew(200)
					.minOnHand(12)
					.price(new BigDecimal(11.95))
					.upc(BEER_3_UPC)
					.build();
			
			beerRepository.save(b1);
			beerRepository.save(b2);
			beerRepository.save(b3);
			
			log.debug("Number of beer records created: " + beerRepository.count());
		}
	}
}
