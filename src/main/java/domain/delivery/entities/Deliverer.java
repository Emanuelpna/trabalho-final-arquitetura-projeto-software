package domain.delivery.entities;

import domain.shared.abstractions.UserRoleType;
import domain.shared.entities.User;

import java.util.UUID;

public class Deliverer extends User {
    public Deliverer(UUID id, String name) {
        super(id, name, UserRoleType.DELIVERER);
    }

    public Deliverer(String name) {
        super(UUID.randomUUID(), name, UserRoleType.DELIVERER);
    }
}
