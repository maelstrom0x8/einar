package com.maelstrom.einar.supplier.adapter.outbound.persistence;

import com.maelstrom.einar.supplier.domain.model.Supplier;
import com.maelstrom.einar.supplier.domain.model.SupplierId;
import com.maelstrom.einar.supplier.domain.repository.SupplierRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SupplierRepositoryAdapter implements SupplierRepository
{

	private final DSLContext ctx;

	public SupplierRepositoryAdapter(DSLContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public List<Supplier> findAllSupplying(String itemId)
	{
		return List.of();
	}

	@Override
	public List<Supplier> findAllByAccountId(Integer accountId)
	{
		return List.of();
	}

	@Override
	public Supplier save(Supplier entity)
	{
		return null;
	}

	@Override
	public void deleteById(SupplierId supplierId)
	{

	}

	@Override
	public Optional<Supplier> findById(SupplierId supplierId)
	{
		return Optional.empty();
	}

	@Override
	public List<Supplier> findAll()
	{
		return List.of();
	}
}
