package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

public class DjangoTalkJsonConverterImplementation
    implements DjangoTalkJsonConverterInterface {

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

	public GeoCamTalkMessage deserialize(String jsonString) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("MM/dd/yy HH:mm:ss");
		
		Gson gson = builder.create();
		return gson.fromJson(jsonString, GeoCamTalkMessage.class);
	}
}
