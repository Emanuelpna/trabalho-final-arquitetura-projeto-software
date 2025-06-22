package domain.orders.states;

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

class OrderStateTest {
    public Order order;
    private Store store;
    private Customer customer;
    private Deliverer deliverer;

    @BeforeEach
    void setUp() {
        Addition doubleCheese = new Addition("Dobro de Queijo", "O dobro de cheddar no seu lanche", 4.99);
        Product xBurguer = new Product("X-Burguer", 25.99, StoreType.RESTAURANT, List.of(doubleCheese));

        OrderBuilder restaurantOrderBuilder = OrderBuilderFactory.getOrder("RestaurantOrderBuilder");
        this.order = restaurantOrderBuilder
                .addShippingAddress(new Address("Rua Aquela Lá", "Juiz de Fora", "MG", "Brasil"))
                .addProduct()
                .chooseProduct(xBurguer)
                .addAddition(doubleCheese)
                .withQuantity(1)
                .build()
                .build();

        this.store = new Store("Hamburgueria 1", StoreType.RESTAURANT);
        this.customer = new Customer("Cliente 1");
        this.deliverer = new Deliverer("Entregador 1");
    }

    //region PlacingOrder
    @Test
    void placeNewRestaurantOrder() {
        assertEquals("Pending", order.getOrderState().getStateName());
    }

    @Test
    void placeAlreadyPendingRestaurantOrder() {
        try {
            order.placeOrder(customer, store);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order already placed", exception.getMessage());
        }
    }

    @Test
    void placeAlreadyPreparingRestaurantOrder() {
        try {
            order.setOrderState(PreparingOrderState.getInstance());

            order.placeOrder(customer, store);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order already placed", exception.getMessage());
        }
    }

    @Test
    void placeAlreadyDeliveringRestaurantOrder() {
        try {
            order.setOrderState(DeliveringOrderState.getInstance());

            order.placeOrder(customer, store);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order already placed", exception.getMessage());
        }
    }

    @Test
    void placeAlreadyArrivedRestaurantOrder() {
        try {
            order.setOrderState(ArrivedOrderState.getInstance());

            order.placeOrder(customer, store);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order already placed", exception.getMessage());
        }
    }

    @Test
    void placeAlreadyRejectedRestaurantOrder() {
        try {
            order.setOrderState(RejectedOrderState.getInstance());

            order.placeOrder(customer, store);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order already placed", exception.getMessage());
        }
    }

    @Test
    void placeAlreadyReceivedRestaurantOrder() {
        try {
            order.setOrderState(ReceivedOrderState.getInstance());

            order.placeOrder(customer, store);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order already placed", exception.getMessage());
        }
    }

    @Test
    void placeAlreadyNoAnsweredRestaurantOrder() {
        try {
            order.setOrderState(NoAnswerOrderState.getInstance());

            order.placeOrder(customer, store);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order already placed", exception.getMessage());
        }
    }

    @Test
    void placeAlreadyCancelledRestaurantOrder() {
        try {
            order.setOrderState(CancelledOrderState.getInstance());

            order.placeOrder(customer, store);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order already placed", exception.getMessage());
        }
    }
    //endregion

    //region StartingOrderPreparation
    @Test
    void startRestaurantOrderPreparationOfPendingOrder() {
        order.startOrderPreparation();

        assertEquals("Preparing", order.getOrderState().getStateName());
    }

    @Test
    void startRestaurantOrderPreparationOfPreparingOrder() {
        try {
            order.setOrderState(PreparingOrderState.getInstance());

            order.startOrderPreparation();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order preparation failed", exception.getMessage());
        }
    }

    @Test
    void startRestaurantOrderPreparationOfDeliveringOrder() {
        try {
            order.setOrderState(DeliveringOrderState.getInstance());

            order.startOrderPreparation();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order preparation failed", exception.getMessage());
        }
    }

