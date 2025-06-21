package domain.shared.userRoles;

import domain.shared.abstractions.UserRole;
import domain.shared.abstractions.UserRoleType;

public class DelivererUserRole extends UserRole {
    public DelivererUserRole(UserRole superior) {
        super(UserRoleType.DELIVERER);

        //        notifications.add()
        setSuperiorUserRole(superior);
    }

    @Override
    public String getRole() {
        return "Deliverer";
    }
}
