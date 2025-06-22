package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class OrderHasBeenRejectedByCustomerNotification implements NotificationType {
    private static final OrderHasBeenRejectedByCustomerNotification orderPendingApprovalNotification = new OrderHasBeenRejectedByCustomerNotification();

    private OrderHasBeenRejectedByCustomerNotification() {};

    public static OrderHasBeenRejectedByCustomerNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "Order has been rejecte by the customer.";
    }
}
