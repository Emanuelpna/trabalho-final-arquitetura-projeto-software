package application.useCases.orders.placeOrder;

import application.mediators.OrderMediator;
import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.customers.entities.Customer;
import domain.delivery.entities.Deliverer;
import domain.orders.abstractions.OrderBuilder;
import domain.orders.abstractions.OrderBuilderFactory;
import domain.orders.entities.Order;
import domain.orders.valueObjects.Address;
import domain.stores.abstractions.StoreType;
import domain.stores.entities.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaceOrderCommandHandlerTest {
    private Order order;
    private Addition doubleCheese;
    private Product xBurguer;

    private Store store;
    private Customer customer;

    private OrderMediator mediator;

    @BeforeEach
    void setUp() {
        this.doubleCheese = new Addition("Dobro de Queijo", "O dobro de cheddar no seu lanche", 4.99);
        this.xBurguer = new Product("X-Burguer", 25.99, StoreType.RESTAURANT, List.of(doubleCheese));

        this.store = new Store("Hamburgueria 1", StoreType.RESTAURANT);
        this.customer = new Customer("Cliente 1");

        OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
        this.order = restaurantOrderBuilder
                .addShippingAddress(new Address("Rua Aquela Lá", "Juiz de Fora", "MG", "Brasil"))
                .addProduct()
                .chooseProduct(xBurguer)
                .addAddition(doubleCheese)
                .withQuantity(1)
                .build()
                .build();
    }

    @Test
    void placeOrder() {
        PlaceOrderCommand command = new PlaceOrderCommand(order, customer, store);

        PlaceOrderCommandHandler commandHandler = new PlaceOrderCommandHandler();

        Order result = commandHandler.handle(command);

        assertEquals("Pending", result.getOrderState().getStateName());
    }

    @Test
    void failToPlaceOrderAlreadyPlaced() {
        try {
            order.startOrderPreparation();

            PlaceOrderCommand command = new PlaceOrderCommand(order, customer, store);

            PlaceOrderCommandHandler commandHandler = new PlaceOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Cannot place an order already placed", e.getMessage());
        }
    }

    @Test
    void failToPlaceOrderWithoutOrder() {
        try {
            PlaceOrderCommand command = new PlaceOrderCommand(null, customer, store);

            PlaceOrderCommandHandler commandHandler = new PlaceOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order is required", e.getMessage());
        }
    }

    @Test
    void failToPlaceOrderWithoutCustomer() {
        try {
            PlaceOrderCommand command = new PlaceOrderCommand(order, null, store);

            PlaceOrderCommandHandler commandHandler = new PlaceOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Customer is required", e.getMessage());
        }
    }

    @Test
    void failToPlaceOrderWithoutStore() {
        try {
            PlaceOrderCommand command = new PlaceOrderCommand(order, customer, null);

            PlaceOrderCommandHandler commandHandler = new PlaceOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Store is required", e.getMessage());
        }
    }
}