package at.domkog.coreplayer.database.sqlite;

import java.util.ArrayList;

import at.domkog.coreplayer.database.LibraryDatabase;
import at.domkog.coreplayer.libary.LibraryEntry;
import at.domkog.coreplayer.libary.LibraryEntry.Attribute;

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
