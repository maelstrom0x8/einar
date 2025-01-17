package com.maelstrom.einar.inventory.adapter.web.resource;

import com.maelstrom.einar.inventory.application.dto.InventoryResponse;
import com.maelstrom.einar.inventory.application.dto.ItemResponse;
import com.maelstrom.einar.inventory.application.dto.NewInventoryRequest;
import com.maelstrom.einar.inventory.application.service.InventoryService;
import com.maelstrom.einar.inventory.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.Set;

@RestController
@RequestMapping("/v1/inventory")
public class InventoryResource
{

	private static final Logger log = LoggerFactory.getLogger(InventoryResource.class);
	private final InventoryService inventoryService;

	public InventoryResource(InventoryService inventoryService) {this.inventoryService = inventoryService;}

	@PostMapping
	public ResponseEntity<Integer> createInventory(Integer accountId, @RequestBody NewInventoryRequest request)
	{
		Inventory inventory = Inventory.open(accountId, request.name(), request.description());
		InventoryId id = inventoryService.createInventory(inventory);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("{id}").buildAndExpand(id.id())
						.toUri();
		return ResponseEntity.created(uri).build();
	}

	@GetMapping("/{id}")
	public ResponseEntity<InventoryResponse> fetchInventoryById(Integer accountId, @PathVariable Integer inventoryId)
	{
		Inventory inventory = inventoryService.getInventoryById(new InventoryId(inventoryId, accountId));
		log.info("Found inventory with id {}", inventory.getId());
		return ResponseEntity.ok(InventoryResponse.from(inventory));
	}

	@GetMapping("/{id}/statistics")
	public Collection<StockDetail> fetchStockStats(@RequestParam("ids") Set<Integer> itemsIds)
	{
		return inventoryService.statistics(itemsIds);
	}

	@GetMapping("/{id}/items/{sku}")
	public ResponseEntity<?> fetchItemBySku(Integer accountId, @PathVariable("id") Integer inventoryId, @PathVariable("sku") String sku)
	{
		ItemId itemId = new ItemId(inventoryId, sku);
		Item item = inventoryService.getItem(itemId);
		return ResponseEntity.ok(ItemResponse.from(item));
	}

	/*
	 * POST /{id}/archive?toggle=true (domain_use_case)
	 * DELETE /{id} // hides from fetching but still remains in the database for auditing
	 * PUT /{id}/update --data = InventoryUpdateRequest{}
	 * GET /{id}/items?page=N&count=M
	 * POST /{id}/items
	 * DELETE /{id}/items/{sku}
	 * GET /{id}/items/{sku}  // https://api.einar.com/v1/inventory/23535/items/KL787J
	 * PUT /{id}/items/{sku} --data = ItemUpdateRequest{}
	 * GET /{id}/stock?ids=[i1, i2, i3,..., in]
	 **/

}
