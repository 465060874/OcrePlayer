package at.domkog.coreplayer.media.minim;

import at.domkog.coreplayer.libary.stations.StationEntry;
import at.domkog.coreplayer.media.PlayerAPI.PlayerState;
import at.domkog.coreplayer.media.RadioAPI;

public class MinimRadio implements RadioAPI {

	@Override
	public void play(StationEntry entry) {
		
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
