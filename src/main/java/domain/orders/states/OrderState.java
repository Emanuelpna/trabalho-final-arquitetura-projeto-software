package domain.orders.states;

import domain.orders.entities.Order;

public abstract class OrderState {
    public abstract String getStateName();

    public boolean startOrderPreparation(Order order) {
        return false;
    }

    public boolean deliver(Order order) {
        return false;
    }

    public boolean markAsArrived(Order order) {
        return false;
    }

    public boolean markAsReceived(Order order) {
        return false;
    }

    public boolean reject(Order order) {
        return false;
    }

    public boolean markAsNotAnswered(Order order) {
        return false;
    }

    public boolean cancel(Order order) {
        return false;
    }

}