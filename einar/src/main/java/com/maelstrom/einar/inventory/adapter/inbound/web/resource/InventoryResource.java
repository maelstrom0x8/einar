package com.maelstrom.einar.inventory.adapter.inbound.web.resource;

import com.maelstrom.einar.inventory.adapter.inbound.web.resource.dto.InventoryUpdateRequest;
import com.maelstrom.einar.inventory.adapter.inbound.web.resource.dto.ItemUpdateRequest;
import com.maelstrom.einar.inventory.application.dto.InventoryResponse;
import com.maelstrom.einar.inventory.application.dto.ItemResponse;
import com.maelstrom.einar.inventory.application.dto.NewInventoryRequest;
import com.maelstrom.einar.inventory.application.dto.NewItemRequest;
import com.maelstrom.einar.inventory.application.service.InventoryService;
import com.maelstrom.einar.inventory.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/v1/inventory")
public class InventoryResource
{

	private static final Logger log = LoggerFactory.getLogger(InventoryResource.class);
	private final InventoryService inventoryService;

	public InventoryResource(InventoryService inventoryService)
	{
		this.inventoryService = inventoryService;
	}

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

	@PostMapping("/{id}/items")
	public ResponseEntity<?> addItemsToInventory(Integer accountId, @PathVariable("id") Integer inventoryId,
																							 @RequestBody List<NewItemRequest> items)
	{
		InventoryId id = new InventoryId(inventoryId, accountId);
		Inventory inventory = inventoryService.getInventoryById(id);
		List<Item> _items = items.stream().map(e -> Item.create(new InventoryId(inventoryId, accountId),
			e.name(), e.description(), e.stockThreshold())).toList();
		try
		{
			inventoryService.addItemsToInventory(id, _items);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		} catch (IllegalAccessException e)
		{
			log.error("Unable to add items to inventory", e);
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@GetMapping("/{id}/items/{sku}")
	public ResponseEntity<?> fetchItemBySku(Integer accountId, @PathVariable("id") Integer inventoryId,
																					@PathVariable("sku") String sku)
	{
		ItemId itemId = new ItemId(inventoryId, Sku.valueOf(sku));
		Item item = inventoryService.getItem(itemId);
		return ResponseEntity.ok(ItemResponse.from(item));
	}

	@PostMapping("/{id}/archive")
	public ResponseEntity<?> toggleArchiveInventory(@PathVariable("id") Integer inventoryId, @RequestParam("action") boolean toggle)
	{
		InventoryId id = new InventoryId(inventoryId, null);
		Inventory inventory = inventoryService.getInventoryById(id);
		inventory.setState(toggle ? InventoryState.ARCHIVED : InventoryState.OPEN);
		inventoryService.setInventoryState(id, inventory.getState());
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteInventory(Integer accountId, @PathVariable("id") Integer inventoryId)
	{
		InventoryId id = new InventoryId(inventoryId, accountId);
		inventoryService.deleteInventory(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/update")
	public ResponseEntity<?> updateInventory(Integer accountId, @PathVariable("id") Integer inventoryId,
																					 @RequestBody InventoryUpdateRequest request)
	{
		InventoryId id = new InventoryId(inventoryId, accountId);
		Inventory inventory = inventoryService.getInventoryById(id);
		inventory.rename(request.name());
		inventory.setDescription(request.description());
		inventoryService.setInventoryState(id, inventory.getState());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}/items")
	public ResponseEntity<List<ItemResponse>> getItems(Integer accountId, @PathVariable("id") Integer inventoryId, @RequestParam("page") int page,
																										 @RequestParam("count") int count)
	{
		InventoryId id = new InventoryId(inventoryId, accountId);
		List<Item> items = inventoryService.getItems(id, page * count, count);
		return ResponseEntity.ok(items.stream().map(ItemResponse::from).toList());
	}

	@DeleteMapping("/{id}/items/{sku}")
	public ResponseEntity<?> deleteItem(@PathVariable("id") Integer inventoryId, @PathVariable("sku") String sku)
	{
		ItemId itemId = new ItemId(inventoryId, Sku.valueOf(sku));
		inventoryService.removeItem(itemId);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/items/{sku}")
	public ResponseEntity<?> updateItem(@PathVariable("id") Integer inventoryId, @PathVariable("sku") String sku, @RequestBody ItemUpdateRequest request)
	{
		ItemId itemId = new ItemId(inventoryId, Sku.valueOf(sku));

		inventoryService.updateItem(itemId, request.name(), request.description(), request.stockThreshold());
		return ResponseEntity.ok().build();
	}

	@GetMapping("/{id}/stock")
	public ResponseEntity<Collection<StockDetail>> getStock(@PathVariable("id") Integer inventoryId, @RequestParam("ids") Set<Integer> itemsIds)
	{
		return ResponseEntity.ok(inventoryService.statistics(itemsIds));
	}

}
