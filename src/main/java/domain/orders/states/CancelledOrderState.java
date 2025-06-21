package domain.orders.states;

public class CancelledOrderState extends OrderState {
    private CancelledOrderState() {
    }

    private static CancelledOrderState instance = new CancelledOrderState();

    public static CancelledOrderState getInstance() {
        return instance;
    }

    @Override
    public String getStateName() {
        return "Cancelled";
    }

}
