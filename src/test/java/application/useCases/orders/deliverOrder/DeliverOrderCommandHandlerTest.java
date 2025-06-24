package application.useCases.orders.deliverOrder;

import application.mediators.OrderMediator;
import application.useCases.orders.deliverOrder.DeliverOrderCommand;
import application.useCases.orders.deliverOrder.DeliverOrderCommandHandler;
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

class DeliverOrderCommandHandlerTest {
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
        this.order.startOrderPreparation();
    }

    @Test
    public void deliverOrder() {
        DeliverOrderCommand command = new DeliverOrderCommand(order, customer, store, deliverer);

        DeliverOrderCommandHandler handler = new DeliverOrderCommandHandler();

        Order result = handler.handle(command);

        assertEquals("Delivering", result.getOrderState().getStateName());
    }

    @Test
    void failToDeliverOrderAlreadyDelivering() {
        try {
            order.deliver(deliverer);

            DeliverOrderCommand command = new DeliverOrderCommand(order, customer, store, deliverer);

            DeliverOrderCommandHandler commandHandler = new DeliverOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order already being delivered", e.getMessage());
        }
    }

    @Test
    void failToDeliverOrderWithoutOrder() {
        try {
            DeliverOrderCommand command = new DeliverOrderCommand(null, customer, store, deliverer);

            DeliverOrderCommandHandler commandHandler = new DeliverOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order is required", e.getMessage());
        }
    }

    @Test
    void failToDeliverOrderWithoutCustomer() {
        try {
            DeliverOrderCommand command = new DeliverOrderCommand(order, null, store, deliverer);

            DeliverOrderCommandHandler commandHandler = new DeliverOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Customer is required", e.getMessage());
        }
    }

    @Test
    void failToDeliverOrderWithoutStore() {
        try {
            DeliverOrderCommand command = new DeliverOrderCommand(order, customer, null, deliverer);

            DeliverOrderCommandHandler commandHandler = new DeliverOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Store is required", e.getMessage());
        }
    }

    @Test
    void failToDeliverOrderWithoutDeliverer() {
        try {
            DeliverOrderCommand command = new DeliverOrderCommand(order, customer, store, null);

            DeliverOrderCommandHandler commandHandler = new DeliverOrderCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Deliverer is required", e.getMessage());
        }
    }
}