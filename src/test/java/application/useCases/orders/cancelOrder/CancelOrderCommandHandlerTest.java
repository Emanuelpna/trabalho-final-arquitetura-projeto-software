package application.useCases.orders.cancelOrder;

import application.mediators.OrderMediator;
import application.useCases.orders.startOrderPreparation.StartOrderPreparationCommand;
import application.useCases.orders.startOrderPreparation.StartOrderPreparationCommandHandler;
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

class CancelOrderCommandHandlerTest {
    private Order order;
    private Addition doubleCheese;
    private Product xBurguer;

    private Store store;
    private Customer customer;
    private Deliverer deliverer;

    private OrderMediator mediator;

    @BeforeEach
    void setUp() {
        this.doubleCheese = new Addition("Dobro de Queijo", "O dobro de cheddar no seu lanche", 4.99);
        this.xBurguer = new Product("X-Burguer", 25.99, StoreType.RESTAURANT, List.of(doubleCheese));

        this.store = new Store("Hamburgueria 1", StoreType.RESTAURANT);
        this.customer = new Customer("Cliente 1");
        this.deliverer = new Deliverer("Entregador 1");

        OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
        this.order = restaurantOrderBuilder
                .addShippingAddress(new Address("Rua Aquela Lá", "Juiz de Fora", "MG", "Brasil"))
                .addProduct()
                    .chooseProduct(xBurguer)
                    .addAddition(doubleCheese)
                    .withQuantity(1)
                    .build()
                .build();

        this.order.processOrder();
        this.order.placeOrder(customer, store);
    }

    @Test
    public void cancelOrder() {
        CancelOrderCommand command = new CancelOrderCommand(order, customer, store, deliverer);

        CancelOrderCommandHandler handler = new CancelOrderCommandHandler();

        Order result = handler.handle(command);

        assertEquals("Cancelled", result.getOrderState().getStateName());
    }

    @Test
    void failToCancelOrderAlreadyCancelled() {
        try {
            order.cancel();

            CancelOrderCommand command = new CancelOrderCommand(order, customer, store, deliverer);

            CancelOrderCommandHandler commandHandler = new CancelOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order already cancelled", e.getMessage());
        }
    }

    @Test
    void failToCancelOrderWithoutOrder() {
        try {
            CancelOrderCommand command = new CancelOrderCommand(null, customer, store, deliverer);

            CancelOrderCommandHandler commandHandler = new CancelOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order is required", e.getMessage());
        }
    }

    @Test
    void failToCancelOrderWithoutCustomer() {
        try {
            CancelOrderCommand command = new CancelOrderCommand(order, null, store, deliverer);

            CancelOrderCommandHandler commandHandler = new CancelOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Customer is required", e.getMessage());
        }
    }

    @Test
    void failToCancelOrderWithoutStore() {
        try {
            CancelOrderCommand command = new CancelOrderCommand(order, customer, null, deliverer);

            CancelOrderCommandHandler commandHandler = new CancelOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Store is required", e.getMessage());
        }
    }

    @Test
    void failToCancelOrderWithoutDeliverer() {
        try {
            CancelOrderCommand command = new CancelOrderCommand(order, customer, store, null);

            CancelOrderCommandHandler commandHandler = new CancelOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Deliverer is required", e.getMessage());
        }
    }
}