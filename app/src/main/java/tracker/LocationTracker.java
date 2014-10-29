package tracker;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohitman on 10/29/2014.
 */
public class LocationTracker implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        GoogleMap.OnCameraChangeListener,
        com.google.android.gms.location.LocationListener {

    private Context locationTrackingContext;
    private LocationClient mLocationClient;
    private final float INITIAL_ZOOM_LEVEL = 15f;

    private List<LatLng> routeToTraverse;

    private boolean locationConnectionInitializationDone = false;

    public LocationTracker(Context locationTrackingContext) {
        Log.i(this.getClass().getName(), "LocationTracker Created");
        //TODO check that Google play services is available to the client

        mLocationClient = new LocationClient(locationTrackingContext, this, this);
        mLocationClient.connect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(this.getClass().getName(), "Connection Made");

        // Rest of this section is required only when Connection is Initialized for first time
        if (!locationConnectionInitializationDone) {
            locationConnectionInitializationDone = true;
        } else {
            return;
        }

        // Get the last location, which in this case will be the initial location from where reporting starts
        Location mLocation = mLocationClient.getLastLocation();
        Log.i(this.getClass().getName(), "Initial Position - Latitude: " + mLocation.getLatitude() + "; Longitude: " +
                mLocation.getLongitude() + "; Altitude: " + mLocation.getAltitude());

        // Enable this tracker to keep getting location updates
        // TODO: currently hard coded to high accuracy. How to handle coarse location updates?
        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(1000);
        mLocationClient.requestLocationUpdates(request, this);
    }

    @Override
    public void onDisconnected() {
        Log.i(this.getClass().getName(), "Connection Made");
    }

    private List<LatLng> pointsOnPathTaken = new ArrayList<LatLng>(5);

    @Override
    public void onLocationChanged(Location location) {
        Log.i(this.getClass().getName(), "LocationUpdate - Latitude: " + location.getLatitude() + "; Longitude: " +
                location.getLongitude() + "; Altitude: " + location.getAltitude());

        // TODO- test code to simulate movement. check that this is commented out
        //LatLng locationToAdd = new LatLng(location.getLatitude() + (double)(5*tempCounter)/10000,
        //                            location.getLongitude() + (double)(5*tempCounter++)/10000);
        LatLng locationToAdd = new LatLng(location.getLatitude(), location.getLongitude());
        pointsOnPathTaken.add(locationToAdd);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
