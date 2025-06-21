package domain.orders.states;

import domain.orders.entities.Order;
public class PendingOrderState extends OrderState {
    private PendingOrderState() {
    }

    private static PendingOrderState instance = new PendingOrderState();

    public static PendingOrderState getInstance() {
        return instance;
    }

    @Override
    public String getStateName() {
        return "Pending";
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
