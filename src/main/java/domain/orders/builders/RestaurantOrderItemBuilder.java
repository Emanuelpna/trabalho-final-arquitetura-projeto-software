package domain.orders.builders;

import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.orders.abstractions.OrderBuilder;
import domain.orders.abstractions.OrderItemBuilder;
import domain.orders.entities.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class RestaurantOrderItemBuilder implements OrderItemBuilder {
    private OrderBuilder orderBuilder;
    private Product product;
    private int quantity;
    private List<Addition> selectedAdditions = new ArrayList<>();


    public RestaurantOrderItemBuilder(OrderBuilder orderBuilder) {
        this.orderBuilder = orderBuilder;
    }

    @Override
    public OrderBuilder build() {
        OrderItem orderItem = new OrderItem(this.product, this.quantity);

        for (Addition addition : this.selectedAdditions) {
            orderItem.addAddition(addition);
        }

        this.orderBuilder.addOrderItem(orderItem);

        return this.orderBuilder;
    }

    @Override
    public OrderItemBuilder chooseProduct(Product product) {
        this.product = product;
        return this;
    }

    @Override
    public OrderItemBuilder withQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    @Override
    public OrderItemBuilder addAddition(Addition addition) {
        this.selectedAdditions.add(addition);
        return this;
    }
}