    @Test
    void startRestaurantOrderPreparationOfArrivedOrder() {
        try {
            order.setOrderState(ArrivedOrderState.getInstance());

            order.startOrderPreparation();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order preparation failed", exception.getMessage());
        }
    }

    @Test
    void startRestaurantOrderPreparationOfRejectedOrder() {
        order.setOrderState(RejectedOrderState.getInstance());

        order.startOrderPreparation();

        assertEquals("Preparing", order.getOrderState().getStateName());
    }

    @Test
    void startRestaurantOrderPreparationOfNoAnsweredOrder() {
        try {
            order.setOrderState(NoAnswerOrderState.getInstance());

            order.startOrderPreparation();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order preparation failed", exception.getMessage());
        }
    }

    @Test
    void startRestaurantOrderPreparationOfReceivedOrder() {
        try {
            order.setOrderState(ReceivedOrderState.getInstance());

            order.startOrderPreparation();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order preparation failed", exception.getMessage());
        }
    }

    @Test
    void startRestaurantOrderPreparationOfCancelledOrder() {
        try {
            order.setOrderState(CancelledOrderState.getInstance());

            order.startOrderPreparation();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order preparation failed", exception.getMessage());
        }
    }
    //endregion

    //region DeliveringOrder
    @Test
    void deliverRestaurantOrderOfPendingOrder() {
        try {
            order.deliver(deliverer);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be sent to delivery", exception.getMessage());
        }
    }

    @Test
    void deliverRestaurantOrderOfPreparingOrder() {
        order.setOrderState(PreparingOrderState.getInstance());

        order.deliver(deliverer);

        assertEquals("Delivering", order.getOrderState().getStateName());
    }

    @Test
    void deliverRestaurantOrderOfDeliveringOrder() {
        try {
            order.setOrderState(DeliveringOrderState.getInstance());

            order.deliver(deliverer);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be sent to delivery", exception.getMessage());
        }
    }

    @Test
    void deliverRestaurantOrderOfArrivedOrder() {
        try {
            order.setOrderState(ArrivedOrderState.getInstance());

            order.deliver(deliverer);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be sent to delivery", exception.getMessage());
        }
    }

    @Test
    void deliverRestaurantOrderOfRejectedOrder() {
        try {
            order.setOrderState(RejectedOrderState.getInstance());

            order.deliver(deliverer);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be sent to delivery", exception.getMessage());
        }
    }

    @Test
    void deliverRestaurantOrderOfNoAnsweredOrder() {
        try {
            order.setOrderState(NoAnswerOrderState.getInstance());

            order.deliver(deliverer);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be sent to delivery", exception.getMessage());
        }
    }

    @Test
    void deliverRestaurantOrderOfReceivedOrder() {
        try {
            order.setOrderState(ReceivedOrderState.getInstance());

            order.deliver(deliverer);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be sent to delivery", exception.getMessage());
        }
    }

    @Test
    void deliverRestaurantOrderOfCancelledOrder() {
        try {
            order.setOrderState(CancelledOrderState.getInstance());

            order.deliver(deliverer);

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be sent to delivery", exception.getMessage());
        }
    }
    //endregion

    //region MarkingOrderAsArrived
    @Test
    void markPendingRestaurantOrderAsArrived() {
        try {
            order.markAsArrived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as arrived", exception.getMessage());
        }
    }

    @Test
    void markPreparingRestaurantOrderAsArrived() {
        try {
            order.setOrderState(PreparingOrderState.getInstance());

            order.markAsArrived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as arrived", exception.getMessage());
        }
    }

    @Test
    void markDeliveringRestaurantOrderAsArrived() {
        order.setOrderState(DeliveringOrderState.getInstance());

        order.markAsArrived();

        assertEquals("Arrived", order.getOrderState().getStateName());
    }

    @Test
    void markArrivedRestaurantOrderAsArrived() {
        try {
            order.setOrderState(PreparingOrderState.getInstance());

            order.markAsArrived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as arrived", exception.getMessage());
        }
    }

