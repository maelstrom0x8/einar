package com.maelstrom.einar.customer.adapter.inbound.web.resource;


import com.maelstrom.config.security.AuthID;
import com.maelstrom.einar.customer.adapter.inbound.web.dto.CreateCustomerRequest;
import com.maelstrom.einar.customer.adapter.inbound.web.dto.CustomerResponse;
import com.maelstrom.einar.customer.application.CustomerService;
import com.maelstrom.einar.customer.domain.model.Contact;
import com.maelstrom.einar.customer.domain.model.Customer;
import com.maelstrom.einar.customer.domain.model.CustomerDetails;
import com.maelstrom.einar.customer.domain.model.CustomerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/customers")
public class CustomerResource
{
	private static final Logger log = LoggerFactory.getLogger(CustomerResource.class);
	private final CustomerService customerService;

	public CustomerResource(CustomerService customerService)
	{
		this.customerService = customerService;
	}

	@PostMapping("/register")
	public ResponseEntity<?> createCustomerAccount(@AuthID Integer accountId, @RequestBody CreateCustomerRequest request)
	{
		var customer = new Customer(new CustomerDetails(request.name(), new Contact(request.email(), request.phone())));

		customerService.createCustomer(customer);

		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getAllCustomers(@AuthID Integer accountId)
	{
		var customers = customerService.getAllCustomers().stream().map(CustomerResponse::from)
			.toList();

		return ResponseEntity.ok(customers);
	}


	@GetMapping("/{id}")
	public ResponseEntity<CustomerResponse> getCustomerById(@AuthID Integer accountId, @PathVariable("id") Long value)
	{
		var customer = customerService.getCustomerById(new CustomerId(value));

		return customer != null ? ResponseEntity.ok(CustomerResponse.from(customer)) : ResponseEntity.notFound().build();

	}

	@GetMapping("/contact")
	public ResponseEntity<?> getCustomerByEmail(@AuthID Integer accountId,
																							@RequestParam(value = "email", required = false) String email,
																							@RequestParam(value = "phone", required = false) String phone)
	{
		if (email == null && phone == null)
		{
			return ResponseEntity.badRequest().body("Provide either customer email or telephone");
		}

		Contact contact = new Contact(email, phone);
		log.info("Searching for customer with contact {}", contact);
		Customer customer = customerService.getCustomerByContact(contact);

		if (customer != null)
		{
			log.info("Found customer with contact {}", customer.getDetails().getContact());
			return ResponseEntity.ok(CustomerResponse.from(customer));
		}

		return ResponseEntity.notFound().build();
	}

}
