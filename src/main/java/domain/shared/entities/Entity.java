package domain.shared.entities;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity {

    private UUID id;

    public Entity(UUID id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Entity other)) {
            return false;
        } else {
            return Objects.equals(id, other.id);
        }
    }
}
