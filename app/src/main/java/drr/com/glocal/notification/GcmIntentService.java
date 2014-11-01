package drr.com.glocal.notification;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.Random;

import drr.com.glocal.R;
import drr.com.glocal.Tracker;

/**
 * Created by racastur on 18-04-2014.
 */
public class GcmIntentService extends IntentService {

    private static final String TAG = GcmIntentService.class.getName();

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        Bundle extras = intent.getExtras();
        if (extras != null && !extras.isEmpty()) {

            NotificationPayload payload = new NotificationPayload();
            try {
                payload.setType(extras.getString("userId"));
                payload.setData(extras.getString("latitude") + extras.getString("longitude"));
            } catch (Exception e) {
                Log.e(getClass().getName(), "Error decoding data element in GCM", e);
            }

            // TODO Handle the notification here appropriately

            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(payload); // TODO how does the descriptor know this is an error type
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(payload); // TODO how does the descriptor know that this is a messages deleted on server issue
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                sendNotification(payload);
            }
        }

        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }

    private void sendNotification(NotificationPayload payload) {

        Log.d(TAG, String.format("Processing notification [%s]", payload.getType()));

        NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, Tracker.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setContentTitle(payload.getType())
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentText(payload.getData())
                        .setContentInfo("Glocal");
        mBuilder.setContentIntent(contentIntent);

        mNotificationManager.notify(new Random().nextInt(), mBuilder.build());

    }

}
