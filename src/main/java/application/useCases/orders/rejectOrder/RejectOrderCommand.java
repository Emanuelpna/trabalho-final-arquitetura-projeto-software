package application.useCases.orders.rejectOrder;

import domain.orders.entities.Order;
import domain.shared.abstractions.Command;

public class RejectOrderCommand extends Command {
    private Order order;

    public RejectOrderCommand(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
