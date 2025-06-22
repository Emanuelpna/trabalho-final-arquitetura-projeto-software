package domain.shared.userRoles;

import domain.shared.abstractions.UserRole;
import domain.shared.abstractions.UserRoleType;
import domain.shared.notifications.OrderIsWaitingForPickupOnStoreNotification;

public class DelivererUserRole extends UserRole {
    public DelivererUserRole(UserRole superior) {
        super(UserRoleType.DELIVERER);

        notifications.add(OrderIsWaitingForPickupOnStoreNotification.getNotification());

        setSuperiorUserRole(superior);
    }

    @Override
    public String getRole() {
        return "Deliverer";
    }
}
