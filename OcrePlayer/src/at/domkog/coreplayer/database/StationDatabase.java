package at.domkog.coreplayer.database;

import java.util.ArrayList;

import at.domkog.coreplayer.libary.stations.StationEntry;

public interface StationDatabase {

	public ArrayList<StationEntry> loadLibrary();
	
	public StationEntry getFromID(int ID);
	
	public void add(StationEntry entry);
	public void remove(StationEntry entry);
	public void update(StationEntry entry);
	
}
