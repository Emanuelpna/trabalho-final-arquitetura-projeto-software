package application.useCases.orders.startOrderPreparation;

import domain.customers.entities.Customer;
import domain.orders.entities.Order;
import domain.shared.abstractions.Command;
import domain.stores.entities.Store;

public class StartOrderPreparationCommand extends Command {
    Order order;
    private Store store;
    private Customer customer;

    public StartOrderPreparationCommand(Order order, Customer customer, Store store) {
        this.order = order;
        this.store = store;
        this.customer = customer;
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
}
