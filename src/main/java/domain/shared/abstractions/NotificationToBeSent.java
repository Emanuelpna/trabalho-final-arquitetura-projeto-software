package domain.shared.abstractions;

public class NotificationToBeSent {
    private Notification notification;
    private boolean sent;

    public NotificationToBeSent(Notification notification) {
        this.notification = notification;
    }

    public Notification getNotification() {
        return notification;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
