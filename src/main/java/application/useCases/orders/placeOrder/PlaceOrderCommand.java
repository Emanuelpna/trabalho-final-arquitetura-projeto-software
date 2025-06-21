package application.useCases.orders.placeOrder;

import domain.customers.entities.Customer;
import domain.orders.entities.Order;
import domain.shared.abstractions.Command;
import domain.stores.abstractions.entities.Store;

public class PlaceOrderCommand extends Command {
    private Order order;
    private Store store;
    private Customer customer;


    public PlaceOrderCommand(Order order, Customer customer, Store store) {
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
