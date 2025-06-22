package domain.shared.abstractions;

import domain.shared.entities.User;

import java.util.List;

public class NotificationToBeSent {
    private Notification notification;
    private List<User> users;
    private boolean sent;

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

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
