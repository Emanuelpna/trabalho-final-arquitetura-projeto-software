package domain.orders.abstractions;

import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.customers.entities.Customer;
import domain.delivery.entities.Deliverer;
import domain.orders.entities.Order;
import domain.orders.entities.OrderItem;
import domain.orders.valueObjects.Address;
import domain.stores.abstractions.StoreType;
import domain.stores.entities.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class OrderBuilderFactoryTest {
    private Addition doubleCheese;
    private Product xBurguer;

    @BeforeEach
    void setUp() {
        this.doubleCheese = new Addition("Dobro de Queijo", "O dobro de cheddar no seu lanche", 4.99);
        this.xBurguer = new Product("X-Burguer", 25.99, StoreType.RESTAURANT, List.of(doubleCheese));
    }

    @Test
    void createOrderWithAddress() {
        OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
        Order order = restaurantOrderBuilder
                .addShippingAddress(new Address("Rua Aquela Lá", "Juiz de Fora", "MG", "Brasil"))
                .addProduct()
                .chooseProduct(xBurguer)
                .addAddition(doubleCheese)
                .withQuantity(1)
                .build()
                .build();

        assertEquals("Rua Aquela Lá", order.getShippingAddress().street());
    }

    @Test
    void createOrderWithCorrectOrderItem() {
        OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
        Order order = restaurantOrderBuilder
                .addShippingAddress(new Address("Rua Aquela Lá", "Juiz de Fora", "MG", "Brasil"))
                .addProduct()
                .chooseProduct(xBurguer)
                .addAddition(doubleCheese)
                .withQuantity(1)
                .build()
                .build();

        Optional<OrderItem> orderItem = order.getOrderItems().stream().findFirst();

        if (orderItem.isEmpty()) {
            fail();
        }

        assertEquals(xBurguer, orderItem.get().getProduct());
        assertEquals(1, orderItem.get().getQuantity());
        assertEquals(Optional.of(doubleCheese), orderItem.get().getSelectedAdditions().stream().findFirst());
    }

    @Test
    void failToCreatOrderWithoutOrderItem() {
        try {
            OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
            restaurantOrderBuilder
                    .addShippingAddress(new Address("Rua Aquela Lá", "Juiz de Fora", "MG", "Brasil"))
                    .build();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order items cannot be empty", e.getMessage());
        }
    }

    @Test
    void failToCreatOrderWithoutShippingAddress() {
        try {
            OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
            restaurantOrderBuilder.build();

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Shipping address not set", e.getMessage());
        }
    }
}