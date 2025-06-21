package domain.shared.entities;

import domain.shared.abstractions.Notification;
import domain.shared.abstractions.UserRoleType;

import java.util.*;

public abstract class User extends Entity implements Observer {
    private String name;
    private UserRoleType userRole;

    private String lastNotification;
    private List<Notification> notifications = new ArrayList<>();

    public User(UUID id, String name, UserRoleType userRole) {
        super(id);
        this.name = name;
        this.userRole = userRole;
    }

    public User(String name, UserRoleType userRole) {
        super(UUID.randomUUID());
        this.name = name;
        this.userRole = userRole;
    }

    public String getName() {
        return name;
    }

    public String getLastNotification() {
        return lastNotification;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public UserRoleType getUserRole() {
        return userRole;
    }

    public void update(Observable o, Object arg) {
        lastNotification = (String) arg;

        notifications.add((Notification) o);
    }
}
