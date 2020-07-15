package com.drifai.services.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.drifai.bootstrap.BeerLoader;

import lombok.extern.slf4j.Slf4j;

@Disabled // utility for manual testing
@SpringBootTest
class BeerInventoryServiceRestTemplateImplTest {
	
	@Autowired
	BeerInventoryService beerInventoryService;
	
	@BeforeEach
	void setUp() {
		
	}
	
	@Test
	void getOnHandInventory() {
		Integer qoh = beerInventoryService.getOnHandInventory(BeerLoader.BEER_1_UUID);
		
		// Quantity on hand is coming from mssc-beer-inventory-service BeerInventoryBootstrap.java
		System.out.println("Inventory on hand: " + qoh);
	}

}
