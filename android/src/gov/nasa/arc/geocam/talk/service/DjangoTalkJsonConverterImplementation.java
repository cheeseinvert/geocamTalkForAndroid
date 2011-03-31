package gov.nasa.arc.geocam.talk.service;

import gov.nasa.arc.geocam.talk.bean.GeoCamTalkMessage;

import java.lang.reflect.Type;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class DjangoTalkJsonConverterImplementation
    implements DjangoTalkJsonConverterInterface {

	@Override
	public List<GeoCamTalkMessage> deserializeList(String jsonString) {
		Pattern jsonList = Pattern.compile("^.*\"ms\":\\s+(.*)\\]$");
		Matcher m = jsonList.matcher(jsonString);

		if  (m.find()){
			GsonBuilder builder = new GsonBuilder();
			builder.setDateFormat("MM/dd/yy HH:mm:ss");
			Gson gson = builder.create();
			
			Type listType = new TypeToken<List<GeoCamTalkMessage>>(){}.getType();
			
			return gson.fromJson(m.group(1), listType);
		} else {
			return null;
		}
		
	}

	public GeoCamTalkMessage deserialize(String jsonString) {
		GsonBuilder builder = new GsonBuilder();
		builder.setDateFormat("MM/dd/yy HH:mm:ss");
		
		Gson gson = builder.create();
		return gson.fromJson(jsonString, GeoCamTalkMessage.class);
	}
}
