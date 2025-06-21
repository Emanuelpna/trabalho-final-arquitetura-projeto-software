package domain.orders.states;

import domain.orders.entities.Order;

public class DeliveringOrderState extends OrderState {
    private DeliveringOrderState() {
    }

    private static DeliveringOrderState instance = new DeliveringOrderState();

    public static DeliveringOrderState getInstance() {
        return instance;
    }

    @Override
    public String getStateName() {
        return "Delivering";
    }

    @Override
    public boolean markAsArrived(Order order) {
        order.setOrderState(ArrivedOrderState.getInstance());
        return true;
    }

    @Override
    public boolean cancel(Order order) {
        order.setOrderState(CancelledOrderState.getInstance());
        return true;
    }
}
