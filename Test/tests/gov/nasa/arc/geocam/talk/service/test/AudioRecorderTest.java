package gov.nasa.arc.geocam.talk.service.test;

import java.io.FileInputStream;

import org.junit.Test;

import com.google.inject.Inject;

import android.content.Context;
import android.util.Log;

import gov.nasa.arc.geocam.talk.service.AudioRecorder;
import gov.nasa.arc.geocam.talk.test.GeoCamTestCase;


public class AudioRecorderTest extends GeoCamTestCase {
	@Inject Context context;
	@Inject AudioRecorder recorderImpl;
	
	@Test
	public void shouldGetAudioFileWhenRecorded() throws Exception {
		// arrange:
		//AudioRecorderImplementation recorderImpl = new AudioRecorderImplementation();
		
		// act:
		// Start Recording 
		Log.i("RECORDER", "Testing start recording");

		recorderImpl.startRecording("test_audio_recording.mp4");
		// Stop Recording
		Log.i("RECORDER", "Testing stop recording");
		
		// assert:
		// Test audio file
		assertNotNull(recorderImpl.stopRecording());
		
	}

}
