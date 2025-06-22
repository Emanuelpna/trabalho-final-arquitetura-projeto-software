package application.mediators;

import application.useCases.orders.placeOrder.PlaceOrderCommand;
import application.useCases.orders.placeOrder.PlaceOrderCommandHandler;
import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.customers.entities.Customer;
import domain.delivery.entities.Deliverer;
import domain.orders.entities.Order;
import domain.orders.valueObjects.Address;
import domain.orders.abstractions.OrderBuilder;
import domain.orders.abstractions.OrderBuilderFactory;
import domain.stores.abstractions.StoreType;
import domain.stores.entities.Store;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

class OrderMediatorTest {
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

        this.mediator = new OrderMediator();
    }

    @Test
    void mediatorSendCommandToHandler() {
        PlaceOrderCommand command = new PlaceOrderCommand(order, customer, store);

        PlaceOrderCommandHandler commandHandler = new PlaceOrderCommandHandler();

        Order result = mediator.send(command, commandHandler);

        assertEquals("Pending", result.getOrderState().getStateName());
        assertEquals("A customer has placed an order to be approved.", this.store.getLastNotification());
    }
}