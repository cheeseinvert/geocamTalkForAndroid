package gov.nasa.arc.geocam.talk.service;

import java.io.IOException;

public interface AudioPlayerInterface {
	public void playBeep() throws IllegalStateException, IOException;
	public void startPlaying(String filename) throws IllegalArgumentException, IllegalStateException, IOException;
	void stopPlaying();
}
