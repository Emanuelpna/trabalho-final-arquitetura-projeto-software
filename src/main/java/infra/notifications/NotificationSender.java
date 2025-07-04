package infra.notifications;

import domain.shared.abstractions.Notification;
import domain.shared.abstractions.NotificationToBeSent;
import domain.shared.abstractions.UserRole;
import domain.shared.entities.User;
import domain.shared.userRoles.CustomerUserRole;
import domain.shared.userRoles.DelivererUserRole;
import domain.shared.userRoles.StoreUserRole;

import java.util.ArrayList;
import java.util.List;

public class NotificationSender {
    private NotificationSender() {
        UserRole store = new StoreUserRole(null);
        UserRole deliverer = new DelivererUserRole(store);
        UserRole customer = new CustomerUserRole(deliverer);
        this.userRole = customer;
    }

    private static final NotificationSender instance = new NotificationSender();

    public static NotificationSender getInstance() {
        return instance;
    }

    private UserRole userRole;
    private List<NotificationToBeSent> notificationsToBeSent = new ArrayList<>();

    public List<NotificationToBeSent> getNotificationsToBeSent() {
        return notificationsToBeSent;
    }

    public void addNotification(Notification notificationToBeSent) {
        this.notificationsToBeSent.add(new NotificationToBeSent(notificationToBeSent));
    }

    public void dispatchNotifications(List<User> users) {
        for (NotificationToBeSent notification : notificationsToBeSent) {
            if (!notification.isSent()) {
                userRole.notify(users, notification.getNotification());
                notification.setSent(true);
            }
        }
    }
}
