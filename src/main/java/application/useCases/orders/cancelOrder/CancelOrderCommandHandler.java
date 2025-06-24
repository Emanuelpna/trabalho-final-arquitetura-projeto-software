package application.useCases.orders.cancelOrder;

import domain.orders.entities.Order;
import domain.orders.states.CancelledOrderState;
import domain.shared.abstractions.CommandHandler;
import domain.shared.abstractions.Notification;
import infra.notifications.NotificationSender;

import java.util.List;

public class CancelOrderCommandHandler implements CommandHandler<CancelOrderCommand, Order> {
    @Override
    public Order handle(CancelOrderCommand command) {
        if (command.getOrder() == null) {
            throw new IllegalStateException("Order is required");
        }

        if (command.getOrder().getOrderState() == CancelledOrderState.getInstance()) {
            throw new IllegalStateException("Order already cancelled");
        }

        List<Notification> notifications = command.getOrder().cancel();

        for (var notification : notifications) {
            NotificationSender.getInstance().addNotification(notification);
        }

        return command.getOrder();
    }
}
