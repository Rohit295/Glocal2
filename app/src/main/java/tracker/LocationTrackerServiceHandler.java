package tracker;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * Created by rohitman on 10/29/2014.
 */
public class LocationTrackerServiceHandler extends Handler {
    public final int STOP_PROCESSING = 1407;

    private Context mLocationTrackingContext;

    public LocationTrackerServiceHandler(Context locationTrackingContext, Looper looper) {
        super(looper);
        this.mLocationTrackingContext = locationTrackingContext;
    }

    @Override
    public void handleMessage(Message msg) {

        // create a LocationTracker and start getting the location updates
        LocationTracker mLocationTracker = new LocationTracker(mLocationTrackingContext);

        // Allow the Location Tracker to get location updates till the stipulated end OR till the
        // service wants it stopped
        while(true) {
            synchronized (this) {
                try {
                    if (hasMessages(STOP_PROCESSING)) {
                        mLocationTracker = null;
                        break;
                    }
                    wait(5000);
                } catch (InterruptedException ie) {
                    Log.e(this.getClass().getName(), ie.getMessage());
                    throw new RuntimeException(ie);
                }
            }
        }

        // time to stop the service.
    }
}
