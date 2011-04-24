package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

// TODO: Auto-generated Javadoc
/**
 * The Class TalkJsonConverter.
 */
public class TalkJsonConverter
    implements ITalkJsonConverter {

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.ITalkJsonConverter#deserializeList(java.lang.String)
	 */
	@Override
	public List<GeoCamTalkMessage> deserializeList(String jsonString) {
		JsonParser parser = new JsonParser();
		JsonObject root = parser.parse(jsonString).getAsJsonObject();
		
		String jsonMessages = root.get("ms").toString();
		
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("MM/dd/yy HH:mm:ss");
		Gson gson = builder.create();
		
		Type listType = new TypeToken<List<GeoCamTalkMessage>>(){}.getType();
		
		return gson.fromJson(jsonMessages, listType);
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.ITalkJsonConverter#deserialize(java.lang.String)
	 */
	@Override
	public GeoCamTalkMessage deserialize(String jsonString) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("MM/dd/yy HH:mm:ss");
		
		Gson gson = builder.create();
		return gson.fromJson(jsonString, GeoCamTalkMessage.class);
	}
	

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.ITalkJsonConverter#serialize(gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage)
	 */
	@Override
	public String serialize(GeoCamTalkMessage message) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("MM/dd/yy HH:mm:ss");
		
		builder.setExclusionStrategies(new TalkMessageExclusionStrategy());
		Gson gson = builder.create();
		String ret= gson.toJson(message);
		return ret;
	}
	
	/**
	 * The Class TalkMessageExclusionStrategy.
	 */
	public class TalkMessageExclusionStrategy implements ExclusionStrategy {
        
        /* (non-Javadoc)
         * @see com.google.gson.ExclusionStrategy#shouldSkipField(com.google.gson.FieldAttributes)
         */
        @Override
		public boolean shouldSkipField(FieldAttributes f) {
            return (f.getName().equals("audio"));
        }

		/* (non-Javadoc)
		 * @see com.google.gson.ExclusionStrategy#shouldSkipClass(java.lang.Class)
		 */
		@Override
		public boolean shouldSkipClass(Class<?> arg0) {
			return false;
		}

    }

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.ITalkJsonConverter#createMap(java.lang.String)
	 */
	@Override
	public Map<String, String> createMap(String jsonString) {
		Gson gson = new Gson();
		Type mapType = new TypeToken<Map<String, String>>(){}.getType();
		return gson.fromJson(jsonString, mapType);
	}
}
