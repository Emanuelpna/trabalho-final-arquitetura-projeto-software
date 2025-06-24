package application.useCases.orders.startOrderPreparation;

import domain.orders.entities.Order;
import domain.shared.abstractions.Command;

public class StartOrderPreparationCommand extends Command {
    Order order;

    public StartOrderPreparationCommand(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}