    @Test
    void markRejectedRestaurantOrderAsArrived() {
        try {
            order.setOrderState(RejectedOrderState.getInstance());

            order.markAsArrived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as arrived", exception.getMessage());
        }
    }

    @Test
    void markNoAnswerRestaurantOrderAsArrived() {
        try {
            order.setOrderState(NoAnswerOrderState.getInstance());

            order.markAsArrived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as arrived", exception.getMessage());
        }
    }

    @Test
    void markReceivedRestaurantOrderAsArrived() {
        try {
            order.setOrderState(ReceivedOrderState.getInstance());

            order.markAsArrived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as arrived", exception.getMessage());
        }
    }

    @Test
    void markCancelledRestaurantOrderAsArrived() {
        try {
            order.setOrderState(CancelledOrderState.getInstance());

            order.markAsArrived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as arrived", exception.getMessage());
        }
    }
    //endregion

    //region RejectingOrder
    @Test
    void rejectPendingRestaurantOrder() {
        try {
            order.reject();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be rejected", exception.getMessage());
        }
    }

    @Test
    void rejectPreparingRestaurantOrder() {
        try {
            order.setOrderState(PreparingOrderState.getInstance());

            order.reject();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be rejected", exception.getMessage());
        }
    }

    @Test
    void rejectDeliveringRestaurantOrder() {
        try {
            order.setOrderState(DeliveringOrderState.getInstance());

            order.reject();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be rejected", exception.getMessage());
        }
    }

    @Test
    void rejectArrivedRestaurantOrder() {
        order.setOrderState(ArrivedOrderState.getInstance());

        order.reject();

        assertEquals("Rejected", order.getOrderState().getStateName());
    }

    @Test
    void rejectRejectedRestaurantOrder() {
        try {
            order.setOrderState(RejectedOrderState.getInstance());

            order.reject();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be rejected", exception.getMessage());
        }
    }

    @Test
    void rejectNoAnsweredRestaurantOrder() {
        try {
            order.setOrderState(NoAnswerOrderState.getInstance());

            order.reject();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be rejected", exception.getMessage());
        }
    }

    @Test
    void rejectReceivedRestaurantOrder() {
        try {
            order.setOrderState(ReceivedOrderState.getInstance());

            order.reject();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be rejected", exception.getMessage());
        }
    }

    @Test
    void rejectCancelledRestaurantOrder() {
        try {
            order.setOrderState(CancelledOrderState.getInstance());

            order.reject();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be rejected", exception.getMessage());
        }
    }
    //endregion

    //region MarkingOrderAsNotAnswered
    @Test
    void markPendingRestaurantOrderAsNoAnswered() {
        try {
            order.markAsNotAnswered();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as not-answered", exception.getMessage());
        }
    }

    @Test
    void markPreparingRestaurantOrderAsNoAnswered() {
        try {
            order.setOrderState(PreparingOrderState.getInstance());

            order.markAsNotAnswered();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as not-answered", exception.getMessage());
        }
    }

    @Test
    void markDeliveringRestaurantOrderAsNoAnswered() {
        try {
            order.setOrderState(DeliveringOrderState.getInstance());

            order.markAsNotAnswered();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as not-answered", exception.getMessage());
        }
    }

    @Test
    void markArrivedRestaurantOrderAsNoAnswered() {
        order.setOrderState(ArrivedOrderState.getInstance());

        order.markAsNotAnswered();

        assertEquals("NoAnswer", order.getOrderState().getStateName());
    }

    @Test
    void markRejectedRestaurantOrderAsNoAnswered() {
        try {
            order.setOrderState(RejectedOrderState.getInstance());

            order.markAsNotAnswered();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as not-answered", exception.getMessage());
        }
    }

    @Test
    void markNoAsweredRestaurantOrderAsNoAnswered() {
        try {
            order.setOrderState(NoAnswerOrderState.getInstance());

            order.markAsNotAnswered();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as not-answered", exception.getMessage());
        }
    }

