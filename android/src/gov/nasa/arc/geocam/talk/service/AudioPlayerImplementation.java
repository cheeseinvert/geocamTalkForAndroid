package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.R;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Provider;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioPlayerImplementation implements AudioPlayerInterface {

	@Inject protected static Provider<Context> contextProvider;
	MediaPlayer player = new MediaPlayer();
	
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
		MediaPlayer mp = MediaPlayer.create(contextProvider.get(), R.raw.beep_a);
	    mp.start();
	}

	@Override
	public void playBeepB() {
		MediaPlayer mp = MediaPlayer.create(contextProvider.get(), R.raw.beep_b);
	    mp.start();
	}
}
