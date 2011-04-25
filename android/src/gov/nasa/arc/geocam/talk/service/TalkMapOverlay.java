package gov.nasa.arc.geocam.talk.service;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

// TODO: Auto-generated Javadoc
/**
 * The Class TalkMapOverlay which creates a marker on the 
 * Talk map where the Talk Message was sent from..
 */
public class TalkMapOverlay extends ItemizedOverlay<OverlayItem> {

	/** The m overlays. */
	private ArrayList<OverlayItem> mOverlays = new ArrayList<OverlayItem>();
	
	/**
	 * Instantiates a new talk map overlay.
	 *
	 * @param defaultMarker the default marker
	 */
	public TalkMapOverlay(Drawable defaultMarker) {
		super(boundCenterBottom(defaultMarker));
	}

	/**
	 * Adds the overlay.
	 *
	 * @param overlay the overlay
	 */
	public void addOverlay(OverlayItem overlay) {
	    mOverlays.add(overlay);
	    populate();
	}
	
	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#createItem(int)
	 */
	@Override
	protected OverlayItem createItem(int i) {
		return mOverlays.get(i);
	}

	/* (non-Javadoc)
	 * @see com.google.android.maps.ItemizedOverlay#size()
	 */
	@Override
	public int size() {
		return mOverlays.size();
	}
}
