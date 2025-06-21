package application.mediators;

import domain.orders.entities.Order;
import domain.shared.abstractions.Command;
import domain.shared.abstractions.CommandHandler;
import domain.shared.abstractions.Mediator;
import infra.notifications.NotificationSender;

public class OrderMediator implements Mediator<Order> {
    @Override
    public <C extends Command, CH extends CommandHandler<C, Order>> Order send(C command, CH commandHandler) {
        Order result = commandHandler.handle(command);

        NotificationSender.getInstance().dispatchNotifications();

        return result;
    }
}
