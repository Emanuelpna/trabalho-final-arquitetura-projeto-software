package domain.delivery.entities;

import domain.shared.abstractions.Notification;
import domain.shared.notifications.OrderApprovedByStoreNotification;

import domain.shared.notifications.OrderIsWaitingForPickupOnStoreNotification;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DelivererTest {
    @Test
    void testDelivererReceivedANotification() {
        Deliverer deliverer = new Deliverer("Nome do Entregador");
        Notification notification = new Notification(OrderIsWaitingForPickupOnStoreNotification.getNotification());

        notification.addObserver(deliverer);

        notification.sendNotificaion();

        assertEquals("Order is waiting for pickup on store.", deliverer.getLastNotification());
    }
}