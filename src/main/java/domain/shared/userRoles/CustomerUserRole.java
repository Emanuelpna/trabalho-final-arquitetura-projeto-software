package domain.shared.userRoles;

import domain.shared.abstractions.UserRole;
import domain.shared.abstractions.UserRoleType;
import domain.shared.notifications.*;

public class CustomerUserRole extends UserRole {
    public CustomerUserRole(UserRole superior) {
        super(UserRoleType.CUSTOMER);

        notifications.add(OrderApprovedByStoreNotification.getNotification());
        notifications.add(OrderHasArrivedOnCustomerAddressNotification.getNotification());
        notifications.add(OrderHasBeenRejectedByCustomerNotification.getNotification());
        notifications.add(CustomerDidntAnswerTheDelivererNotification.getNotification());
        notifications.add(CustomerReceivedTheDeliveryWithSuccessNotification.getNotification());
        notifications.add(OrderIsInTransitToCustomerNotification.getNotification());

        setSuperiorUserRole(superior);
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}
