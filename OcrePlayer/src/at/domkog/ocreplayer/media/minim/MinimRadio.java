package at.domkog.ocreplayer.media.minim;

import at.domkog.ocreplayer.libary.stations.StationEntry;
import at.domkog.ocreplayer.media.PlayerAPI.PlayerState;
import at.domkog.ocreplayer.media.RadioAPI;

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
