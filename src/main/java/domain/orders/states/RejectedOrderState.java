package domain.orders.states;

import domain.orders.entities.Order;

public class RejectedOrderState extends OrderState {
    private RejectedOrderState() {
    }

    private static RejectedOrderState instance = new RejectedOrderState();

    public static RejectedOrderState getInstance() {
        return instance;
    }

    @Override
    public String getStateName() {
        return "Rejected";
    }

    @Override
    public boolean startOrderPreparation(Order order) {
        order.setOrderState(PreparingOrderState.getInstance());
        return true;
    }

    @Override
    public boolean cancel(Order order) {
        order.setOrderState(CancelledOrderState.getInstance());
        return true;
    }
}
