package at.domkog.ocreplayer.libary.tag;

import java.io.IOException;

import com.google.common.io.Files;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;

public class ID3TagHandler implements TagHandler {

	private boolean initialized = false;;
	
	private Mp3File mp3File;
	private boolean hasID3v1Tag;
    private boolean hasID3v2Tag;
	
	@Override
	public String getTag(LibraryEntry entry, Attribute attribute) {
		if(!initialized) {
			this.mp3File = getMp3File(entry);
			this.hasID3v1Tag = mp3File.hasId3v1Tag();
		    this.hasID3v2Tag = mp3File.hasId3v2Tag();
		}
	    
	    switch (attribute) {
	        case ALBUMARTIST:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getAlbumArtist() : "";
	        case ALBUM:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getAlbum() : hasID3v1Tag ? mp3File.getId3v1Tag().getAlbum() : "";
	        case ARTIST:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getArtist() : hasID3v1Tag ? mp3File.getId3v1Tag().getArtist() : "";
	        case BPM:
	            return "" + (hasID3v2Tag ? mp3File.getId3v2Tag().getBPM() : "");
	        case COMPOSER:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getComposer() : "";
	        case DATE:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getDate() : "";
	        case LENGTH:
	            return "" + (hasID3v2Tag ? mp3File.getId3v2Tag().getLength() : "");
	        case PUBLISHER:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getPublisher() : "";
	        case COMMENT:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getComment() : hasID3v1Tag ? mp3File.getId3v1Tag().getComment() : "";
	        case GENRE:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getGenreDescription() : hasID3v1Tag ? mp3File.getId3v1Tag().getGenreDescription() : "";
	        case TITLE:
	        	String title = hasID3v2Tag ? mp3File.getId3v2Tag().getTitle() : hasID3v1Tag ? mp3File.getId3v1Tag().getTitle() : "";
	        	title = (title == null || title.equals("")) ? Files.getNameWithoutExtension(entry.getPath()) : title;
	            return title;
	        case TRACK:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getTrack() : hasID3v1Tag ? mp3File.getId3v1Tag().getTrack() : "";
	        case YEAR:
	            return hasID3v2Tag ? mp3File.getId3v2Tag().getYear() : hasID3v1Tag ? mp3File.getId3v1Tag().getYear() : "";
	        case RATING:
	            return "" + (hasID3v2Tag ? mp3File.getId3v2Tag().getWmpRating() : "");
	        default:
	            return "";
	    }
	}

	@Override
	public void setTag(LibraryEntry entry, Attribute attribute) {
	}
	
	public boolean isValid(LibraryEntry entry) {
		return getMp3File(entry) != null;
	}
	
	public Mp3File getMp3File(LibraryEntry entry) {
		try {
			return new Mp3File(entry.getPath());
		} catch (UnsupportedTagException e) {
			e.printStackTrace();
		} catch (InvalidDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public TagHandler newInstance() {
		return new ID3TagHandler();
	}

}
