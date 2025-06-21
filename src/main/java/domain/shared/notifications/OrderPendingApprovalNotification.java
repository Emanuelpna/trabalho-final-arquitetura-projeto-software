package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class OrderPendingApprovalNotification implements NotificationType {
    private static final OrderPendingApprovalNotification orderPendingApprovalNotification = new OrderPendingApprovalNotification();

    private OrderPendingApprovalNotification() {};

    public static OrderPendingApprovalNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "A customer has placed an order to be approved.";
    }
}
