package application.useCases.orders.markOrderAsArrived;

import domain.orders.entities.Order;
import domain.shared.abstractions.Command;

public class MarkOrderAsArrivedCommand extends Command {
    private Order order;


    public MarkOrderAsArrivedCommand(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
