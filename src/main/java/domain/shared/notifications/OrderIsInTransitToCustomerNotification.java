package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class OrderIsInTransitToCustomerNotification implements NotificationType {
    private static final OrderIsInTransitToCustomerNotification orderPendingApprovalNotification = new OrderIsInTransitToCustomerNotification();

    private OrderIsInTransitToCustomerNotification() {};

    public static OrderIsInTransitToCustomerNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "Order is in transit to customer.";
    }
}
