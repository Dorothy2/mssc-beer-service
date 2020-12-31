package com.drifai.services.inventory;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.drifai.services.inventory.model.BeerInventoryDto;

@FeignClient(name = "inventory-failover")
public interface InventoryFailoverFeignClient {
	
	@RequestMapping(method = RequestMethod.GET, value = "/inventory-failover")
	ResponseEntity<List<BeerInventoryDto>> getOnHandInventory();

}
