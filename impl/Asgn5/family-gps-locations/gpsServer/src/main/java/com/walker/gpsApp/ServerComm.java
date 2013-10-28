package com.walker.gpsApp;

import java.util.ArrayList;
import java.util.List;

import org.magnum.soda.proxy.SodaAsync;

import com.walkernation.gpslocations.GPSComm;
import com.walkernation.gpslocations.GPSLocation;

public class ServerComm implements GPSComm {

	@SodaAsync
	public void pushLocation(GPSLocation location) {
		System.out.println("Location Recieved: " + location.toString());
	}

	@SodaAsync
	public List<GPSLocation> getLocations() {
		List<GPSLocation> rValue = new ArrayList<GPSLocation>();
		for (int i = 0; i < 4; i++) {

			GPSLocation temp = new GPSLocation("Name " + i, Math.random() * 30,
					Math.random() * 30, System.nanoTime());
			rValue.add(temp);
		}
		return rValue;
	}
}
