package com.maelstrom.einar.customer.adapter.outbound.persistence;

import com.maelstrom.config.TenantContext;
import com.maelstrom.einar.customer.domain.model.Contact;
import com.maelstrom.einar.customer.domain.model.Customer;
import com.maelstrom.einar.customer.domain.model.CustomerDetails;
import com.maelstrom.einar.customer.domain.model.CustomerId;
import com.maelstrom.einar.customer.domain.repository.CustomerRepository;
import com.maelstrom.jooq.tables.records.CustomersRecord;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.maelstrom.jooq.tables.Customers.CUSTOMERS;


@Repository
public class CustomerRepositoryAdapter implements CustomerRepository
{

	private final DSLContext ctx;

	public CustomerRepositoryAdapter(DSLContext ctx)
	{
		this.ctx = ctx;
	}

	@Override
	public Customer save(Customer entity)
	{
		Integer currentTenant = TenantContext.getCurrentTenant();
		CustomersRecord _rec = ctx.insertInto(CUSTOMERS)
			.set(CUSTOMERS.NAME, entity.getDetails().getName())
			.set(CUSTOMERS.EMAIL, entity.getDetails().getContact().getEmail())
			.set(CUSTOMERS.PHONE, entity.getDetails().getContact().getPhone())
			.set(CUSTOMERS.TENANT_ID, currentTenant)
			.returning().fetchOne();

		return mapToCustomer(Objects.requireNonNull(_rec));
	}

	private Customer saveAnonymous(Customer entity)
	{
		var _rec = ctx.insertInto(CUSTOMERS)
			.set(CUSTOMERS.CUSTOMER_ID, entity.getId().value())
			.set(CUSTOMERS.TENANT_ID, TenantContext.getCurrentTenant())
			.returning().fetchOne();

		return mapToCustomer(Objects.requireNonNull(_rec));
	}

	@Override
	public void deleteById(CustomerId customerId)
	{
		ctx.deleteFrom(CUSTOMERS)
			.where(CUSTOMERS.CUSTOMER_ID.eq(customerId.value()))
			.execute();
	}

	@Override
	public Optional<Customer> findById(CustomerId customerId)
	{
		return ctx.selectFrom(CUSTOMERS)
			.where(CUSTOMERS.CUSTOMER_ID.eq(customerId.value()))
			.fetchOptional().map(this::mapToCustomer);
	}

	private Customer mapToCustomer(CustomersRecord e)
	{
		var customerId = e.getCustomerId();

		Customer customer = new Customer(new CustomerDetails(e.getName(), new Contact(e.getEmail(), e.getPhone())));
		customer.setId(new CustomerId(e.getCustomerId()));
		customer.setCreatedAt(e.getCreatedAt());
		return customer;
	}

	@Override
	public List<Customer> findAll()
	{
		return ctx.selectFrom(CUSTOMERS)
			.fetch().map(this::mapToCustomer);
	}

	@Override
	public Optional<Customer> findByContact(Contact contact)
	{
		return ctx.selectFrom(CUSTOMERS)
			.where(CUSTOMERS.EMAIL.eq(contact.getEmail()).or(CUSTOMERS.PHONE.eq(contact.getPhone())))
			.fetchOptional().map(this::mapToCustomer);
	}
}
