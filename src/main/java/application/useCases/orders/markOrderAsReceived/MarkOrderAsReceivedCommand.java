package application.useCases.orders.markOrderAsReceived;

import domain.orders.entities.Order;
import domain.shared.abstractions.Command;

public class MarkOrderAsReceivedCommand extends Command {

    private Order order;

    public MarkOrderAsReceivedCommand(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
