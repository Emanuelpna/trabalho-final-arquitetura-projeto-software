package domain.catalogs.entities;

import domain.shared.entities.Entity;

import java.util.UUID;

public class Addition extends Entity {
    private String name;
    private String description;
    private double price;

    public Addition(UUID id, String name, String description, double price) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public Addition(String name, String description, double price) {
        super(UUID.randomUUID());
        this.name = name;
        this.description = description;
        this.price = price;
    }


}
