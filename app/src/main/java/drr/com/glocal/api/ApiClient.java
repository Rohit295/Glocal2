package drr.com.glocal.api;

import android.util.Log;

import com.drr.glocal.services.services.Services;
import com.drr.glocal.services.services.model.TrackInfo;
import com.drr.glocal.services.services.model.TrackLocationInfo;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by racastur on 01-11-2014.
 */
public class ApiClient {

    private static String TAG = ApiClient.class.getName();

    public static TrackInfo createNewTrack(Long userId, String name) {

        TrackInfo info = null;
        try {

            Services services =
                    new Services(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);

            info = services.createNewTrack(userId, name).execute();

        } catch (Exception e) {
            Log.e(TAG, "[" + e.getMessage() + "]", e);
            return null;
        }

        return info;

    }

    public List<TrackInfo> getTracks(Long userId) {

        List<TrackInfo> infos = null;
        try {

            Services services =
                    new Services(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);

            infos = services.getTracks(userId).execute().getItems();

        } catch (Exception e) {
            Log.e(TAG, "[" + e.getMessage() + "]", e);
            return new ArrayList<TrackInfo>();
        }

        return infos;

    }

    public static void saveLocation(Long userId, Long trackId,
                                    Long deviceId, Long timestamp,
                                    Double latitude, Double longitude) {

        try {

            Services services =
                    new Services(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);

            services.saveLocation(userId, trackId, deviceId, latitude, longitude, timestamp).execute();

        } catch (Exception e) {
            Log.e(TAG, "[" + e.getMessage() + "]", e);
        }

    }

    public static List<TrackLocationInfo> getTracks(Long userId, Long trackId) {

        List<TrackLocationInfo> infos = null;
        try {

            Services services =
                    new Services(AndroidHttp.newCompatibleTransport(), new AndroidJsonFactory(), null);

            infos = services.getTrackLocations(userId, trackId).execute().getItems();

        } catch (Exception e) {
            Log.e(TAG, "[" + e.getMessage() + "]", e);
            return new ArrayList<TrackLocationInfo>();
        }

        return infos;

    }

}
