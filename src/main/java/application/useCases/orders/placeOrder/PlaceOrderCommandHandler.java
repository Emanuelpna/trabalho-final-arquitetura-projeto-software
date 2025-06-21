package application.useCases.orders.placeOrder;

import domain.orders.entities.Order;
import domain.orders.states.PendingOrderState;
import domain.shared.abstractions.CommandHandler;
import infra.notifications.NotificationSender;

import java.util.List;

public class PlaceOrderCommandHandler implements CommandHandler<PlaceOrderCommand, Order> {
    @Override
    public Order handle(PlaceOrderCommand command) {
        if (command.getOrder() == null) {
            throw new IllegalStateException("Order is null");
        }

        if (command.getOrder().getOrderState() != PendingOrderState.getInstance()) {
            throw new IllegalStateException("Cannot place an order already placed");
        }

        command.getOrder().processOrder();

        var notifications = command.getOrder().placeOrder(command.getCustomer(), command.getStore());

        for (var notification : notifications) {
            NotificationSender.getInstance().addNotification(notification, List.of(command.getCustomer(), command.getStore()));
        }

        return command.getOrder();
    }

    @Override
    public void cancel() {

    }
}
