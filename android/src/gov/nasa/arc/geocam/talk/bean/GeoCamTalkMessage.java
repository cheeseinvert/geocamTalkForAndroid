package gov.nasa.arc.geocam.talk.bean;

import java.util.Date;

import android.location.Location;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * The Class GeoCamTalkMessage.
 */
@DatabaseTable(tableName = "talkMessages")
public class GeoCamTalkMessage implements Comparable<GeoCamTalkMessage> {

	/** The Constant DATE_FIELD_NAME. */
	public static final String DATE_FIELD_NAME = "contentTimestamp";
	
	/** The Constant MESSAGE_ID_FIELD_NAME. */
	public static final String MESSAGE_ID_FIELD_NAME = "messageId";
	
	/** The Constant IS_SYNCHRONIZED_FIELD_NAME. */
	public static final String IS_SYNCHRONIZED_FIELD_NAME = "isSynchronized";
	
	/** The is synchronized. */
	@DatabaseField(columnName = IS_SYNCHRONIZED_FIELD_NAME)
	private boolean		isSynchronized = false;
	
	/** The local id. */
	@DatabaseField(generatedId = true)
	private int			localId;
	
	/** The message id. */
	@DatabaseField(columnName = MESSAGE_ID_FIELD_NAME)
	private Integer 	messageId;
	
	/** The author id. */
	@DatabaseField
	private Integer  	authorId;
	
	/** The author username. */
	@DatabaseField
	private String 		authorUsername;
	
	/** The author fullname. */
	@DatabaseField
	private String		authorFullname;
	
	/** The content. */
	@DatabaseField
	private String 		content;
	
	/** The content timestamp. */
	@DatabaseField(columnName = DATE_FIELD_NAME)
	private Long 		contentTimestamp;
	
	/** The latitude. */
	@DatabaseField
	private Double 		latitude;
	
	/** The longitude. */
	@DatabaseField
	private Double 		longitude;
	
	/** The accuracy. */
	@DatabaseField
	private Integer 	accuracy;
	
	/** The audio. */
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[]		audio;
	
	/** The audio url. */
	@DatabaseField
	private String 		audioUrl;
	
	/**
	 * Gets the message id.
	 *
	 * @return the message id
	 */
	public int getMessageId() {
		if (this.messageId == null) {
			return -1;
		}
		else {
			return messageId;
		}
	}
	
	/**
	 * Sets the message id.
	 *
	 * @param messageId the new message id
	 */
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	
	/**
	 * Sets the author id.
	 *
	 * @param authorId the new author id
	 */
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	
	/**
	 * Gets the author id.
	 *
	 * @return the author id
	 */
	public Integer getAuthorId() {
		return authorId;
	}
	
	/**
	 * Gets the author username.
	 *
	 * @return the author username
	 */
	public String getAuthorUsername() {
		return authorUsername;
	}
	
