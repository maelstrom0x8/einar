package com.maelstrom.einar.order.adapter.outbound.infrastructure.persistence;

import com.maelstrom.config.TenantContext;
import com.maelstrom.einar.order.domain.model.Order;
import com.maelstrom.einar.order.domain.model.OrderId;
import com.maelstrom.einar.order.domain.model.OrderItem;
import com.maelstrom.einar.order.domain.repository.OrderRepository;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.maelstrom.jooq.tables.OrderItems.ORDER_ITEMS;
import static com.maelstrom.jooq.tables.Orders.ORDERS;


@Repository
public class OrderRepositoryAdapter implements OrderRepository
{

	private final DSLContext ctx;

	public OrderRepositoryAdapter(DSLContext ctx)
	{
		this.ctx = ctx;
	}


	@Override
	public Order save(Order entity)
	{
		Integer tenant = TenantContext.getCurrentTenant();
		var _opt = ctx.insertInto(ORDERS)
			.set(ORDERS.TENANT_ID, tenant)
			.returning(ORDERS.ORDER_ID, ORDERS.CREATED_AT, ORDERS.LAST_UPDATED).fetchOptional();

		_opt.ifPresentOrElse(
			o ->
			{
				entity.setId(new OrderId(o.getOrderId().longValue()));
				entity.setCreatedAt(o.getCreatedAt());
				entity.setLastUpdated(o.getLastUpdated());
				for (OrderItem item : entity.getItems())
				{
					item.setOrderId(entity.getId());
					saveItem(item);
				}
			},
			() -> {
				throw new RuntimeException("Order not saved");
			}
		);

		return entity;
	}

	void saveItem(OrderItem item) {
		ctx.insertInto(ORDER_ITEMS)
			.set(ORDER_ITEMS.ORDER_ID, item.getOrderId().value())
			.set(ORDER_ITEMS.ITEM_ID, item.getItemId())
			.set(ORDER_ITEMS.QUANTITY, item.getQuantity())
			.execute();
	}

	@Override
	public void deleteById(OrderId orderId)
	{

	}

	@Override
	public Optional<Order> findById(OrderId orderId)
	{
		return Optional.empty();
	}

	@Override
	public List<Order> findAll()
	{
		return List.of();
	}
}
