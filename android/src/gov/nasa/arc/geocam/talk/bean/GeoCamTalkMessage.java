package gov.nasa.arc.geocam.talk.bean;

import java.util.Date;

import android.location.Location;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "talkMessages")
public class GeoCamTalkMessage implements Comparable<GeoCamTalkMessage> {

	public static final String DATE_FIELD_NAME = "contentTimestamp";
	public static final String MESSAGE_ID_FIELD_NAME = "messageId";
	public static final String IS_SYNCHRONIZED_FIELD_NAME = "isSynchronized";
	
	@DatabaseField(columnName = IS_SYNCHRONIZED_FIELD_NAME)
	private boolean		isSynchronized = false;
	
	@DatabaseField(generatedId = true)
	private int			localId;
	
	@DatabaseField(columnName = MESSAGE_ID_FIELD_NAME)
	private Integer 	messageId;
	
	@DatabaseField
	private Integer  	authorId;
	
	@DatabaseField
	private String 		authorUsername;
	
	@DatabaseField
	private String		authorFullname;
	
	@DatabaseField
	private String 		content;
	
	@DatabaseField(columnName = DATE_FIELD_NAME)
	private Long 		contentTimestamp;
	
	@DatabaseField
	private Double 		latitude;
	
	@DatabaseField
	private Double 		longitude;
	
	@DatabaseField
	private Integer 	accuracy;
	
	@DatabaseField(dataType = DataType.BYTE_ARRAY)
	private byte[]		audio;
	
	@DatabaseField
	private String 		audioUrl;
	
	public int getMessageId() {
		if (this.messageId == null) {
			return -1;
		}
		else {
			return messageId;
		}
	}
	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public Integer getAuthorId() {
		return authorId;
	}
	public String getAuthorUsername() {
		return authorUsername;
	}
	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}
	public String getContent() {
		return content;
	}
	public String getAuthorFullname() {
		return authorFullname;
	}
	public void setAuthorFullname(String authorFullname) {
		this.authorFullname = authorFullname;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getContentTimestamp() {
		return this.contentTimestamp;
	}
	public void setContentTimestamp(Long contentTimestamp) {
		this.contentTimestamp = contentTimestamp;
	}
	public Date getContentTimestampDate() {
		return new Date(this.contentTimestamp);
	}
	public void setContentTimestamp(Date contentTimestamp) {
		this.contentTimestamp = contentTimestamp.getTime();
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public Integer getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
	public boolean hasGeolocation() {
		return this.longitude != null && this.latitude != null;
	}
	
	public byte[] getAudio() {
		return audio;
	}
	public void setAudio(byte[] audio) {
		this.audio = audio;
	}
	
	public boolean hasAudio()
	{
		return this.audioUrl != null;
	}
	
	public String getAudioUrl() {
		return audioUrl;
	}
	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}
	
	public void setSynchronized(boolean isSynchronized) {
		this.isSynchronized = isSynchronized;
	}
	public boolean isSynchronized() {
		return isSynchronized;
	}
	
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
