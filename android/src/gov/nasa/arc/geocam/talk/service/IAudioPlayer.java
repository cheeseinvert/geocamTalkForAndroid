package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Interface IAudioPlayer.
 */
public interface IAudioPlayer {
	
	/**
	 * Start playing the audio from the binary audio bytes.
	 *
	 * @param soundByte the sound byte
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startPlaying(byte[] soundByte) throws IllegalArgumentException, IllegalStateException, IOException;
	
	/**
	 * Start playing the audio from the file locations.
	 *
	 * @param filename the filename
	 * @throws IllegalArgumentException the illegal argument exception
	 * @throws IllegalStateException the illegal state exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startPlaying(String filename) throws IllegalArgumentException, IllegalStateException, IOException;
	
	/**
	 * Start playing with tts intro.
	 *
	 * @param intro the intro
	 * @param filename the filename
	 */
	public void startPlayingWithTtsIntro(String intro, String filename);
	
	/**
	 * Start playing with tts intro.
	 *
	 * @param intro the intro
	 * @param soundByte the sound byte
	 */
	void startPlayingWithTtsIntro(String intro, byte[] soundByte);
	
	/**
	 * Speak.
	 *
	 * @param text the text
	 */
	public void speak(String text);
	
	/**
	 * Stop playing any audio that is currently playing.
	 */
	void stopPlaying();
	
	/**
	 * Play beep a to signify that the recording has started..
	 */
	void playBeepA();
	
	/**
	 * Play beep b to signify that the recording has stopped.
	 */
	void playBeepB();
}
