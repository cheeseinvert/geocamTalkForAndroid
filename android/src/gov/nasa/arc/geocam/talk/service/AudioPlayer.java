package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

import com.google.inject.Inject;

public class AudioPlayer implements IAudioPlayer, OnInitListener{

	Context context;
	MediaPlayer player = new MediaPlayer();
	TextToSpeech mTts;
	boolean textToSpeechOk = false;
	
	@Inject
	public AudioPlayer(Context context)
	{
		mTts = new TextToSpeech(context, this);
		this.context = context;
	}
	
	@Override
	public void startPlaying(String filename) throws IllegalArgumentException, IllegalStateException, IOException {
		if (player.isPlaying()) {
			player.stop();
		}
		player.reset();
		FileInputStream f = new FileInputStream(filename);
		player.setDataSource(f.getFD());
		player.prepare();
		player.start();
	}
	
	@Override
	public void stopPlaying() {
		player.stop();
		player.reset();
	}
	
	@Override
	public void playBeepA() {
		MediaPlayer mp = MediaPlayer.create(context, R.raw.beep_a);
	    mp.start();
	}

	@Override
	public void playBeepB() {
		MediaPlayer mp = MediaPlayer.create(context, R.raw.beep_b);
	    mp.start();
	}
	
	@Override
	public void startPlaying(byte[] soundByte) throws IllegalArgumentException, IllegalStateException, IOException {
		startPlaying(bytesToFilename(soundByte));
	}

	private String bytesToFilename(byte[] soundByte) throws IOException
	{
		File tempAudio = File.createTempFile("tempAudioMessage", ".mp4", context.getFilesDir());
        //tempAudio.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempAudio);
        fos.write(soundByte);
        fos.close();
        return tempAudio.getAbsolutePath();
	}
	
	@Override
	public void startPlayingWithTtsIntro(String intro, byte[] soundByte) {
		try {
			startPlayingWithTtsIntro(intro, bytesToFilename(soundByte));
		} catch (IOException e) {
			Log.e("Talk", "audio filewrite failed");
		}
	}
	
	@Override
	public void startPlayingWithTtsIntro(String intro, String filename) {
		HashMap<String, String> alarm = new HashMap<String, String>();
		alarm.put(TextToSpeech.Engine.KEY_PARAM_STREAM,
		        String.valueOf(AudioManager.STREAM_ALARM));
		alarm.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID,
				filename);		
		mTts.speak(intro, TextToSpeech.QUEUE_FLUSH, alarm);
	}
	
	public void onUtteranceCompleted(String filename) throws IllegalArgumentException, IllegalStateException, IOException {
		startPlaying(filename);
	}

	@Override
	public void onInit(int status) {
		if(status == TextToSpeech.SUCCESS)
		{
			textToSpeechOk = true;
		}
	}

	@Override
	public void speak(String text) {
		mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);		
	}
}
