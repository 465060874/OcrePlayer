package at.domkog.ocreplayer.media.minim;

import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.media.PlayerAPI;

public class MinimPlayer implements PlayerAPI {

	@Override
	public void play(LibraryEntry entry) {
		
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public boolean hasPrevious() {
		return false;
	}

	@Override
	public void playNext() {
		
	}

	@Override
	public void playPrevious() {
		
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void setVolume(double volume) {
		
	}

	@Override
	public double getVolume() {
		return 0;
	}

	@Override
	public void mute() {
		
	}

	@Override
	public boolean isMuted() {
		return false;
	}

	@Override
	public PlayerState getState() {
		return null;
	}

}
