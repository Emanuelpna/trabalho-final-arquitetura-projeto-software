package domain.orders.states;

public class NoAnswerOrderState extends OrderState {
    private NoAnswerOrderState() {
    }

    private static NoAnswerOrderState instance = new NoAnswerOrderState();

    public static NoAnswerOrderState getInstance() {
        return instance;
    }

    @Override
    public String getStateName() {
        return "NoAnswer";
    }

}
