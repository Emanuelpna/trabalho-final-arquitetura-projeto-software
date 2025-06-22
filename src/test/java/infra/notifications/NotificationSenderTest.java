package infra.notifications;

import domain.customers.entities.Customer;
import domain.delivery.entities.Deliverer;
import domain.shared.abstractions.Notification;
import domain.shared.abstractions.NotificationToBeSent;
import domain.shared.notifications.OrderApprovedByStoreNotification;
import domain.shared.notifications.OrderIsWaitingForPickupOnStoreNotification;
import domain.shared.notifications.OrderPendingApprovalNotification;
import domain.stores.abstractions.StoreType;
import domain.stores.entities.Store;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class NotificationSenderTest {
    private Store store;
    private Customer customer;
    private Deliverer deliverer;
    private Notification notificationToStore;
    private Notification notificationToCustomer;
    private Notification notificationToDeliverer;

    @BeforeEach
    void setUp() {
        this.customer = new Customer("Nome do Cliente");
        this.deliverer = new Deliverer("Nome do Entregador");
        this.store = new Store("Nome da Loja", StoreType.RESTAURANT);

        this.notificationToStore = new Notification(OrderPendingApprovalNotification.getNotification());
        this.notificationToCustomer = new Notification(OrderApprovedByStoreNotification.getNotification());
        this.notificationToDeliverer = new Notification(OrderIsWaitingForPickupOnStoreNotification.getNotification());
    }

    @Test
    void testNotificationSenderStoringNotificationToBeSentToUser() {
        NotificationSender.getInstance().addNotification(notificationToCustomer, List.of(customer));

        assertTrue(
                NotificationSender.getInstance()
                        .getNotificationsToBeSent()
                        .stream()
                        .anyMatch(notificationToBeSent -> notificationToBeSent.getNotification().equals(notificationToCustomer))
        );
    }

    @Test
    void testNotificationSenderSendingNotificationToUser() {
        NotificationSender.getInstance().addNotification(notificationToCustomer, List.of(customer));

        NotificationSender.getInstance().dispatchNotifications();

        assertEquals("Your order was approved by the store and started the preparation.", customer.getLastNotification());
    }

    @Test
    void testNotificationSenderMarkingNotificationAsSent() {
        NotificationSender.getInstance().addNotification(notificationToCustomer, List.of(customer));

        NotificationSender.getInstance().dispatchNotifications();

        Optional<NotificationToBeSent> notificationToBeSentToUser = NotificationSender.getInstance()
                .getNotificationsToBeSent()
                .stream()
                .filter(notificationToBeSent -> notificationToBeSent.getNotification().equals(notificationToCustomer))
                .findAny();

        if (notificationToBeSentToUser.isEmpty()) {
            fail();
        }

        NotificationToBeSent sentNotification = notificationToBeSentToUser.get();

        assertTrue(sentNotification.isSent());
    }

    @Test
    void testNotificationFromOtherUserRolesNotBeingSentToCustomer() {
        NotificationSender.getInstance().addNotification(notificationToStore, List.of(customer, deliverer, store));
        NotificationSender.getInstance().addNotification(notificationToCustomer, List.of(customer, deliverer, store));
        NotificationSender.getInstance().addNotification(notificationToDeliverer, List.of(customer, deliverer, store));

        NotificationSender.getInstance().dispatchNotifications();

        assertEquals(1, customer.getNotifications().size());
    }

    @Test
    void testNotificationFromOtherUserRolesNotBeingSentToStore() {
        NotificationSender.getInstance().addNotification(notificationToStore, List.of(customer, deliverer, store));
        NotificationSender.getInstance().addNotification(notificationToCustomer, List.of(customer, deliverer, store));
        NotificationSender.getInstance().addNotification(notificationToDeliverer, List.of(customer, deliverer, store));

        NotificationSender.getInstance().dispatchNotifications();

        assertEquals(1, store.getNotifications().size());
    }

    @Test
    void testNotificationFromOtherUserRolesNotBeingSentToDeliverer() {
        NotificationSender.getInstance().addNotification(notificationToStore, List.of(customer, deliverer, store));
        NotificationSender.getInstance().addNotification(notificationToCustomer, List.of(customer, deliverer, store));
        NotificationSender.getInstance().addNotification(notificationToDeliverer, List.of(customer, deliverer, store));

        NotificationSender.getInstance().dispatchNotifications();

        assertEquals(1, deliverer.getNotifications().size());
    }
}