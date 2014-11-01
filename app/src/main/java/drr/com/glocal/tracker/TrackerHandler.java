package drr.com.glocal.tracker;

import android.location.Location;
import android.os.Handler;
import android.os.Message;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by rohitman on 10/31/2014.
 */
public class TrackerHandler extends Handler {
    private GoogleMap mMap;
    Marker headOfThePath;

    private int tempCounter = 0;

    public TrackerHandler(GoogleMap map) {
        mMap = map;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case LocationTracker.LOCATION_UPDATE:
                Location currentPosition = (Location)msg.obj;
                LatLng currentPositionLatLng =
                        new LatLng(currentPosition.getLatitude(), currentPosition.getLongitude());

                // TODO - ensure this code below is commented
                // Test code to simulate small amounts of movement
                currentPositionLatLng = new LatLng(currentPosition.getLatitude() + (double)(5*tempCounter)/10000,
                                            currentPosition.getLongitude() + (double)(5*tempCounter++)/10000);

                if (headOfThePath != null) {
                    headOfThePath.remove();
                }
                headOfThePath  = mMap.addMarker(new MarkerOptions().title("Start").
                        position(currentPositionLatLng));

                //pointsOnPathTaken.add(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));

                break;
        }
    }
}