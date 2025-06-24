package application.useCases.orders.rejectOrder;

import domain.orders.entities.Order;
import domain.orders.states.RejectedOrderState;
import domain.shared.abstractions.CommandHandler;
import domain.shared.abstractions.Notification;
import infra.notifications.NotificationSender;

import java.util.List;

public class RejectOrderCommandHandler implements CommandHandler<RejectOrderCommand, Order> {
    @Override
    public Order handle(RejectOrderCommand command) {
        if (command.getOrder() == null) {
            throw new IllegalStateException("Order is required");
        }

        if (command.getOrder().getOrderState() == RejectedOrderState.getInstance()) {
            throw new IllegalStateException("Order already rejected");
        }

        List<Notification> notifications = command.getOrder().reject();

        for (var notification : notifications) {
            NotificationSender.getInstance().addNotification(notification);
        }

        return command.getOrder();
    }
}
