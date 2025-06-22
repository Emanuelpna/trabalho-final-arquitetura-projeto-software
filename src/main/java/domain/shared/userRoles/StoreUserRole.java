package domain.shared.userRoles;

import domain.shared.abstractions.UserRole;
import domain.shared.abstractions.UserRoleType;
import domain.shared.notifications.*;

public class StoreUserRole extends UserRole {
    public StoreUserRole(UserRole superior) {
        super(UserRoleType.STORE);

        notifications.add(OrderPendingApprovalNotification.getNotification());
        notifications.add(OrderCancelledByCustomerNotification.getNotification());
        notifications.add(OrderHasArrivedOnCustomerAddressNotification.getNotification());
        notifications.add(OrderHasBeenRejectedByCustomerNotification.getNotification());
        notifications.add(CustomerDidntAnswerTheDelivererNotification.getNotification());
        notifications.add(CustomerReceivedTheDeliveryWithSuccessNotification.getNotification());
        notifications.add(OrderIsInTransitToCustomerNotification.getNotification());

        setSuperiorUserRole(superior);
    }

    @Override
    public String getRole() {
        return "Store";
    }
}
