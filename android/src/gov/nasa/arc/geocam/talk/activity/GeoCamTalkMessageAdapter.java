package gov.nasa.arc.geocam.talk.activity;

import gov.nasa.arc.geocam.talk.R;
import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import roboguice.adapter.IterableAdapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.inject.Inject;

public class GeoCamTalkMessageAdapter extends IterableAdapter<GeoCamTalkMessage> {

	@Inject
	LayoutInflater mInflater;

	@Inject
	public GeoCamTalkMessageAdapter(Context context) {
		super(context, R.layout.list_item);
	}

	public void setTalkMessages(List<GeoCamTalkMessage> talkMessages) {
		this.clear();
		for (GeoCamTalkMessage m : talkMessages) {
			add(m);
		}
	}

	public GeoCamTalkMessage getTalkMessage(int position) {
		GeoCamTalkMessage msg = getItem(position);
		return msg;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row;

		if (null == convertView) {
			row = mInflater.inflate(R.layout.list_item, null);
		} else {
			row = convertView;
		}

		TextView contentTextView = (TextView) row.findViewById(R.id.content);
		TextView fullnameTextView = (TextView) row.findViewById(R.id.fullname);
		TextView contentTimestampTextView = (TextView) row.findViewById(R.id.content_timestamp);
		ImageView geolocationImageView = (ImageView) row.findViewById(R.id.hasGeoLocation);
		ImageView audioImageView = (ImageView) row.findViewById(R.id.hasAudio);

		GeoCamTalkMessage msg = getItem(position);

		contentTextView.setText(msg.getContent());
		fullnameTextView.setText(msg.getAuthorFullname());
		
		Date contentTimestamp = msg.getContentTimestampDate();

		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
		contentTimestampTextView.setText(sdf.format(contentTimestamp));

		if (msg.hasGeolocation()) {
			geolocationImageView.setVisibility(View.VISIBLE);
		} else {
			geolocationImageView.setVisibility(View.INVISIBLE);
		}
		if (msg.hasAudio()) {
			audioImageView.setVisibility(View.VISIBLE);
		} else {
			audioImageView.setVisibility(View.GONE);
		}
		
		
		//row.setClickable(true);
		//row.setFocusable(true);
		
		return row;
	}
}