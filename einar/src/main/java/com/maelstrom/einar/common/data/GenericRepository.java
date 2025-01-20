package com.maelstrom.einar.common.data;

import java.util.List;
import java.util.Optional;

public interface GenericRepository<E, ID>
{
	E save(E entity);

	void deleteById(ID id);

	Optional<E> findById(ID id);

	List<E> findAll();
}
