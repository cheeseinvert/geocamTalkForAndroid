package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

import com.google.inject.Inject;

/**
 * Implementation of the {@link IAudioPlayer} interface.
 */
public class AudioPlayer implements IAudioPlayer, OnInitListener, OnUtteranceCompletedListener{

	/** The context. */
	Context context;
	
	/** The player. */
	MediaPlayer player;
	
	/**
	 * Instantiates a new audio player.
	 *
	 * @param context the context
	 */
	@Inject
	public AudioPlayer(Context context)
	{
		player = new MediaPlayer();
		this.context = context;
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioPlayer#startPlaying(java.lang.String)
	 */
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
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioPlayer#stopPlaying()
	 */
	@Override
	public void stopPlaying() {
		player.stop();
		player.reset();
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioPlayer#playBeepA()
	 */
	@Override
	public void playBeepA() {
		MediaPlayer mp = MediaPlayer.create(context, R.raw.beep_a);
	    mp.start();
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioPlayer#playBeepB()
	 */
	@Override
	public void playBeepB() {
		MediaPlayer mp = MediaPlayer.create(context, R.raw.beep_b);
	    mp.start();
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioPlayer#startPlaying(byte[])
	 */
	@Override
	public void startPlaying(byte[] soundByte) throws IllegalArgumentException, IllegalStateException, IOException {
		startPlaying(bytesToFilename(soundByte));
	}

	/**
	 * Writes a byte array to the filesystem and returns the filename as an MP4 file
	 *
	 * @param soundByte The audio file contents to be written
	 * @return the name of the temporary mp4 file
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String bytesToFilename(byte[] soundByte) throws IOException
	{
		File tempAudio = File.createTempFile("tempAudioMessage", ".mp4", context.getFilesDir());
        //tempAudio.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempAudio);
        fos.write(soundByte);
        fos.close();
        return tempAudio.getAbsolutePath();
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioPlayer#startPlayingWithTtsIntro(java.lang.String, byte[])
	 */
	@Override
	public void startPlayingWithTtsIntro(String intro, byte[] soundByte) {
		try {
			startPlayingWithTtsIntro(intro, bytesToFilename(soundByte));
		} catch (IOException e) {
			Log.e("Talk", "audio filewrite failed");
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioPlayer#startPlayingWithTtsIntro(java.lang.String, java.lang.String)
	 */
	@Override
	public void startPlayingWithTtsIntro(String intro, String filename) {
		try {
			startPlaying(filename);
		} catch (Exception e) {
			Log.e("Talk", "Could not play audio");
		}
	}
	
	/* (non-Javadoc)
	 * @see android.speech.tts.TextToSpeech.OnUtteranceCompletedListener#onUtteranceCompleted(java.lang.String)
	 */
	@Override
	public void onUtteranceCompleted(String filename) {
		try {
			startPlaying(filename);
		} catch (Exception e) {
			Log.e("Talk", "Could not play audio");
		}
		Log.i("Talk", "Utterance receieved");
	}

	/* (non-Javadoc)
	 * @see android.speech.tts.TextToSpeech.OnInitListener#onInit(int)
	 */
	@Override
	public void onInit(int status) {
		if(status == TextToSpeech.SUCCESS)
		{
			//textToSpeechOk = true;
		}
	}

	/* (non-Javadoc)
	 * @see gov.nasa.arc.geocam.talk.service.IAudioPlayer#speak(java.lang.String)
	 */
	@Override
	public void speak(String text) {
		//mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);		
	}
}
