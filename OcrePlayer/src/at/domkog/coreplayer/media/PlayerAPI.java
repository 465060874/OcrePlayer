package at.domkog.coreplayer.media;

import at.domkog.coreplayer.libary.LibraryEntry;

public interface PlayerAPI {

	public enum PlayerState {
		WAITING, PLAYING, PAUSED;
	}
	
	public void play(LibraryEntry entry);
	
	public boolean hasNext();
	public boolean hasPrevious();
	
	public void playNext();
	public void playPrevious();
	
	public void pause();
	public void resume();
	
	public void setVolume(double volume);
	public double getVolume();
	
	public void mute();
	public boolean isMuted();
	
	public PlayerState getState();
	
}
