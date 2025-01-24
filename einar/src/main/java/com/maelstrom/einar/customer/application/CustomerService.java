package com.maelstrom.einar.customer.application;


import com.maelstrom.einar.customer.domain.model.Contact;
import com.maelstrom.einar.customer.domain.model.Customer;
import com.maelstrom.einar.customer.domain.model.CustomerId;
import com.maelstrom.einar.customer.domain.repository.CustomerRepository;
import org.jooq.exception.DataAccessException;
import org.jooq.exception.IntegrityConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService
{
	private static final Logger log = LoggerFactory.getLogger(CustomerService.class);
	private final CustomerRepository customerRepository;

	public CustomerService(CustomerRepository customerRepository)
	{
		this.customerRepository = customerRepository;
	}

	public Customer getCustomerById(CustomerId customerId) {
		return customerRepository.findById(customerId).orElseThrow();
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	@Transactional
	public void createCustomer(Customer customer) {
		try
		{
			customerRepository.save(customer);
		} catch (DataAccessException e)
		{
			if (e instanceof IntegrityConstraintViolationException ex)
			{
				log.error("Failed to create customer", ex);
				throw new CustomerAlreadyExistsException();
			}
				throw new RuntimeException(e);
		}
	}

	public Customer getCustomerByContact(Contact contact) {
		return customerRepository.findByContact(contact).orElseThrow();
	}
}
