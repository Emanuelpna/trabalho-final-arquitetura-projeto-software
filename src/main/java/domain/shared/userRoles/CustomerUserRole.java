package domain.shared.userRoles;

import domain.shared.abstractions.UserRole;
import domain.shared.abstractions.UserRoleType;

public class CustomerUserRole extends UserRole {
    public CustomerUserRole(UserRole superior) {
        super(UserRoleType.CUSTOMER);

        // notifications.add()

        setSuperiorUserRole(superior);
    }

    @Override
    public String getRole() {
        return "Customer";
    }
}
