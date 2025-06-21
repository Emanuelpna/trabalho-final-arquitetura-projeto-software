package domain.stores.entities;

import domain.delivery.entities.Deliverer;
import domain.shared.abstractions.Notification;
import domain.shared.notifications.OrderIsWaitingForPickupOnStoreNotification;
import domain.stores.abstractions.StoreType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StoreTest {
    @Test
    void testStoreCreatedWithCorrectStoreType() {
        Store store = new Store("Nome da Loja", StoreType.RESTAURANT);

        assertEquals(StoreType.RESTAURANT, store.getStoreType());
    }

    @Test
    void testStoreReceivedANotification() {
        Store store = new Store("Nome da Loja", StoreType.RESTAURANT);
        Notification notification = new Notification(OrderIsWaitingForPickupOnStoreNotification.getNotification());

        notification.addObserver(store);

        notification.sendNotificaion();

        assertEquals("Order is waiting for pickup on store.", store.getLastNotification());
    }
}