package domain.orders.builders;

import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.orders.abstractions.OrderBuilder;
import domain.orders.abstractions.OrderItemBuilder;
import domain.orders.entities.OrderItem;
import domain.stores.abstractions.StoreType;

import java.util.ArrayList;
import java.util.List;

public class SupermarketOrderItemBuilder implements OrderItemBuilder {
    private OrderBuilder orderBuilder;
    private Product product;
    private int quantity;
    private List<Addition> selectedAdditions = new ArrayList<>();


    public SupermarketOrderItemBuilder(OrderBuilder orderBuilder) {
        this.orderBuilder = orderBuilder;
    }

    @Override
    public OrderBuilder build() {
        if (product == null || quantity == 0) {
            throw new IllegalStateException("Product and quantity are required");
        }

        OrderItem orderItem = new OrderItem(this.product, this.quantity);

        for (Addition addition : this.selectedAdditions) {
            orderItem.addAddition(addition);
        }

        this.orderBuilder.addOrderItem(orderItem);

        return this.orderBuilder;
    }

    @Override
    public OrderItemBuilder chooseProduct(Product product) {
        if (!product.getStoreType().equals(StoreType.SUPERMARKET)) {
            throw new IllegalStateException("Order items must be of type SUPERMARKET");
        }

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
