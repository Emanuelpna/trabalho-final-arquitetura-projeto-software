package application.useCases.orders.deliverOrder;

import domain.delivery.entities.Deliverer;
import domain.orders.entities.Order;
import domain.shared.abstractions.Command;

public class DeliverOrderCommand extends Command {
    private Order order;
    private Deliverer deliverer;

    public DeliverOrderCommand(Order order, Deliverer deliverer) {
        this.order = order;
        this.deliverer = deliverer;
    }

    public Order getOrder() {
        return order;
    }

    public Deliverer getDeliverer() {
        return deliverer;
    }
}
