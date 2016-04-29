package at.domkog.ocreplayer.media;

import at.domkog.ocreplayer.libary.stations.StationEntry;
import at.domkog.ocreplayer.media.PlayerAPI.PlayerState;

public interface RadioAPI {

	public void play(StationEntry entry);
	
	public void pause();
	public void resume();
	
	public void setVolume(double volume);
	public double getVolume();
	
	public void mute();
	public boolean isMuted();
	
	public PlayerState getState();
	
}
