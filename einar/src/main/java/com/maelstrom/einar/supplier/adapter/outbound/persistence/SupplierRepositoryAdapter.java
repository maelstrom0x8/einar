package com.maelstrom.einar.supplier.adapter.outbound.persistence;

import com.maelstrom.config.TenantContext;
import com.maelstrom.einar.customer.domain.model.Contact;
import com.maelstrom.einar.supplier.domain.model.Supplier;
import com.maelstrom.einar.supplier.domain.model.SupplierId;
import com.maelstrom.einar.supplier.domain.repository.SupplierRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.maelstrom.jooq.tables.SupplierItems.SUPPLIER_ITEMS;
import static com.maelstrom.jooq.tables.Suppliers.SUPPLIERS;

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
		return ctx.selectFrom(SUPPLIERS)
			.where(SUPPLIERS.ID.in(ctx.selectDistinct(SUPPLIERS.ID)
				.from(SUPPLIERS)
				.join(SUPPLIER_ITEMS)
				.on(SUPPLIERS.ID.eq(SUPPLIER_ITEMS.SUPPLIER_ID))
				.where(SUPPLIER_ITEMS.ITEM_ID.eq(itemId))
			)).fetch()
			.map(_rec -> {
				Supplier supplier = new Supplier(_rec.getName(), new Contact(_rec.getEmail(), _rec.getPhone()), List.of());
				supplier.setId(new SupplierId(_rec.getId()));
				supplier.setAccountId(_rec.getAccountId());
				return supplier;
			});
	}

	@Override
	public List<Supplier> findAllByAccountId(Integer accountId)
	{
		return ctx.selectFrom(SUPPLIERS)
			.where(SUPPLIERS.ACCOUNT_ID.eq(accountId)).fetch()
			.map(_rec -> {
				Supplier supplier = new Supplier(_rec.getName(), new Contact(_rec.getEmail(), _rec.getPhone()), List.of());
				supplier.setId(new SupplierId(_rec.getId()));
				supplier.setAccountId(_rec.getAccountId());
				return supplier;
			});
	}

	@Override
	public Supplier save(Supplier entity)
	{
		Integer currentTenant = TenantContext.getCurrentTenant();
		var result = ctx.insertInto(SUPPLIERS)
			.set(SUPPLIERS.NAME, entity.getName())
			.set(SUPPLIERS.ACCOUNT_ID, entity.getAccountId())
			.set(SUPPLIERS.TENANT_ID, currentTenant)
			.set(SUPPLIERS.EMAIL, entity.getContact().getEmail())
			.set(SUPPLIERS.PHONE, entity.getContact().getPhone())
			.returning(SUPPLIERS.ID, SUPPLIERS.CREATED_AT, SUPPLIERS.LAST_UPDATED).fetchOptional();

		if (result.isPresent())
		{
			entity.setId(new SupplierId(result.get().getId()));
			for(var item :  entity.getItems()){
				ctx.insertInto(SUPPLIER_ITEMS)
					.columns(SUPPLIER_ITEMS.SUPPLIER_ID, SUPPLIER_ITEMS.ITEM_ID)
					.values(result.get().getId(), item)
					.execute();
			}
			return entity;
		}

		return null;
	}

	@Override
	public void deleteById(SupplierId supplierId)
	{
		ctx.deleteFrom(SUPPLIERS).where(SUPPLIERS.ID.eq(supplierId.value())).execute();
	}

	@Override
	public Optional<Supplier> findById(SupplierId supplierId)
	{
		return ctx.selectFrom(SUPPLIERS)
			.where(SUPPLIERS.ID.eq(supplierId.value())).fetchOptional()
			.map(_rec -> {
				Supplier supplier = new Supplier(_rec.getName(), new Contact(_rec.getEmail(), _rec.getPhone()), List.of());
				supplier.setId(new SupplierId(_rec.getId()));
				supplier.setAccountId(_rec.getAccountId());
				return supplier;
			});
	}

	@Override
	public List<Supplier> findAll()
	{
		return ctx.selectFrom(SUPPLIERS).fetch()
			.map(_rec -> {
				Supplier supplier = new Supplier(_rec.getName(), new Contact(_rec.getEmail(), _rec.getPhone()), List.of());
				supplier.setId(new SupplierId(_rec.getId()));
				return supplier;
			});
	}
}
