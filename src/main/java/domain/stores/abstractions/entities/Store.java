package domain.stores.abstractions.entities;

import domain.shared.abstractions.UserRoleType;
import domain.shared.entities.User;
import domain.stores.abstractions.StoreType;

import java.util.UUID;

public class Store extends User {
    private StoreType storeType;

    public Store(UUID id, String name, StoreType storeType) {
        super(id, name, UserRoleType.STORE);

        this.storeType = storeType;
    }

    public Store(String name, StoreType storeType) {
        super(UUID.randomUUID(), name, UserRoleType.STORE);

        this.storeType = storeType;
    }
}
