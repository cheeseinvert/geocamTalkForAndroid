package gov.nasa.arc.geocam.talk.service;


import gov.nasa.arc.geocam.talk.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;

import com.google.inject.Inject;

public class AudioPlayer implements IAudioPlayer {

	Context context;
	MediaPlayer player = new MediaPlayer();
	
	@Inject
	public AudioPlayer(Context context)
	{
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
		
		File tempAudio = File.createTempFile("tempAudioMessage", ".mp4", context.getFilesDir());
        //tempAudio.deleteOnExit();
        FileOutputStream fos = new FileOutputStream(tempAudio);
        fos.write(soundByte);
        fos.close();
        
		startPlaying(tempAudio.getAbsolutePath());
	}
}
