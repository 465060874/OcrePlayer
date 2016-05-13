package at.domkog.ocreplayer.libary.tag;

import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;

public interface TagHandler {

	public String getTag(LibraryEntry entry, Attribute attribute);
	public void setTag(LibraryEntry entry, Attribute attribute);
	
	public boolean isValid(LibraryEntry entry);
	
	public TagHandler newInstance();
	
}
