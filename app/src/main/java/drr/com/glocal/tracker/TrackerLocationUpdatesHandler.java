package drr.com.glocal.tracker;

import android.location.Location;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.drr.glocal.services.services.model.TrackInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import drr.com.glocal.api.ApiClient;

/**
 * Created by rohitman on 11/5/2014.
 */
public class TrackerLocationUpdatesHandler extends Handler {
    public static final int CREATE_TRACKINFO = 609;
    public static final int SAVE_LOCATION_UPDATE = 158;
    public static final int CLOSE_TRACKINFO = 402;

    public static final String TRACK_NAME = "Track_Name";

    private Map<String, TrackInfo> mMapTrackToInfo =  new HashMap<String, TrackInfo>();

    // Make the Constructor Private so that it wont be invoked and instead use the Singleton
    // retrieve
    // TODO verify that this is truly singleton across all the using Processes
    private static TrackerLocationUpdatesHandler mHandler = new TrackerLocationUpdatesHandler();
    private TrackerLocationUpdatesHandler() {
    }

    public static TrackerLocationUpdatesHandler getHandler() {
        return mHandler;
    }

    @Override
    public void handleMessage(Message msg) {
        Log.i(this.getClass().getName(), "About to Process a location update on the DB - " + whatIsBeingProcessed(msg.what));
        // Extract the Name of the Track. All callers are expected to pass that as a Bundle in the
        // message Object
        String trackName = "";
        if (msg.getData() != null) {
            trackName = msg.getData().getString(TRACK_NAME);
            if (trackName == null) {
                Log.e(this.getClass().getName(), "Track Name cannot be null");
                throw new RuntimeException("TrackName cannot be null");
            }
        }

        switch(msg.what) {

            case CREATE_TRACKINFO:
                new TrackerLocationCreateTrackInfoAsyncTask(this).execute(trackName);
                break;

            case SAVE_LOCATION_UPDATE:
                TrackInfo thisTrackInfo = mMapTrackToInfo.get(trackName);
                Location locationToUpdate = (Location)msg.obj;
                new TrackerLocationSaveLocationDataAsyncTask(thisTrackInfo);
                break;

            case CLOSE_TRACKINFO:
                mMapTrackToInfo.remove(trackName);
                break;
        }

    }

    private String whatIsBeingProcessed(int what) {
        switch (what) {
            case CREATE_TRACKINFO:
                return "CREATE_TRACKINFO";
            case SAVE_LOCATION_UPDATE:
                return "SAVE_LOCATION_UPDATE";
            case CLOSE_TRACKINFO:
                return "CLOSE_TRACKINFO";
            default:
                // should never come here. But if it does, ERROR
                throw new RuntimeException(this.getClass().getName() + ": cannot process this message. Value = " + what);
        }
    }

    public void updateTrackInfo(String trackName, TrackInfo trackInfo) {
        mMapTrackToInfo.put(trackName, trackInfo);
    }

    /**
     * Use this Static Method to create the Name of every Track. Name is 'Track_formatted date'
     * @return
     */
    public static String getNewTrackName() {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("EEEE, MMM dd, yyyy HH:mm:ss a");
        return "Track_" + dateFormatter.format(Calendar.getInstance().getTime());
    }

    class TrackerLocationCreateTrackInfoAsyncTask extends AsyncTask<String, Integer, TrackInfo> {
        private TrackerLocationUpdatesHandler mUpdatesHandler;

        public TrackerLocationCreateTrackInfoAsyncTask(TrackerLocationUpdatesHandler updatesHandler) {
            mUpdatesHandler = updatesHandler;
        }

        private String mTrackName;

        @Override
        protected TrackInfo doInBackground(String... trackNames) {
            mTrackName = trackNames[0];
            return ApiClient.createNewTrack(1L, mTrackName);
        }

        @Override
        protected void onPostExecute(TrackInfo trackInfo) {
            super.onPostExecute(trackInfo);

            mUpdatesHandler.updateTrackInfo(mTrackName, trackInfo);
        }
    }

    class TrackerLocationSaveLocationDataAsyncTask extends AsyncTask<Location, Integer, Boolean> {
        private TrackInfo mTrackInfo;

        public TrackerLocationSaveLocationDataAsyncTask(TrackInfo trackInfo) {
            mTrackInfo = trackInfo;
        }

        @Override
        protected Boolean doInBackground(Location... locations) {
            ApiClient.saveLocation(1L, mTrackInfo.getId(), 1L, 1L, locations[0].getLatitude(), locations[0].getLongitude());
            return null;
        }
    }
}
