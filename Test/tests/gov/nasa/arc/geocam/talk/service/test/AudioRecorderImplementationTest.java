package gov.nasa.arc.geocam.talk.service.test;

import java.io.FileInputStream;

import org.junit.Test;

import com.google.inject.Inject;

import android.content.Context;
import android.util.Log;

import gov.nasa.arc.geocam.talk.service.AudioRecorderImplementation;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;


public class AudioRecorderImplementationTest extends GeoCamTestCase {
	@Inject Context context;
	@Inject AudioRecorderImplementation recorderImpl;
	
	@Test
	public void shouldGetAudioFileWhenRecorded() throws Exception {
		// arrange:
		//AudioRecorderImplementation recorderImpl = new AudioRecorderImplementation();
		
		// act:
		// Start Recording 
		Log.i("RECORDER", "Testing start recording");
		recorderImpl.startRecording();
		Thread.sleep(1000);
		// Stop Recording
		Log.i("RECORDER", "Testing stop recording");
		
		// assert:
		// Test audio file
		FileInputStream f = new FileInputStream(recorderImpl.stopRecording());
		assertNotNull(f);
		
	}

}
