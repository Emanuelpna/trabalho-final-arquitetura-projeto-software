package application.useCases.orders.markOrderAsNotAnswered;

import domain.orders.entities.Order;
import domain.orders.states.NoAnswerOrderState;
import domain.shared.abstractions.CommandHandler;
import domain.shared.abstractions.Notification;
import infra.notifications.NotificationSender;

import java.util.List;

public class MarkOrderAsNotAnsweredCommandHandler implements CommandHandler<MarkOrderAsNotAnsweredCommand, Order> {
    @Override
    public Order handle(MarkOrderAsNotAnsweredCommand command) {
        if (command.getOrder() == null) {
            throw new IllegalStateException("Order is required");
        }

        if (command.getOrder().getOrderState() == NoAnswerOrderState.getInstance()) {
            throw new IllegalStateException("Order already has been marked as not-answered");
        }

        List<Notification> notifications = command.getOrder().markAsNotAnswered();

        for (var notification : notifications) {
            NotificationSender.getInstance().addNotification(notification);
        }

        return command.getOrder();
    }
}
