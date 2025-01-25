package com.maelstrom.einar.supplier.adapter.inbound.web;

import com.maelstrom.config.security.AuthID;
import com.maelstrom.einar.customer.domain.model.Contact;
import com.maelstrom.einar.supplier.adapter.inbound.web.dto.RegisterSupplierRequest;
import com.maelstrom.einar.supplier.application.SupplierService;
import com.maelstrom.einar.supplier.domain.model.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/suppliers")
public class SupplierResource
{
	private final SupplierService supplierService;

	public SupplierResource(SupplierService supplierService)
	{
		this.supplierService = supplierService;
	}

	@GetMapping
	public ResponseEntity<?> fetchAllSuppliers(@AuthID Integer accountId)
	{
		List<Supplier> suppliers = supplierService.getAllSuppliers(accountId);
		return ResponseEntity.ok(suppliers);
	}

	@GetMapping("/{sku}")
	public ResponseEntity<?> fetchSuppliers(String sku)
	{
		return ResponseEntity.ok().build();
	}

	@PostMapping
	public ResponseEntity<?> createSupplier(@AuthID Integer accountId, @RequestBody RegisterSupplierRequest request)
	{
		Supplier supplier = new Supplier(request.name(), new Contact(request.email(), request.phone()), request.itemIds());
		supplier.setAccountId(accountId);
		supplierService.register(supplier);

		return ResponseEntity.ok().build();
	}



}
