package domain.shared.userRoles;

import domain.shared.abstractions.UserRole;
import domain.shared.abstractions.UserRoleType;
import domain.shared.notifications.OrderApprovedByStoreNotification;

public class CustomerUserRole extends UserRole {
    public CustomerUserRole(UserRole superior) {
        super(UserRoleType.CUSTOMER);

        notifications.add(OrderApprovedByStoreNotification.getNotification());

        setSuperiorUserRole(superior);
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}
