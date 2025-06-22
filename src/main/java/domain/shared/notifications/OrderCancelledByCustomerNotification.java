package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class OrderCancelledByCustomerNotification implements NotificationType {
    private static final OrderCancelledByCustomerNotification orderPendingApprovalNotification = new OrderCancelledByCustomerNotification();

    private OrderCancelledByCustomerNotification() {};

    public static OrderCancelledByCustomerNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "Order was cancelled by the customer.";
    }
}
