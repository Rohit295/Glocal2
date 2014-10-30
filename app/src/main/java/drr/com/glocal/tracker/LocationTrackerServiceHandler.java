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
    public final int STOP_PROCESSING = 1407;

    private Service mLocationTrackingService;

    public LocationTrackerServiceHandler(Service locationTrackingService, Looper looper) {
        super(looper);
        this.mLocationTrackingService = locationTrackingService;
    }

    @Override
    public void handleMessage(Message msg) {

        // create a LocationTracker and start getting the location updates
        LocationTracker mLocationTracker = new LocationTracker(mLocationTrackingService);

        // Allow the Location Tracker to get location updates till the stipulated end OR till the
        // service wants it stopped
        while(true) {
            synchronized (this) {
                try {
                    if (hasMessages(STOP_PROCESSING)) {
                        mLocationTracker = null; // which will cause the Location Tracking to stop
                        break;
                    }
                    wait(2000);
                } catch (InterruptedException ie) {
                    Log.e(this.getClass().getName(), ie.getMessage());
                    throw new RuntimeException(ie);
                }
            }
        }

        // time to stop the service.
        mLocationTrackingService.stopSelf(msg.arg1);
    }
}