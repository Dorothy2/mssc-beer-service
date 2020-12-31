package com.drifai.services.inventory;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.drifai.services.inventory.model.BeerInventoryDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public final class InventoryServiceFeignClientFailover implements InventoryServiceFeignClient{
	
	private final InventoryFailoverFeignClient failoverFeignClient;

	@Override
	public ResponseEntity<List<BeerInventoryDto>> getOnHandInventory(UUID beerId) {
		return failoverFeignClient.getOnHandInventory();
	}

}
