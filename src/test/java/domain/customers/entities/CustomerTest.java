package domain.customers.entities;

import domain.shared.abstractions.Notification;
import domain.shared.notifications.OrderApprovedByStoreNotification;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void testCustomerReceivedANotification() {
        Customer customer = new Customer("Nome do Cliente");
        Notification notification = new Notification(OrderApprovedByStoreNotification.getNotification());

        notification.addObserver(customer);

        notification.sendNotificaion();

        assertEquals("Your order was approved by the store and started the preparation.", customer.getLastNotification());
    }
}