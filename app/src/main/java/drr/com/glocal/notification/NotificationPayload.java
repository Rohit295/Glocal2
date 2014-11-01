package drr.com.glocal.notification;

/**
 * Created by racastur on 22-08-2014.
 */
public class NotificationPayload {

    private String type;

    private String data;

    public NotificationPayload() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
