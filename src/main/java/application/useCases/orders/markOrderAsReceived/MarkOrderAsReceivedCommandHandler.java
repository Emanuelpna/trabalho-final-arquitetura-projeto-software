package application.useCases.orders.markOrderAsReceived;

import domain.orders.entities.Order;
import domain.orders.states.ReceivedOrderState;
import domain.shared.abstractions.CommandHandler;
import domain.shared.abstractions.Notification;
import infra.notifications.NotificationSender;

import java.util.List;

public class MarkOrderAsReceivedCommandHandler implements CommandHandler<MarkOrderAsReceivedCommand, Order> {
    @Override
    public Order handle(MarkOrderAsReceivedCommand command) {
        if (command.getOrder() == null) {
            throw new IllegalStateException("Order is required");
        }

        if (command.getOrder().getOrderState() == ReceivedOrderState.getInstance()) {
            throw new IllegalStateException("Order already received");
        }

        List<Notification> notifications = command.getOrder().markAsReceived();

        for (var notification : notifications) {
            NotificationSender.getInstance().addNotification(notification);
        }

        return command.getOrder();
    }
}
