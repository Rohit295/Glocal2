package drr.com.glocal.notification;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import drr.com.glocal.model.DeviceInfo;

/**
 * Created by racastur on 22-08-2014.
 */
public class GCMRegistrationHelper {

    private static final String TAG = GCMRegistrationHelper.class.getName();

    private static final String API_BASE_URL = "http://glocal-services.appspot.com:8080/_ah/api/";
    private static final String GCM_REGISTRATION_URL = API_BASE_URL + "services/v1/register?userId=%d&deviceId=%d&gcmRegistrationId=%s";
    private static final String SENDER_ID = "542015697225";

    public static void doGCMRegistration(Context context, Long userId) {

        // Check device for Play Services APK. If check succeeds, proceed with GCM registration.
        if (checkPlayServices(context)) {
            registerInBackground(context, userId);
        } else {
            Log.e(TAG, "No valid Google Play Services APK found.");
        }

    }

    private static boolean checkPlayServices(Context context) {
        if (GooglePlayServicesUtil.isGooglePlayServicesAvailable(context) != ConnectionResult.SUCCESS) {
            Log.e(TAG, "This device is not supported.");
            return false;
        }
        return true;
    }

    private static void registerInBackground(final Context context, final Long userId) {

        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {

                try {

                    // TODO get deviceId from server response
                    Long deviceId = 1L;
                    String registrationId;

                    DeviceInfo info = DeviceInfo.loadData(context);
                    if (info == null) {
                        registrationId = GoogleCloudMessaging.getInstance(context).register(SENDER_ID);
                        Log.d(TAG, "GCM Registration done [" + registrationId + "]");
                    } else {
                        registrationId = info.getGcmRegistrationId();
                    }

                    HttpClient client = new DefaultHttpClient();

                    String url = String.format(GCM_REGISTRATION_URL, userId, deviceId, registrationId);

                    HttpUriRequest request = new HttpPost(url);

                    HttpResponse response = client.execute(request);
                    if (response.getStatusLine().getStatusCode() != 200 && response.getStatusLine().getStatusCode() != 204) {
                        Log.e(TAG, "Status [" + response.getStatusLine().getStatusCode() + "] " +
                                "Reason [" + response.getStatusLine().getReasonPhrase() + "]");
                    }

                    info = new DeviceInfo();
                    info.setDeviceId(deviceId);
                    info.setGcmRegistrationId(registrationId);

                    DeviceInfo.saveData(context, info);

                    Log.d(TAG, "GCM Registration sent to server [" + registrationId + "]");

                    return registrationId;

                } catch (Exception ex) {
                    Log.e(TAG, "Failed to register with GCM/server [" + ex.getMessage() + "]", ex);
                    return null;
                }

            }

        }.execute(null, null, null);

    }

}