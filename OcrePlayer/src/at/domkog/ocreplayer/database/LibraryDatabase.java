package at.domkog.ocreplayer.database;

import java.util.ArrayList;

import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;

public interface LibraryDatabase {

	public ArrayList<LibraryEntry> loadLibrary();
	public ArrayList<String> loadFolders();
	
	public ArrayList<LibraryEntry> search(Attribute attribute, String key);
	public int getID(String path);
	
	public void add(LibraryEntry entry);
	public void remove(LibraryEntry entry);
	public void update(LibraryEntry entry);
	
	public boolean contains(String path);
	
	public void addFolder(String path);
	public void removeFolder(String path);
	
	public void shutdown();
	
}
