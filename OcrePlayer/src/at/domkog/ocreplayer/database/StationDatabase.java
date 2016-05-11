package at.domkog.ocreplayer.database;

import java.util.ArrayList;

import at.domkog.ocreplayer.libary.stations.StationEntry;

public interface StationDatabase {

	public ArrayList<StationEntry> loadLibrary();
	
	public StationEntry getFromID(String ID);
	
	public void add(StationEntry entry);
	public void remove(StationEntry entry);
	public void update(StationEntry entry);
	
}
