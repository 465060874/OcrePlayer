package at.domkog.ocreplayer.libary;

import java.util.HashMap;

import at.domkog.ocreplayer.OcrePlayer;
import at.domkog.ocreplayer.libary.tag.TagHandler;

public class LibraryEntry {

	public enum Attribute {
		ID, PATH, ALBUMARTIST, ALBUM, ARTIST, BPM, COMPOSER, DATE, LENGTH, PUBLISHER, COMMENT, GENRE, TITLE, TRACK, YEAR, RATING;
	
		public String formatedName() {
			return this.toString().charAt(0) + this.toString().toLowerCase().substring(1);
		}
	}

	private HashMap<Attribute, String> attributes;
	
	public LibraryEntry() {
		attributes = new HashMap<Attribute, String>();
		this.addDefaults();
	}
	
	public LibraryEntry(String path) {
		attributes = new HashMap<Attribute, String>();
		this.addDefaults();
		
		attributes.put(Attribute.PATH, path);
	}
	
	public int getID() {
		return Integer.parseInt(getAttribute(Attribute.ID));
	}
	
	public String getPath() {
		return getAttribute(Attribute.PATH);
	}
	
	public String getAttribute(Attribute a) {
		return attributes.get(a);
	}
	
	public void setAttribute(Attribute a, String value) {
		attributes.put(a, value);
	}
	
	public boolean syncTags() {
		TagHandler tagHandler = OcrePlayer.tagHandler.newInstance();
		if(!tagHandler.isValid(this)) return false;
		for(Attribute a: Attribute.values()) {
			if(a == Attribute.PATH || a == Attribute.ID) continue;
			else attributes.put(a, tagHandler.getTag(this, a));
		}
		return true;
	}
	
	private void addDefaults() {
		for(Attribute a: Attribute.values()) {
			attributes.put(a, "");
		}
	}
	
}