	/**
	 * Sets the author username.
	 *
	 * @param authorUsername the new author username
	 */
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}
	
	/**
	 * Gets the content.
	 *
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	
	/**
	 * Gets the author fullname.
	 *
	 * @return the author fullname
	 */
	public String getAuthorFullname() {
		return authorFullname;
	}
	
	/**
	 * Sets the author fullname.
	 *
	 * @param authorFullname the new author fullname
	 */
	public void setAuthorFullname(String authorFullname) {
		this.authorFullname = authorFullname;
	}
	
	/**
	 * Sets the content.
	 *
	 * @param content the new content
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	/**
	 * Gets the content timestamp.
	 *
	 * @return the content timestamp
	 */
	public Long getContentTimestamp() {
		return this.contentTimestamp;
	}
	
	/**
	 * Sets the content timestamp.
	 *
	 * @param contentTimestamp the new content timestamp
	 */
	public void setContentTimestamp(Long contentTimestamp) {
		this.contentTimestamp = contentTimestamp;
	}
	
	/**
	 * Gets the content timestamp date.
	 *
	 * @return the content timestamp date
	 */
	public Date getContentTimestampDate() {
		return new Date(this.contentTimestamp);
	}
	
	/**
	 * Sets the content timestamp.
	 *
	 * @param contentTimestamp the new content timestamp
	 */
	public void setContentTimestamp(Date contentTimestamp) {
		this.contentTimestamp = contentTimestamp.getTime();
	}
	
	/**
	 * Gets the latitude.
	 *
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	
	/**
	 * Sets the latitude.
	 *
	 * @param latitude the new latitude
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	/**
	 * Gets the longitude.
	 *
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	
	/**
	 * Sets the longitude.
	 *
	 * @param longitude the new longitude
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	
	/**
	 * Gets the accuracy.
	 *
	 * @return the accuracy
	 */
	public Integer getAccuracy() {
		return accuracy;
	}
	
	/**
	 * Sets the accuracy.
	 *
	 * @param accuracy the new accuracy
	 */
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	
	/**
	 * Checks for geolocation.
	 *
	 * @return true, if successful
	 */
	public boolean hasGeolocation() {
		return this.longitude != null && this.latitude != null;
	}
	
	/**
	 * Gets the audio.
	 *
	 * @return the audio
	 */
	public byte[] getAudio() {
		return audio;
	}
	
	/**
	 * Sets the audio.
	 *
	 * @param audio the new audio
	 */
	public void setAudio(byte[] audio) {
		this.audio = audio;
	}
	
	/**
	 * Checks for audio.
	 *
	 * @return true, if successful
	 */
	public boolean hasAudio()
	{
		return this.audioUrl != null;
	}
	
	/**
	 * Gets the audio url.
	 *
	 * @return the audio url
	 */
	public String getAudioUrl() {
		return audioUrl;
	}
	
	/**
	 * Sets the audio url.
	 *
	 * @param audioUrl the new audio url
	 */
	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}
	
	/**
	 * Sets the synchronized.
	 *
	 * @param isSynchronized the new synchronized
	 */
	public void setSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}
	
	/**
	 * Checks if is synchronized.
	 *
	 * @return true, if is synchronized
	 */
	public boolean isSynchronized() {
		return isSynchronized;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		GeoCamTalkMessage other = (GeoCamTalkMessage)o;
		
		return 
		equalOrBothNull(this.messageId, other.messageId); // assumed there is no editing taking place
		/*&& 	
		equalOrBothNull(authorId, other.authorId) &&
		equalOrBothNull(authorUsername, other.authorUsername) &&
		equalOrBothNull(authorFullname, other.authorFullname) &&
		equalOrBothNull(content, other.content) &&
		equalOrBothNull(contentTimestamp, other.contentTimestamp) &&
		equalOrBothNull(latitude, other.latitude) &&
		equalOrBothNull(longitude, other.longitude) &&
		equalOrBothNull(accuracy, other.accuracy);*/
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		if (this.messageId != null) {
			return this.messageId.intValue();
		}
		else if (this.content != null) {
			return this.content.hashCode();
		}
		else {
			return super.hashCode();
		}
	}
	
	// TODO: Revisit this if we need other helper methods. Maybe move to global helper function?
	// Jakarta Commons library may provide some additional methods that would be useful
	/**
	 * Equal or both null.
	 *
	 * @param a the a
	 * @param b the b
	 * @return true, if successful
	 */
	public static boolean equalOrBothNull(Object a, Object b)
	{
		if(a != null && b != null)
		{
			return a.equals(b);
		}
		else if(a == null && b == null)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(GeoCamTalkMessage another) {
		if(this.equals(another)) {
			return 0;
		} else if(this.contentTimestamp == null) {
			return -1;
		} else if(another.getContentTimestamp() == null) {
			return 1;
		} else if(this.getContentTimestampDate().after(another.getContentTimestampDate())) {
			return 1;
		} else {
			return -1;
		}
	}
	
	/**
	 * Sets the location.
	 *
	 * @param location the new location
	 */
	public void setLocation(Location location) {
		if (location != null) {
			this.setLatitude(location.getLatitude());
			this.setLongitude(location.getLongitude());
			
			if (location.hasAccuracy()) {
				this.setAccuracy((int) location.getAccuracy());
			}
		}
	}
}
