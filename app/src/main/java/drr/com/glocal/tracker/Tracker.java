package drr.com.glocal.tracker;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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

        public TrackerFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tracker2, container, false);

            // Add a Button listener to start location tracking
            Button startButton = (Button) rootView.findViewById(R.id.btn_start_tracking);
            startButton.setOnClickListener(this);

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
