package application.useCases.orders.startOrderPreparation;

import domain.orders.entities.Order;
import domain.orders.states.PreparingOrderState;
import domain.shared.abstractions.CommandHandler;
import domain.shared.abstractions.Notification;
import infra.notifications.NotificationSender;

import java.util.List;

public class StartOrderPreparationCommandHandler implements CommandHandler<StartOrderPreparationCommand, Order> {
    @Override
    public Order handle(StartOrderPreparationCommand command) {
        if (command.getOrder() == null) {
            throw new IllegalStateException("Order is required");
        }

        if (command.getCustomer() == null) {
            throw new IllegalStateException("Customer is required");
        }

        if (command.getStore() == null) {
            throw new IllegalStateException("Store is required");
        }

        if (command.getOrder().getOrderState() == PreparingOrderState.getInstance()) {
            throw new IllegalStateException("Order already in preparation");
        }

        List<Notification> notifications = command.getOrder().startOrderPreparation();

        for (var notification : notifications) {
            NotificationSender.getInstance().addNotification(notification, List.of(command.getCustomer(), command.getStore()));
        }

        return command.getOrder();
    }

    @Override
    public void cancel() {

    }
}
