package com.walkernation;

import java.util.List;

import android.content.Context;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.util.Log;

public class LocatorImpl implements Locator {

	public Location getMyLocation(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(context.LOCATION_SERVICE);
		Geocoder geocoder = new Geocoder(context);

		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		List<String> lProviders = locationManager.getProviders(true);
		for (int i = 0; i < lProviders.size(); i++) {
			Log.d("LocationProvidersGet:", lProviders.get(i));
		}
		String providerName = locationManager.getBestProvider(criteria, true);

		if (providerName != null && !providerName.equals("")) {
			LocationProvider provider = locationManager
					.getProvider(providerName);
			if (provider != null) {
				return locationManager.getLastKnownLocation(providerName);

			}
		}
		return null;
	}
}
