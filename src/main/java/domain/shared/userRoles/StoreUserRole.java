package domain.shared.userRoles;

import domain.shared.abstractions.UserRole;
import domain.shared.abstractions.UserRoleType;
import domain.shared.notifications.OrderPendingApprovalNotification;

public class StoreUserRole extends UserRole {
    public StoreUserRole(UserRole superior) {
        super(UserRoleType.STORE);

        notifications.add(OrderPendingApprovalNotification.getNotification());

        setSuperiorUserRole(superior);
    }

    @Override
    public String getRole() {
        return "Store";
    }
}
