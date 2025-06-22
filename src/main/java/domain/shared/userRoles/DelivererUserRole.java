package domain.shared.userRoles;

import domain.shared.abstractions.UserRole;
import domain.shared.abstractions.UserRoleType;
import domain.shared.notifications.*;

public class DelivererUserRole extends UserRole {
    public DelivererUserRole(UserRole superior) {
        super(UserRoleType.DELIVERER);

        notifications.add(OrderIsWaitingForPickupOnStoreNotification.getNotification());
        notifications.add(CustomerReceivedTheDeliveryWithSuccessNotification.getNotification());
        notifications.add(OrderCancelledByCustomerNotification.getNotification());

        setSuperiorUserRole(superior);
    }

    @Override
    public String getRole() {
        return "Deliverer";
    }
}