    @Test
    void markReceivedRestaurantOrderAsNoAnswered() {
        try {
            order.setOrderState(ReceivedOrderState.getInstance());

            order.markAsNotAnswered();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as not-answered", exception.getMessage());
        }
    }

    @Test
    void markCancelledRestaurantOrderAsNoAnswered() {
        try {
            order.setOrderState(CancelledOrderState.getInstance());

            order.markAsNotAnswered();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as not-answered", exception.getMessage());
        }
    }
    //endregion

    //region ReceivingOrder
    @Test
    void receivePendingRestaurantOrder() {
        try {
            order.markAsReceived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as received", exception.getMessage());
        }
    }

    @Test
    void receivePreparingRestaurantOrder() {
        try {
            order.setOrderState(PreparingOrderState.getInstance());

            order.markAsReceived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as received", exception.getMessage());
        }
    }

    @Test
    void receiveDeliveringRestaurantOrder() {
        try {
            order.setOrderState(DeliveringOrderState.getInstance());

            order.markAsReceived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as received", exception.getMessage());
        }
    }


    @Test
    void receiveArrivedRestaurantOrder() {
        order.setOrderState(ArrivedOrderState.getInstance());

        order.markAsReceived();

        assertEquals("Received", order.getOrderState().getStateName());
    }

    @Test
    void receiveRejectedRestaurantOrder() {
        try {
            order.setOrderState(RejectedOrderState.getInstance());

            order.markAsReceived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as received", exception.getMessage());
        }
    }

    @Test
    void receiveNoAnswerRestaurantOrder() {
        try {
            order.setOrderState(NoAnswerOrderState.getInstance());

            order.markAsReceived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as received", exception.getMessage());
        }
    }

    @Test
    void receiveReceivedRestaurantOrder() {
        try {
            order.setOrderState(ReceivedOrderState.getInstance());

            order.markAsReceived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as received", exception.getMessage());
        }
    }

    @Test
    void receiveCancelledRestaurantOrder() {
        try {
            order.setOrderState(CancelledOrderState.getInstance());

            order.markAsReceived();

            fail();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be marked as received", exception.getMessage());
        }
    }
    //endregion

    //region CancelingOrder
    @Test
    void cancelPendingRestaurantOrder() {
        order.cancel();

        assertEquals("Cancelled", order.getOrderState().getStateName());
    }

    @Test
    void cancelPreparingRestaurantOrder() {
        order.setOrderState(PreparingOrderState.getInstance());

        order.cancel();

        assertEquals("Cancelled", order.getOrderState().getStateName());
    }

    @Test
    void cancelDeliveringRestaurantOrder() {
        order.setOrderState(DeliveringOrderState.getInstance());

        order.cancel();

        assertEquals("Cancelled", order.getOrderState().getStateName());
    }

    @Test
    void cancelArrivedRestaurantOrder() {
        try {
            order.setOrderState(ArrivedOrderState.getInstance());

            order.cancel();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be cancelled", exception.getMessage());
        }
    }

    @Test
    void cancelRejectedRestaurantOrder() {
        order.setOrderState(RejectedOrderState.getInstance());

        order.cancel();

        assertEquals("Cancelled", order.getOrderState().getStateName());
    }

    @Test
    void cancelNotAnsweredRestaurantOrder() {
        try {
            order.setOrderState(NoAnswerOrderState.getInstance());

            order.cancel();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be cancelled", exception.getMessage());
        }
    }

    @Test
    void cancelReceivedRestaurantOrder() {
        try {
            order.setOrderState(ReceivedOrderState.getInstance());

            order.cancel();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be cancelled", exception.getMessage());
        }
    }

    @Test
    void cancelCancelledRestaurantOrder() {
        try {
            order.setOrderState(CancelledOrderState.getInstance());

            order.cancel();
        } catch (IllegalStateException exception) {
            assertEquals("Order cannot be cancelled", exception.getMessage());
        }
    }
    //endregion
}