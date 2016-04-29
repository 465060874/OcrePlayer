package at.domkog.ocreplayer.database.sqlite;

import java.util.ArrayList;

import at.domkog.ocreplayer.database.LibraryDatabase;
import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;

public class SQLiteLibrary implements LibraryDatabase {

	@Override
	public ArrayList<LibraryEntry> loadLibrary() {
		return null;
	}

	@Override
	public LibraryEntry getFromID(int ID) {
		return null;
	}

	@Override
	public ArrayList<LibraryEntry> search(Attribute attribute, String key) {
		return null;
	}

	@Override
	public void add(LibraryEntry entry) {
		
	}

	@Override
	public void remove(LibraryEntry entry) {
		
	}

	@Override
	public void update(LibraryEntry entry) {
		
	}

}
