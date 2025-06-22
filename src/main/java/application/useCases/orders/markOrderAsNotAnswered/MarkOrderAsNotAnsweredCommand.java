package application.useCases.orders.markOrderAsNotAnswered;

import domain.customers.entities.Customer;
import domain.delivery.entities.Deliverer;
import domain.orders.entities.Order;
import domain.shared.abstractions.Command;
import domain.stores.entities.Store;

public class MarkOrderAsNotAnsweredCommand extends Command {
    private Order order;
    private Store store;
    private Customer customer;
    private Deliverer deliverer;

    public MarkOrderAsNotAnsweredCommand(Order order, Customer customer, Store store, Deliverer deliverer) {
        this.order = order;
        this.store = store;
        this.customer = customer;
        this.deliverer = deliverer;
    }

    public Order getOrder() {
        return order;
    }

    public Store getStore() {
        return store;
    }

    public Customer getCustomer() {
        return customer;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }
}
