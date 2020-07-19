package com.drifai.bootstrap;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.drifai.domain.Beer;
//import com.drifai.domain.BeerStyleEnum;
import com.drifai.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class BeerLoader implements CommandLineRunner {
	private final BeerRepository beerRepository;
	
	public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

	@Override
	public void run(String... args) throws Exception {
		if(beerRepository.count() == 0) {
          loadBeerObjects();
		}
	}
	
	private void loadBeerObjects() {
		if(beerRepository.count() == 0) {
			Beer b1 = Beer.builder()
					.beerName("Mango Bobs")				
					//.beerStyle(BeerStyleEnum.IPA)
					.beerStyle("IPA")
					.quantityToBrew(200)
					.minOnHand(12)
					.price(new BigDecimal(12.95))
					.upc(BEER_1_UPC)
					.build();
			
			Beer b2 = Beer.builder()
					.beerName("Galaxy Cat")
					//.beerStyle(BeerStyleEnum.PALE_ALE)
					.beerStyle("PALE_ALE")
					.quantityToBrew(200)
					.minOnHand(12)
					.price(new BigDecimal(11.95))
					.upc(BEER_2_UPC)
					.build();
			
			Beer b3 = Beer.builder()
					.beerName("No Hammers on the Bar")
					//.beerStyle(BeerStyleEnum.ALE)
					.beerStyle("ALE")
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
