package com.maelstrom.einar.supplier.application;


import com.maelstrom.einar.supplier.domain.model.Supplier;
import com.maelstrom.einar.supplier.domain.repository.SupplierRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SupplierService
{

	private final SupplierRepository supplierRepository;
	private final ApplicationEventPublisher eventPublisher;

	public SupplierService(SupplierRepository supplierRepository, ApplicationEventPublisher eventPublisher)
	{
		this.supplierRepository = supplierRepository;
		this.eventPublisher = eventPublisher;
	}


	@Transactional
	public void register(Supplier supplier)
	{
		supplierRepository.save(supplier);
	}

	public List<Supplier> getAllSuppliers(Integer accountId)
	{
		return supplierRepository.findAllByAccountId(accountId);
	}

	public List<Supplier> getSuppliersSupplying(String itemId)
	{
		return supplierRepository.findAllSupplying(itemId);
	}
}
