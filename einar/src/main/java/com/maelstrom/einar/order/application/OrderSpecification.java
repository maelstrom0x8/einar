package com.maelstrom.einar.order.application;

import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class OrderSpecification
{
	private Long customerId;
	private final Map<String, Integer> items = new HashMap<>();
	private LocalDateTime scheduledFor;

	private OrderSpecification() {}

	public Long getCustomerId()
	{
		return customerId;
	}

	public Map<String, Integer> getItems()
	{
		return items;
	}

	public LocalDateTime getScheduledFor()
	{
		return scheduledFor;
	}

	public boolean isScheduled()
	{
		return scheduledFor != null;
	}

	public static Builder withCustomerId(Long customerId) {
		Builder builder = new Builder();
		builder.specification.customerId = customerId;
		return builder;
	}

	public static class Builder {
		private final OrderSpecification specification;

		private Builder() {
			specification = new OrderSpecification();
		}

		public Builder item(String itemId, Integer quantity) {
			specification.items.put(itemId, quantity);
			return this;
		}

		public Builder items(Map<String, Integer> items) {
			specification.items.putAll(items);
			return this;
		}

		public Builder scheduledFor(LocalDateTime scheduledFor) {
			specification.scheduledFor = scheduledFor;
			return this;
		}

		public OrderSpecification build() {
			Assert.notNull(specification.customerId, "customerId is required");
			Assert.notEmpty(specification.items, "items are required");
			return specification;
		}



	}

}
