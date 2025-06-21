package domain.customers.entities;

import domain.shared.abstractions.UserRoleType;
import domain.shared.entities.User;

import java.util.*;

public class Customer extends User {
    public Customer(UUID id, String name) {
        super(id, name, UserRoleType.CUSTOMER);
    }

    public Customer(String name) {
        super(UUID.randomUUID(), name, UserRoleType.CUSTOMER);
    }
}
