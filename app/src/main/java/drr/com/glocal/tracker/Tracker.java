package drr.com.glocal.tracker;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import drr.com.glocal.R;

public class Tracker extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker2);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new TrackerFragment())
                    .commit();
        }
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class TrackerFragment extends Fragment implements View.OnClickListener {

        private final float INITIAL_ZOOM_LEVEL = 15f;
        private MapView mMapView;

        public TrackerFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tracker2, container, false);

            // Initialize our lovely Google Map View & give it its initial settings. Logic is to
            // initialize a MapFragment and add it to our container layout
            GoogleMapOptions mapOptions = new GoogleMapOptions();
            CameraPosition cp = CameraPosition.builder().target(new LatLng(17.4321496, 78.3612867)).
                    zoom(INITIAL_ZOOM_LEVEL).build();
            mapOptions.camera(cp).mapType(GoogleMap.MAP_TYPE_NORMAL);
            MapFragment mapFragment = MapFragment.newInstance(mapOptions);

            LinearLayout mapViewContainer = (LinearLayout)rootView.findViewById(R.id.f_cont_map_fragment);
            getFragmentManager().beginTransaction().add(mapViewContainer.getId(), mapFragment).commit();

            // Add a Button listener to start & stop location tracking
            Button startButton = (Button) rootView.findViewById(R.id.btn_start_tracking);
            startButton.setOnClickListener(this);
            Button stopButton = (Button) rootView.findViewById(R.id.btn_stop_tracking);
            stopButton.setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View view) {
            // If the user has pressed the Start Button
            Intent locationTrackingIntent = new Intent(getActivity(), LocationTrackerService.class);
            if (view.getId() == R.id.btn_start_tracking) {
                getActivity().startService(locationTrackingIntent);
            } else if (view.getId() == R.id.btn_stop_tracking) {
                getActivity().stopService(locationTrackingIntent);
            }
        }
    }
}
