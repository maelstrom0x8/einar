package com.maelstrom.einar.supplier.domain.repository;

import com.maelstrom.einar.common.data.GenericRepository;
import com.maelstrom.einar.supplier.domain.model.Supplier;
import com.maelstrom.einar.supplier.domain.model.SupplierId;

import java.util.List;

public interface SupplierRepository extends GenericRepository<Supplier, SupplierId>
{

	List<Supplier> findAllSupplying(String itemId);

	List<Supplier> findAllByAccountId(Integer accountId);
}
