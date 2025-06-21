package domain.shared.abstractions;

import domain.shared.entities.User;

import java.util.List;

public class NotificationToBeSent {
    private Notification notification;
    private List<User> users;

    public NotificationToBeSent(Notification notification, List<User> users) {
        this.notification = notification;
        this.users = users;
    }

    public Notification getNotification() {
        return notification;
    }

    public List<User> getUsers() {
        return users;
    }
}
