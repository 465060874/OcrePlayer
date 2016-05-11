package at.domkog.ocreplayer.database.sqlite;

import java.util.ArrayList;

import at.domkog.ocreplayer.database.StationDatabase;
import at.domkog.ocreplayer.libary.stations.StationEntry;

public class SQLiteStationLibrary implements StationDatabase {

	@Override
	public ArrayList<StationEntry> loadLibrary() {
		return null;
	}

	@Override
	public StationEntry getFromID(String ID) {
		return null;
	}

	@Override
	public void add(StationEntry entry) {
		
	}

	@Override
	public void remove(StationEntry entry) {
		
	}

	@Override
	public void update(StationEntry entry) {
		
	}

}
