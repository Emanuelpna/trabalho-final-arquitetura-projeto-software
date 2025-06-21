package domain.shared.abstractions;

import java.util.Observable;

public class Notification extends Observable {
    public NotificationType notificationType;

    public Notification(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public NotificationType getNotification() {
        return notificationType;
    }

    public void setNotification(NotificationType notificationType) {
        this.notificationType = notificationType;
    }

    public void sendNotificaion() {
        String notification = this.notificationType.getNotificationContent();

        setChanged();
        notifyObservers(notification);
    }
}
