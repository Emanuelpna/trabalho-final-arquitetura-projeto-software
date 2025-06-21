package domain.shared.abstractions;

import domain.shared.entities.User;

import java.util.ArrayList;
import java.util.List;

public abstract class UserRole {
    private UserRoleType userRoleType;
    protected List<NotificationType> notifications = new ArrayList<>();

    private UserRole superiorUserRole;

    public UserRole(UserRoleType userRoleType) {
        this.userRoleType = userRoleType;
    }

    public List<NotificationType> getNotifications() {
        return notifications;
    }

    public UserRole getSuperiorUserRole() {
        return superiorUserRole;
    }

    public void setSuperiorUserRole(UserRole superiorUserRole) {
        this.superiorUserRole = superiorUserRole;
    }

    public abstract String getRole();

    private void addUserObservers(List<User> users, Notification notification) {
        for (User user : users) {
            if (user.getUserRole().equals(this.userRoleType)) {
                // remove first so same user will not receive a notification repeated
                notification.deleteObserver(user);
                notification.addObserver(user);
            }
        }
    }

    public String notify(List<User> users, Notification notification) {
        if (notifications.contains(notification.getNotification())) {
            this.addUserObservers(users, notification);

            notification.sendNotificaion();

            return this.getRole();
        }


        if (superiorUserRole != null) {
            return superiorUserRole.notify(users, notification);
        } else {
            return "Notifiable User Role not found";
        }

    }
}