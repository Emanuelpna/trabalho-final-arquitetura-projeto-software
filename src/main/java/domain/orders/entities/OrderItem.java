package domain.orders.entities;

import domain.catalogs.entities.Addition;
import domain.catalogs.entities.Product;
import domain.shared.entities.Entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderItem extends Entity {
    private Product product;
    private int quantity;
    private List<Addition> selectedAdditions = new ArrayList<>();

    public OrderItem(UUID id, Product product, int quantity) {
        super(id);

        this.product = product;
        this.quantity = quantity;
    }

    public OrderItem(Product product, int quantity) {
        super(UUID.randomUUID());

        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void addAddition(Addition addition) {
        if(!this.product.getAdditions().contains(addition)) {
            throw new IllegalArgumentException("Addition cannot be added to this Product");
        }

        selectedAdditions.add(addition);
    }

    double calculateTotalPrice() {
        return product.getPrice() * quantity;
    }
}
