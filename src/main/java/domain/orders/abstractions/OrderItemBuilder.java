package domain.orders.abstractions;

import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.orders.entities.OrderItem;

public interface OrderItemBuilder {
    OrderBuilder build();

    OrderItemBuilder chooseProduct(Product product);

    OrderItemBuilder withQuantity(int quantity);

    OrderItemBuilder addAddition(Addition addition);
}
