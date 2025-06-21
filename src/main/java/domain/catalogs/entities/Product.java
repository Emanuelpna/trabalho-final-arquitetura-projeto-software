package domain.catalogs.entities;

import domain.shared.entities.Entity;
import domain.stores.abstractions.StoreType;

import java.util.List;
import java.util.UUID;

public class Product extends Entity {
    private String name;
    private double price;

    private StoreType storeType;

    private List<Addition> additions;
    
    public Product(UUID id, String name, double price, StoreType storeType, List<Addition> additions) {
        super(id);
        this.name = name;
        this.price = price;
        this.storeType = storeType;
        this.additions = additions;
    }

    public Product(String name, double price, StoreType storeType, List<Addition> additions) {
        super(UUID.randomUUID());
        this.name = name;
        this.price = price;
        this.storeType = storeType;
        this.additions = additions;
    }

    public StoreType getStoreType() {
        return storeType;
    }

    public double getPrice() {
        return price;
    }

    public List<Addition> getAdditions() {
        return additions;
    }
}
