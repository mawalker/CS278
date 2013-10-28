package com.walkernation.gpslocations;

import java.util.List;

import org.magnum.soda.proxy.SodaAsync;

public interface GPSComm {

	public static final String SVC_NAME = "comm";

	@SodaAsync
	public void pushLocation(GPSLocation location);

	@SodaAsync
	public List<GPSLocation> getLocations();
}
