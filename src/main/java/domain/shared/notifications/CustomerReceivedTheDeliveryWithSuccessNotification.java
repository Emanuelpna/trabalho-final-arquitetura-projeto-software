package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class CustomerReceivedTheDeliveryWithSuccessNotification implements NotificationType {
    private static final CustomerReceivedTheDeliveryWithSuccessNotification orderPendingApprovalNotification = new CustomerReceivedTheDeliveryWithSuccessNotification();

    private CustomerReceivedTheDeliveryWithSuccessNotification() {};

    public static CustomerReceivedTheDeliveryWithSuccessNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "Customer received the delivery with success notification";
    }
}
