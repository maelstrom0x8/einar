package com.maelstrom.einar.order.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class OrderTest
{
	@Test
	void createNewOrder(){
		OrderItem i1 = new OrderItem( "BG44XS", null, 24);
		var order = Order.withOrderItems(Set.of(i1));

		assertThat(i1.getOrderId()).isEqualTo(order.getId());
		assertThat(order.getStatus()).isEqualTo(OrderStatus.NEW);
	}

	@Test void cannotUpdateCompletedOrCancelledOrder(){
		OrderItem i1 = new  OrderItem( "BG44XS", null, 24);
		Order order = new Order();
		order.setId(new OrderId(54534L));
		order.addItem(i1);
		order.setStatus(OrderStatus.COMPLETED);

		assertThatThrownBy(() -> order.updateOrderItems(Set.of(i1)))
				.isInstanceOf(IllegalStateException.class)
				.hasMessage("Cannot update a completed or cancelled order");
	}
}
