package com.maelstrom.einar.customer.domain.repository;

import com.maelstrom.einar.common.data.GenericRepository;
import com.maelstrom.einar.customer.domain.model.Contact;
import com.maelstrom.einar.customer.domain.model.Customer;
import com.maelstrom.einar.customer.domain.model.CustomerId;

import java.util.Optional;

public interface CustomerRepository extends GenericRepository<Customer, CustomerId> {

	Optional<Customer> findByContact(Contact contact);
}
