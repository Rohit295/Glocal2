package drr.com.glocal.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;

import java.io.Serializable;

/**
 * Created by racastur on 17-09-2014.
 */
public class DeviceInfo implements Serializable {

    private static final String PROPERTY_DEVICE_ID = "deviceId";
    private static final String PROPERTY_REG_ID = "registrationId";
    private static final String PROPERTY_APP_VERSION = "appVersion";

    private Long deviceId;

    private String gcmRegistrationId;

    public DeviceInfo() {

    }

    public Long getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Long deviceId) {
        this.deviceId = deviceId;
    }

    public String getGcmRegistrationId() {
        return gcmRegistrationId;
    }

    public void setGcmRegistrationId(String gcmRegistrationId) {
        this.gcmRegistrationId = gcmRegistrationId;
    }

    public static DeviceInfo loadData(Context context) {

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        Long deviceId = prefs.getLong(PROPERTY_DEVICE_ID, -1);
        if (registrationId.isEmpty()) {
            return null;
        }

        // Check if app was updated; if so, it must clear the registration ID
        // since the existing regID is not guaranteed to work with the new
        // app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            return null;
        }

        DeviceInfo info = new DeviceInfo();
        info.setGcmRegistrationId(registrationId);
        info.setDeviceId(deviceId);

        return info;

    }

    public static void saveData(Context context, DeviceInfo info) {

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        int appVersion = getAppVersion(context);

        SharedPreferences.Editor editor = prefs.edit();

        editor.putLong(PROPERTY_DEVICE_ID, info.getDeviceId());
        editor.putString(PROPERTY_REG_ID, info.getGcmRegistrationId());
        editor.putInt(PROPERTY_APP_VERSION, appVersion);

        editor.apply();

    }

    private static int getAppVersion(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            throw new RuntimeException(e);
        }
    }

}
