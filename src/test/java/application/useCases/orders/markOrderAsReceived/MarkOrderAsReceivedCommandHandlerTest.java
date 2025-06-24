package application.useCases.orders.markOrderAsReceived;

import application.mediators.OrderMediator;
import application.useCases.orders.markOrderAsReceived.MarkOrderAsReceivedCommand;
import application.useCases.orders.markOrderAsReceived.MarkOrderAsReceivedCommandHandler;
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

class MarkOrderAsReceivedCommandHandlerTest {

    private Order order;
    private Addition doubleCheese;
    private Product xBurguer;

    private Store store;
    private Customer customer;
    private Deliverer deliverer;

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
        this.order.deliver(deliverer);
        this.order.markAsArrived();
    }

    @Test
    public void markOrderAsReceived() {
        MarkOrderAsReceivedCommand command = new MarkOrderAsReceivedCommand(order);

        MarkOrderAsReceivedCommandHandler handler = new MarkOrderAsReceivedCommandHandler();

        Order result = handler.handle(command);

        assertEquals("Received", result.getOrderState().getStateName());
    }

    @Test
    void failToMarkOrderAsReceivedAlreadyDelivering() {
        try {
            order.markAsReceived();

            MarkOrderAsReceivedCommand command = new MarkOrderAsReceivedCommand(order);

            MarkOrderAsReceivedCommandHandler commandHandler = new MarkOrderAsReceivedCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order already received", e.getMessage());
        }
    }

    @Test
    void failToMarkOrderAsReceivedWithoutOrder() {
        try {
            MarkOrderAsReceivedCommand command = new MarkOrderAsReceivedCommand(null);

            MarkOrderAsReceivedCommandHandler commandHandler = new MarkOrderAsReceivedCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order is required", e.getMessage());
        }
    }
}