package application.useCases.orders.deliverOrder;

import domain.orders.entities.Order;
import domain.orders.states.PreparingOrderState;
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

        if (command.getCustomer() == null) {
            throw new IllegalStateException("Customer is required");
        }

        if (command.getStore() == null) {
            throw new IllegalStateException("Store is required");
        }

        if (command.getDeliverer() == null) {
            throw new IllegalStateException("Deliverer is required");
        }

        if (command.getOrder().getOrderState() == PreparingOrderState.getInstance()) {
            throw new IllegalStateException("Order already in preparation");
        }

        List<Notification> notifications = command.getOrder().deliver(command.getDeliverer());

        for (var notification : notifications) {
            NotificationSender.getInstance().addNotification(notification, List.of(command.getCustomer(), command.getStore(), command.getDeliverer()));
        }

        return command.getOrder();
    }

    @Override
    public void cancel() {

    }
}
