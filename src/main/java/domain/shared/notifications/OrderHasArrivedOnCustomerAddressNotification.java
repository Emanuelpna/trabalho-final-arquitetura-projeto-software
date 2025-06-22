package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class OrderHasArrivedOnCustomerAddressNotification implements NotificationType {
    private static final OrderHasArrivedOnCustomerAddressNotification orderPendingApprovalNotification = new OrderHasArrivedOnCustomerAddressNotification();

    private OrderHasArrivedOnCustomerAddressNotification() {};

    public static OrderHasArrivedOnCustomerAddressNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "Order has arrived to the customer address.";
    }
}
