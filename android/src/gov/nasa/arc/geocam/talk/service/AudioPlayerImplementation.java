package gov.nasa.arc.geocam.talk.service;

import java.io.FileInputStream;
import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.Provider;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.widget.Toast;

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
	public void playBeep() throws IllegalStateException, IOException {
		player.reset();
		player.setDataSource(contextProvider.get().getAssets().openFd("beep.mp3").getFileDescriptor());
		player.prepare();
		player.start();

	}	


}
