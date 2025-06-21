package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class OrderIsWaitingForPickupOnStoreNotification implements NotificationType {
    private static final OrderIsWaitingForPickupOnStoreNotification orderPendingApprovalNotification = new OrderIsWaitingForPickupOnStoreNotification();

    private OrderIsWaitingForPickupOnStoreNotification() {};

    public static OrderIsWaitingForPickupOnStoreNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "Order is waiting for pickup on store.";
    }
}
