package domain.orders.entities;

import domain.customers.entities.Customer;
import domain.delivery.entities.Deliverer;
import domain.orders.states.PendingOrderState;
import domain.orders.valueObjects.Address;
import domain.orders.states.OrderState;
import domain.shared.abstractions.Notification;
import domain.shared.entities.Entity;
import domain.shared.notifications.OrderPendingApprovalNotification;
import domain.stores.entities.Store;

import java.util.List;
import java.util.UUID;

public abstract class Order extends Entity {
    private Address shippingAddress;
    private List<OrderItem> orderItems;
    private OrderState state;

    private Store store;
    private Customer customer;
    private Deliverer deliverer;

    public Order(UUID id, Address shippingAddress, List<OrderItem> orderItems, OrderState state) {
        super(id);

        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.state = state;
    }

    public Order(Address shippingAddress, List<OrderItem> orderItems, OrderState state) {
        super(UUID.randomUUID());

        this.shippingAddress = shippingAddress;
        this.orderItems = orderItems;
        this.state = state;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public OrderState getOrderState() {
        return state;
    }

    public Address getShippingAddress() {
        return shippingAddress;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }


    public void setShippingAddress(Address shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setDeliverer(Deliverer deliverer) {
        this.deliverer = deliverer;
    }

    public void setOrderState(OrderState state) {
        this.state = state;
    }

    public abstract void processOrder();

    public List<Notification> placeOrder(Customer customer, Store store) {
        if (!this.getOrderState().equals(PendingOrderState.getInstance()) || this.store != null && this.customer != null) {
            throw new IllegalStateException("Order already placed");
        }

        this.setOrderState(PendingOrderState.getInstance());

        this.setStore(store);
        this.setCustomer(customer);

        return List.of(new Notification(OrderPendingApprovalNotification.getNotification()));
    }

    public List<Notification> startOrderPreparation() {
        boolean successfulStateChange = this.getOrderState().startOrderPreparation(this);

        if (!successfulStateChange) {
            throw new IllegalStateException("Order preparation failed");
        }

        return List.of(new Notification(OrderPendingApprovalNotification.getNotification()));
    }

    public List<Notification> deliver(Deliverer deliverer) {
        boolean successfulStateChange = this.getOrderState().deliver(this);

        if (!successfulStateChange) {
            throw new IllegalStateException("Order cannot be sent to delivery");
        }

        this.setDeliverer(deliverer);

        return List.of();
    }

    public List<Notification> markAsArrived() {
        boolean successfulStateChange = this.getOrderState().markAsArrived(this);

        if (!successfulStateChange) {
            throw new IllegalStateException("Order cannot be marked as arrived");
        }

        return List.of();
    }

    public List<Notification> markAsReceived() {
        boolean successfulStateChange = this.getOrderState().markAsReceived(this);

        if (!successfulStateChange) {
            throw new IllegalStateException("Order cannot be marked as received");
        }

        return List.of();
    }

    public List<Notification> reject() {
        boolean successfulStateChange = this.getOrderState().reject(this);

        if (!successfulStateChange) {
            throw new IllegalStateException("Order cannot be rejected");
        }

        return List.of();
    }

    public List<Notification> markAsNotAnswered() {
        boolean successfulStateChange = this.getOrderState().markAsNotAnswered(this);

        if (!successfulStateChange) {
            throw new IllegalStateException("Order cannot be marked as not-answered");
        }

        return List.of();
    }

    public List<Notification> cancel() {
        boolean successfulStateChange = this.getOrderState().cancel(this);

        if (!successfulStateChange) {
            throw new IllegalStateException("Order cannot be cancelled");
        }

        return List.of();
    }
}
