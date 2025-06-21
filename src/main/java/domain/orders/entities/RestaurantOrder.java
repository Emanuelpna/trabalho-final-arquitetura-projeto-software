package domain.orders.entities;

import domain.orders.valueObjects.Address;
import domain.orders.states.OrderState;
import domain.stores.abstractions.StoreType;

import java.util.List;
import java.util.UUID;

public class RestaurantOrder extends Order {
    public RestaurantOrder(UUID id, Address shippingAddress, List<OrderItem> orderItems, OrderState state) {
        super(id, shippingAddress, orderItems, state);
    }

    public RestaurantOrder(Address shippingAddress, List<OrderItem> orderItems, OrderState state) {
        super(shippingAddress, orderItems, state);
    }

    @Override
    public void processOrder() {
        for (OrderItem orderItem : getOrderItems()) {
            if (!orderItem.getProduct().getStoreType().equals(StoreType.RESTAURANT)) {
                throw new IllegalStateException("Restaurant cannot process orders from other Store types");
            }
        }
    }
}
