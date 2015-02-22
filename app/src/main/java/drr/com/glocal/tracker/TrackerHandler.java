package drr.com.glocal.tracker;

import android.location.Location;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohitman on 10/31/2014.
 */
public class TrackerHandler extends Handler {
    private GoogleMap mMap;
    Marker mTailOfThePath, mHeadOfThePath;

    private List<LatLng> pointsOnPathTaken = new ArrayList<LatLng>(5);
    private int tempCounter = 0;
    Polyline mPathTraced;

    public TrackerHandler(GoogleMap map) {
        mMap = map;
    }

    @Override
    public void handleMessage(Message msg) {
        Location currentPosition;
        LatLng currentPositionLatLng;

        switch (msg.what) {
            case LocationTracker.LOCATION_INITIAL_POSITION:
                currentPosition = (Location)msg.obj;
                currentPositionLatLng =
                        new LatLng(currentPosition.getLatitude(), currentPosition.getLongitude());

                // Identify the current position, at the start of tracking, which would be the last
                // position on the trail. Make a marker and add to the path polyline for later plotting
                mTailOfThePath = mMap.addMarker(new MarkerOptions().title("Start").
                        position(currentPositionLatLng));
                pointsOnPathTaken.add(currentPositionLatLng);

                break;
            case LocationTracker.LOCATION_UPDATE:
                currentPosition = (Location)msg.obj;
                currentPositionLatLng =
                        new LatLng(currentPosition.getLatitude(), currentPosition.getLongitude());

                // TODO - ensure this code below is commented
                // Test code to simulate small amounts of movement
                //currentPositionLatLng = new LatLng(currentPosition.getLatitude() + (double)(5*tempCounter)/10000,
                //                            currentPosition.getLongitude() + (double)(5*tempCounter++)/10000);

                //Now trace the path taken by the user. Draw the Polyline and set the Head Tracker
                if (mHeadOfThePath != null) {
                    mHeadOfThePath.remove();
                }
                mHeadOfThePath = mMap.addMarker(new MarkerOptions().title("Last").
                        position(currentPositionLatLng));

                pointsOnPathTaken.add(currentPositionLatLng);
                if (mPathTraced == null)
                    mPathTraced = mMap.addPolyline(new PolylineOptions().add(pointsOnPathTaken.get(0)));
                mPathTraced.setPoints(pointsOnPathTaken);
                //pointsOnPathTaken.add(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));

                break;
            case LocationTracker.LOCATION_FINAL_POSITION:
                // Delete the Polyline and reset the list of lat longs
                if (mPathTraced != null) {
                    mPathTraced.remove();
                    mPathTraced = null;
                }
                pointsOnPathTaken = new ArrayList<LatLng>(5);

                if (mHeadOfThePath != null)
                    mHeadOfThePath.remove();
                if (mTailOfThePath != null)
                    mTailOfThePath.remove();

                break;
        }
    }
}