package com.walkernation;

import org.magnum.soda.android.AndroidSoda;
import org.magnum.soda.android.AndroidSodaConnectionException;
import org.magnum.soda.android.AndroidSodaListener;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.walkernation.gpslocations.GPSComm;
import com.walkernation.gpslocations.GPSLocation;

public class HelloAndroidActivity extends Activity
// implements AndroidSodaListener
{

	private static final String LOG_TAG = HelloAndroidActivity.class
			.getCanonicalName();

	EditText myNameET;
	TextView myLatTV;
	TextView myLonTV;
	ListView listOfOthersLV;
	Button pushLocationButton;
	Button getLocationsButton;
	Locator locator;

	private AndroidSoda ASm;
	private AndroidSodaListener ASLm;

	private String Host = "localhost";
	private int Port = 10240;

	private GPSComm gpsComm;

	OnClickListener myOnClickListener = new OnClickListener() {

		public void onClick(View v) {
			if (v.getId() == R.id.push_location_button) {
				pushLocationButtonPressed();
			} else if (v.getId() == R.id.get_locations_button) {
				getLocationsButtonPressed();
			}
		}
	};

	/**
	 * Called when the activity is first created.
	 * 
	 * @param savedInstanceState
	 *            If the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle). <b>Note: Otherwise it
	 *            is null.</b>
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setupLogic();
		setupUI();
	}

	private void setupLogic() {
		// ASLm = this;
		AndroidSoda.init(this, Host, Port, ASLm);
		locator = new LocatorImpl();
	}

	private void setupUI() {
		setContentView(R.layout.activity_main);

		myNameET = (EditText) findViewById(R.id.edit_name_ET);
		myLatTV = (TextView) findViewById(R.id.my_lat_value_TV);
		myLonTV = (TextView) findViewById(R.id.my_long_value_TV);
		listOfOthersLV = (ListView) findViewById(R.id.list_of_others);

		pushLocationButton = (Button) findViewById(R.id.push_location_button);
		getLocationsButton = (Button) findViewById(R.id.get_locations_button);

		pushLocationButton.setOnClickListener(myOnClickListener);
		getLocationsButton.setOnClickListener(myOnClickListener);

		aa = new GPSLocationListViewAdaptor(this, new GPSLocation[] {});

		listOfOthersLV.setAdapter(aa);

	}

	// Called when push location button pressed
	private void pushLocationButtonPressed() {
		Location location = locator.getMyLocation(this);
		if (location != null) {
			double lat = location.getLatitude();
			double lon = location.getLongitude();
			myLatTV.setText("" + lat);
			myLonTV.setText("" + lon);
		} else {
			Toast.makeText(this,
					"Sorry GPS Location is unavailable/Turned off",
					Toast.LENGTH_SHORT).show();
		}
	}

	// Called when get others locations button pressed
	private void getLocationsButtonPressed() {
		AndroidSoda.async(new Runnable() {
			public void run() {
				gpsComm = ASm.get(GPSComm.class, GPSComm.SVC_NAME);
				aa.clear();
				aa.addAll(gpsComm.getLocations());
				aa.notifyDataSetChanged();
			}
		});
	}

	GPSLocationListViewAdaptor aa;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void connected(AndroidSoda as) {
		this.ASm = as;
	}

	public void connectionFailure(AndroidSoda s,
			AndroidSodaConnectionException ex) {
		Log.d(LOG_TAG, "Android Soda: " + s.toString()
				+ " , caused Exception: " + ex);
	}
}
