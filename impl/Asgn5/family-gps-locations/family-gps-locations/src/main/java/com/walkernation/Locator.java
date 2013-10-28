package com.walkernation;

import android.content.Context;
import android.location.Location;

public interface Locator {

	Location getMyLocation(Context context);
	
}
