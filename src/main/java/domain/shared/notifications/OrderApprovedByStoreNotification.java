package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class OrderApprovedByStoreNotification implements NotificationType {
    private static final OrderApprovedByStoreNotification orderPendingApprovalNotification = new OrderApprovedByStoreNotification();

    private OrderApprovedByStoreNotification() {};

    public static OrderApprovedByStoreNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "Your order was approved by the store and started the preparation.";
    }
}
