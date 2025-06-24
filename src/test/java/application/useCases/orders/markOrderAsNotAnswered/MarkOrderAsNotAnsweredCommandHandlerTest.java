package application.useCases.orders.markOrderAsNotAnswered;

import application.mediators.OrderMediator;
import application.useCases.orders.markOrderAsNotAnswered.MarkOrderAsNotAnsweredCommand;
import application.useCases.orders.markOrderAsNotAnswered.MarkOrderAsNotAnsweredCommandHandler;
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

class MarkOrderAsNotAnsweredCommandHandlerTest {
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
    public void markOrderAsNotAnswered() {
        MarkOrderAsNotAnsweredCommand command = new MarkOrderAsNotAnsweredCommand(order);

        MarkOrderAsNotAnsweredCommandHandler handler = new MarkOrderAsNotAnsweredCommandHandler();

        Order result = handler.handle(command);

        assertEquals("NoAnswer", result.getOrderState().getStateName());
    }

    @Test
    void failToMarkOrderAsNotAnsweredAlreadyDelivering() {
        try {
            order.markAsNotAnswered();

            MarkOrderAsNotAnsweredCommand command = new MarkOrderAsNotAnsweredCommand(order);

            MarkOrderAsNotAnsweredCommandHandler commandHandler = new MarkOrderAsNotAnsweredCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order already has been marked as not-answered", e.getMessage());
        }
    }

    @Test
    void failToMarkOrderAsNotAnsweredWithoutOrder() {
        try {
            MarkOrderAsNotAnsweredCommand command = new MarkOrderAsNotAnsweredCommand(null);

            MarkOrderAsNotAnsweredCommandHandler commandHandler = new MarkOrderAsNotAnsweredCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order is required", e.getMessage());
        }
    }
}