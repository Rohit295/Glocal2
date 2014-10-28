package drr.com.glocal;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;


public class Tracker extends Activity implements
        GooglePlayServicesClient.ConnectionCallbacks,
        GooglePlayServicesClient.OnConnectionFailedListener,
        GoogleMap.OnCameraChangeListener,
        com.google.android.gms.location.LocationListener{

    private LocationClient mLocationClient;
    private MapFragment mapFragment;

    private final float INITIAL_ZOOM_LEVEL = 15f;

    private List<LatLng> routeToTraverse;

    /**
     * On Create, initalize the Map Fragment and position to a default
     * TODO replace this with a MapView so that controls can be placed on top of the map
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        if (savedInstanceState == null) {
            // Add a MapFragment to show the Map
            GoogleMapOptions mapOptions = new GoogleMapOptions();
            CameraPosition cp = CameraPosition.builder().target(new LatLng(17.4321496, 78.3612867)).
                    zoom(INITIAL_ZOOM_LEVEL).build();
            mapOptions.camera(cp).mapType(GoogleMap.MAP_TYPE_NORMAL);

            mapFragment = MapFragment.newInstance(mapOptions);
            getFragmentManager().beginTransaction()
                    .add(R.id.container, mapFragment)
                    .commit();
        }

        //routeToTraverse = new DirectionsManager().execute(new LatLng(17.4324617, 78.361380), new LatLng(17.24000, 78.4281));
    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(this.getClass().getName(), "onStart called");
        //TODO check that Google play services is available to the client

        mLocationClient = new LocationClient(this, this, this);
        mLocationClient.connect();
    }

    @Override
    protected void onStop() {
        Log.i(this.getClass().getName(), "OnStop called");

        if (mLocationClient.isConnected()) {
            mLocationClient.removeLocationUpdates(this);
        }
        mLocationClient.disconnect();
        super.onStop();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.tracker, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean locationConnectionInitializationDone = false;

    @Override
    public void onConnected(Bundle bundle) {
        Toast.makeText(this, "Connection Made", Toast.LENGTH_SHORT).show();

        // Rest of this section is required only when Connection is Initialized for first time
        if (!locationConnectionInitializationDone) {
            locationConnectionInitializationDone = true;
        } else {
            return;
        }

        Location mLocation = mLocationClient.getLastLocation();
        System.out.println("Latitude: " + mLocation.getLatitude() + " ; Longitude: " +
                mLocation.getLongitude() + "; Altitude: " + mLocation.getAltitude());
        Log.i(this.getClass().getName(), "LocationUpdate - Latitude: " + mLocation.getLatitude() + "; Longitude: " +
                mLocation.getLongitude() + "; Altitude: " + mLocation.getAltitude());

        // TODO If the MapFragment is added, then add the initial marker. Check later to see how to
        // overcome the .isAdded not returning true
        if (mapFragment.isAdded()) {
            // Use this to update the Map
            GoogleMap map = mapFragment.getMap();
            map.setOnCameraChangeListener(this);

            map.setMyLocationEnabled(true);
            anchorOfThePath = map.addMarker(new MarkerOptions().title("Start").position(new LatLng(mLocation.getLatitude(),
                    mLocation.getLongitude())));

            pointsOnPathTaken.add(new LatLng(mLocation.getLatitude(), mLocation.getLongitude()));
        }

        LocationRequest request = new LocationRequest();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setInterval(5000);
        request.setFastestInterval(1000);
        mLocationClient.requestLocationUpdates(request, this);
    }

    @Override
    public void onDisconnected() {
        Toast.makeText(this, "Connection Disconnected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //TODO implement this method for checking connection failure
    }

    private List<LatLng> pointsOnPathTaken = new ArrayList<LatLng>(5);
    private int tempCounter = 0;
    Polyline pathTraced;

    Marker anchorOfThePath, headOfThePath;

    /**
     * Everytime Location changes, auto update it on the map
     * @param location
     */
    @Override
    public void onLocationChanged(Location location) {
        Log.i(this.getClass().getName(), "LocationUpdate - Latitude: " + location.getLatitude() + "; Longitude: " +
                location.getLongitude() + "; Altitude: " + location.getAltitude());

        // Use this to update the Map
        GoogleMap map = mapFragment.getMap();


        // TODO - comment this section. test code to simulate movement
        //LatLng locationToAdd = new LatLng(location.getLatitude() + (double)(5*tempCounter)/10000,
        //                            location.getLongitude() + (double)(5*tempCounter++)/10000);
        LatLng locationToAdd = new LatLng(location.getLatitude(), location.getLongitude());
        pointsOnPathTaken.add(locationToAdd);
        if (pathTraced == null)
            pathTraced = map.addPolyline(new PolylineOptions().add(pointsOnPathTaken.get(0)));
        pathTraced.setPoints(pointsOnPathTaken);

        // also set a marker at the head of the path. Remove the earlier head and add another
        if (headOfThePath != null) {
            headOfThePath.remove();
        }
        headOfThePath = map.addMarker(new MarkerOptions().position(locationToAdd).flat(true));


        Log.i(this.getClass().getName(), "Above LatLng added as: " + locationToAdd + "; mod: " + (double)(5*tempCounter)/10000);
        //
    }



    private float currentZoomLevel = INITIAL_ZOOM_LEVEL;

    /**
     * Monitor changes to the camera such as the zoom level etc. Use this to change the path lines, markets etc
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        // check to see if Zoom level has changed
        if (currentZoomLevel != cameraPosition.zoom) {
            pathTraced.setWidth(10);
            currentZoomLevel = cameraPosition.zoom;
        }

    }
}