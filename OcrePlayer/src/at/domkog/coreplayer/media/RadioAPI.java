package at.domkog.coreplayer.media;

import at.domkog.coreplayer.libary.stations.StationEntry;
import at.domkog.coreplayer.media.PlayerAPI.PlayerState;

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
