package application.useCases.orders.cancelOrder;

import domain.orders.entities.Order;
import domain.shared.abstractions.Command;

public class CancelOrderCommand extends Command {
    private Order order;

    public CancelOrderCommand(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
