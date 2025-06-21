package domain.orders.abstractions;

import domain.customers.entities.Customer;
import domain.orders.valueObjects.Address;
import domain.orders.entities.Order;
import domain.orders.entities.OrderItem;

public interface OrderBuilder {
    Order build();

    OrderItemBuilder addProduct();

    OrderBuilder addOrderItem(OrderItem orderItem);

    OrderBuilder addShippingAddress(Address shippingAddress);
}
