package com.walkernation.gpslocations;

import org.magnum.soda.proxy.SodaByValue;

@SodaByValue
public class GPSLocation {

	public static final String SVC_NAME = "location";
	private String name;
	private double lat;
	private double lon;
	private long age;

	public GPSLocation(String name, double lat, double lon, long age) {
		super();
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		this.age = age;
	}

	@Override
	public String toString() {
		return "GPSLocation [name=" + name + ", lat=" + lat + ", lon=" + lon
				+ ", age=" + age + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	public long getAge() {
		return age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public static String getSvcName() {
		return SVC_NAME;
	}

}
