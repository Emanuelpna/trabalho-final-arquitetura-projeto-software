package domain.orders.builders;

import domain.orders.abstractions.OrderBuilder;
import domain.orders.abstractions.OrderItemBuilder;
import domain.orders.entities.Order;
import domain.orders.entities.OrderItem;
import domain.orders.entities.RestaurantOrder;
import domain.orders.states.PendingOrderState;
import domain.orders.valueObjects.Address;
import domain.stores.abstractions.StoreType;

import java.util.ArrayList;
import java.util.List;

public class SupermarketOrderBuilder implements OrderBuilder {
    private List<OrderItem> orderItems = new ArrayList<>();

    private Address shippingAddress;

    @Override
    public Order build() {
        if(this.shippingAddress == null) {
            throw new IllegalStateException("Shipping address not set");
        }

        if(this.orderItems.isEmpty()) {
            throw new IllegalStateException("Order items cannot be empty");
        }

        return new RestaurantOrder(this.shippingAddress, this.orderItems, PendingOrderState.getInstance());
    }

    @Override
    public OrderItemBuilder addProduct() {
        return new RestaurantOrderItemBuilder(this);
    }

    @Override
    public OrderBuilder addOrderItem(OrderItem orderItem) {
        if (!orderItem.getProduct().getStoreType().equals(StoreType.SUPERMARKET)) {
            throw new IllegalStateException("Order items must be of type SUPERMARKET");
        }

        this.orderItems.add(orderItem);
        return this;
    }

    @Override
    public OrderBuilder addShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }
}
