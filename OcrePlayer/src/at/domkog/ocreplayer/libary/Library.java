package at.domkog.ocreplayer.libary;

import java.io.File;
import java.util.ArrayList;
import java.util.stream.Collectors;

import at.domkog.ocreplayer.database.LibraryDatabase;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;

public class Library {

	public LibraryDatabase db;
	
	public ArrayList<LibraryEntry> entries;
	public ArrayList<String> folders;
	
	public Library(LibraryDatabase db) {
		this.db = db;
		
		this.entries = db.loadLibrary();
		
		System.out.println("Loaded " + entries.size() + " LibraryEntries!");
		
		this.folders = db.loadFolders();
	}
	
	public boolean contains(String path) {
		ArrayList<LibraryEntry> result = entries.stream().filter((e) -> e.getAttribute(Attribute.PATH).equals(path)).collect(Collectors.toCollection(ArrayList::new));
		return !result.isEmpty();
	}
	
	public boolean contains(File file) {
		return this.contains(file.getAbsolutePath());
	}
	
	public boolean containsFolder(File file) {
		ArrayList<String> result = folders.stream().filter((path) -> path.equals(file.getAbsolutePath())).collect(Collectors.toCollection(ArrayList::new));
		return !result.isEmpty();
	}
	
	public LibraryEntry getFromPath(String path) {
		ArrayList<LibraryEntry> result = entries.stream().filter((e) -> e.getAttribute(Attribute.PATH).equals(path)).collect(Collectors.toCollection(ArrayList::new));
		if(result.isEmpty()) return null;
		else return result.get(0);
	}
	
}
