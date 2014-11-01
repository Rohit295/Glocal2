package drr.com.glocal.tracker;

import android.app.Service;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by rohitman on 10/29/2014.
 */
public class LocationTrackerServiceHandler extends Handler {
    public static final int START_TRACKING_LOCATION = 0707;
    public static final int STOP_TRACKING_LOCATION = 1407;

    private Service mLocationTrackingService;
    private LocationTracker mLocationTracker;

    public LocationTrackerServiceHandler(Service locationTrackingService) {
        super();
        this.mLocationTrackingService = locationTrackingService;
    }

    @Override
    public void handleMessage(Message msg) {

        // Either Create a new LocationTracker and start tracking or simply destroy it
        switch(msg.what) {
            case START_TRACKING_LOCATION:
                if (mLocationTracker == null)
                    mLocationTracker = new LocationTracker(mLocationTrackingService, msg.replyTo);
                mLocationTracker.startTrackingLocation();
                break;

            case STOP_TRACKING_LOCATION:
                if (mLocationTracker != null) {
                    mLocationTracker.stopTrackingLocation();
                    mLocationTracker = null;
                }
                break;
        }
    }
}