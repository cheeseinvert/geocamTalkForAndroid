package gov.nasa.arc.geocam.talk.service;

import java.io.FileDescriptor;
import java.io.FileInputStream;

import com.google.inject.Inject;
import com.google.inject.Provider;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

public class AudioPlayerImplementation implements AudioPlayerInterface {

	@Inject protected static Provider<Context> contextProvider;
	
	MediaPlayer player = new MediaPlayer();
	
	@Override
	public void startPlaying(String filename) {
		if (player.isPlaying()) {
			player.stop();
		}
		try {
			player.reset();
			FileInputStream f = new FileInputStream(filename);
			player.setDataSource(f.getFD());
			player.prepare();
		    player.start();
		} 
		catch (Exception e) {
			Toast.makeText(contextProvider.get(), e.getMessage(), Toast.LENGTH_SHORT).show();
			player.reset();
		}
	}	
	
	@Override
	public void playBeep() {
		if (player.isPlaying()) {
			player.stop();
		}
		try {
			player.reset();
			player.setDataSource(contextProvider.get().getAssets().openFd("beep.mp3").getFileDescriptor());
			player.prepare();
		    player.start();
		} 
		catch (Exception e) {
			Toast.makeText(contextProvider.get(), e.getMessage(), Toast.LENGTH_SHORT).show();
			player.reset();
		}
	}	


}
