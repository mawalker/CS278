package com.walkernation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.walkernation.gpslocations.GPSLocation;

public class GPSLocationListViewAdaptor extends ArrayAdapter<GPSLocation> {

	private final Context context;
	private final GPSLocation[] values;

	public GPSLocationListViewAdaptor(Context context, GPSLocation[] values) {
		super(context, R.layout.each_person_listview_row, values);
		this.context = context;
		this.values = values;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View rowView = inflater.inflate(R.layout.each_person_listview_row,
				parent, false);

		TextView person = (TextView) rowView.findViewById(R.id.person);
		TextView lat = (TextView) rowView.findViewById(R.id.lat);
		TextView lon = (TextView) rowView.findViewById(R.id.lon);

		person.setText("" + values[position].getName());
		lat.setText("" + values[position].getLat());
		lon.setText("" + values[position].getLon());

		return rowView;
	}
}
