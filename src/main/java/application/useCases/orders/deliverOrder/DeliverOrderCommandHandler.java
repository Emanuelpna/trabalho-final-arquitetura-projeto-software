package application.useCases.orders.deliverOrder;

import domain.orders.entities.Order;
import domain.orders.states.DeliveringOrderState;
import domain.shared.abstractions.CommandHandler;
import domain.shared.abstractions.Notification;
import infra.notifications.NotificationSender;

import java.util.List;

public class DeliverOrderCommandHandler implements CommandHandler<DeliverOrderCommand, Order> {
    @Override
    public Order handle(DeliverOrderCommand command) {
        if (command.getOrder() == null) {
            throw new IllegalStateException("Order is required");
        }

        if (command.getDeliverer() == null) {
            throw new IllegalStateException("Deliverer is required");
        }

        if (command.getOrder().getOrderState() == DeliveringOrderState.getInstance()) {
            throw new IllegalStateException("Order already being delivered");
        }

        List<Notification> notifications = command.getOrder().deliver(command.getDeliverer());

        for (var notification : notifications) {
            NotificationSender.getInstance().addNotification(notification);
        }

        return command.getOrder();
    }
}
