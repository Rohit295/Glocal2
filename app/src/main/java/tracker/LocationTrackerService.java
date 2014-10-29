package tracker;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.*;
import android.os.Process;

/**
 *
 */
public class LocationTrackerService extends Service {

    private Looper mServiceLooper;
    private LocationTrackerServiceHandler mServiceHandler;

    public LocationTrackerService() {
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();

        // Start up the thread running the service.  Note that we create a separate thread because
        // the service normally runs in the process's main thread, which we don't want to block.
        // We also make it background priority so CPU-intensive work will not disrupt our UI.
        HandlerThread thread = new HandlerThread("ServiceStartArguments",
                Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        // Get the HandlerThread's Looper and use it for our Handler
        mServiceLooper = thread.getLooper();
        mServiceHandler = new LocationTrackerServiceHandler(this, mServiceLooper);    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}


