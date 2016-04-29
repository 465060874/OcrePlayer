package at.domkog.ocreplayer.database;

import java.util.ArrayList;

import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;

public interface LibraryDatabase {

	public ArrayList<LibraryEntry> loadLibrary();
	
	public LibraryEntry getFromID(int ID);
	
	public ArrayList<LibraryEntry> search(Attribute attribute, String key);
	
	public void add(LibraryEntry entry);
	public void remove(LibraryEntry entry);
	public void update(LibraryEntry entry);
	
}