package application.useCases.orders.startOrderPreparation;

import application.mediators.OrderMediator;
import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.customers.entities.Customer;
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

class StartOrderPreparationCommandHandlerTest {
    private Order order;
    private Addition doubleCheese;
    private Product xBurguer;

    private Store store;
    private Customer customer;

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
    void startOrderPreparation() {
        order.placeOrder(customer, store);

        StartOrderPreparationCommand command = new StartOrderPreparationCommand(order);

        StartOrderPreparationCommandHandler commandHandler = new StartOrderPreparationCommandHandler();

        Order result = commandHandler.handle(command);

        assertEquals("Preparing", result.getOrderState().getStateName());
    }

    @Test
    void failToStartOrderPreparationAlreadyPlaced() {
        try {
            order.placeOrder(customer, store);

            order.startOrderPreparation();

            StartOrderPreparationCommand command = new StartOrderPreparationCommand(order);

            StartOrderPreparationCommandHandler commandHandler = new StartOrderPreparationCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order already in preparation", e.getMessage());
        }
    }

    @Test
    void failToStartOrderPreparationWithoutOrder() {
        try {
            StartOrderPreparationCommand command = new StartOrderPreparationCommand(null);

            StartOrderPreparationCommandHandler commandHandler = new StartOrderPreparationCommandHandler();

            commandHandler.handle(command);

            fail();
        } catch (IllegalStateException e) {
            assertEquals("Order is required", e.getMessage());
        }
    }
}