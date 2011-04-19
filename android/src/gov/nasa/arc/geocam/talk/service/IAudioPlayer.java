package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;

public interface IAudioPlayer {
	public void startPlaying(byte[] soundByte) throws IllegalArgumentException, IllegalStateException, IOException;
	public void startPlaying(String filename) throws IllegalArgumentException, IllegalStateException, IOException;
	public void startPlayingWithTtsIntro(String intro, String filename);
	void startPlayingWithTtsIntro(String intro, byte[] soundByte);
	public void speak(String text);
	void stopPlaying();
	void playBeepA();
	void playBeepB();
}
