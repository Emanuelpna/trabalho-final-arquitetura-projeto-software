package application.useCases.orders.markOrderAsNotAnswered;

import domain.orders.entities.Order;
import domain.shared.abstractions.Command;

public class MarkOrderAsNotAnsweredCommand extends Command {
    private Order order;

    public MarkOrderAsNotAnsweredCommand(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
