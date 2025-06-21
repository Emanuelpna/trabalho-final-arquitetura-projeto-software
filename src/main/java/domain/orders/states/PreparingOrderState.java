package domain.orders.states;

import domain.orders.entities.Order;

public class PreparingOrderState extends OrderState {
    private PreparingOrderState() {
    }

    private static PreparingOrderState instance = new PreparingOrderState();

    public static PreparingOrderState getInstance() {
        return instance;
    }

    @Override
    public String getStateName() {
        return "Preparing";
    }

    @Override
    public boolean deliver(Order order) {
        order.setOrderState(DeliveringOrderState.getInstance());
        return true;
    }

    @Override
    public boolean cancel(Order order) {
        order.setOrderState(CancelledOrderState.getInstance());
        return true;
    }
}
