package domain.shared.notifications;

import domain.shared.abstractions.NotificationType;

public class CustomerDidntAnswerTheDelivererNotification implements NotificationType {
    private static final CustomerDidntAnswerTheDelivererNotification orderPendingApprovalNotification = new CustomerDidntAnswerTheDelivererNotification();

    private CustomerDidntAnswerTheDelivererNotification() {};

    public static CustomerDidntAnswerTheDelivererNotification getNotification() {
        return orderPendingApprovalNotification;
    }

    public String getNotificationContent() {
        return "Customer didn't answer the deliverer at the destination address.";
    }
}
