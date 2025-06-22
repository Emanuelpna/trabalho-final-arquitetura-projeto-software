package domain.orders.builders;

import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.orders.abstractions.OrderBuilder;
import domain.orders.abstractions.OrderBuilderFactory;
import domain.orders.abstractions.OrderItemBuilder;
import domain.orders.entities.Order;
import domain.orders.entities.OrderItem;
import domain.orders.valueObjects.Address;
import domain.stores.abstractions.StoreType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantOrderItemBuilderTest {
    private Addition doubleCheese;
    private Product xBurguer;

    @BeforeEach
    void setUp() {
        this.doubleCheese = new Addition("Dobro de Queijo", "O dobro de cheddar no seu lanche", 4.99);
        this.xBurguer = new Product("X-Burguer", 25.99, StoreType.RESTAURANT, List.of(doubleCheese));
    }

    @Test
    void createOrderItem() {
        OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
        OrderItemBuilder restauranteOrderItemBuilder = new RestaurantOrderItemBuilder(restaurantOrderBuilder);

        OrderBuilder orderBuilder = restauranteOrderItemBuilder
                .chooseProduct(xBurguer)
                .withQuantity(1)
                .addAddition(doubleCheese)
                .build();

        Order order = orderBuilder
                .addShippingAddress(new Address("Rua Aquela Lá", "Juiz de Fora", "MG", "Brasil"))
                .build();

        List<OrderItem> orderItems = order.getOrderItems();

        assertEquals(1, orderItems.size());
    }

    @Test
    void failToCreateOrderItemWithoutProduct() {
        try {
            OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
            OrderItemBuilder restauranteOrderItemBuilder = new RestaurantOrderItemBuilder(restaurantOrderBuilder);

            restauranteOrderItemBuilder
                    .withQuantity(1)
                    .addAddition(doubleCheese)
                    .build();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Product and quantity are required", e.getMessage());
        }
    }

    @Test
    void failToCreateOrderItemWithoutQuantity() {
        try {
            OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
            OrderItemBuilder restauranteOrderItemBuilder = new RestaurantOrderItemBuilder(restaurantOrderBuilder);

            restauranteOrderItemBuilder
                    .chooseProduct(xBurguer)
                    .addAddition(doubleCheese)
                    .build();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Product and quantity are required", e.getMessage());
        }
    }
}