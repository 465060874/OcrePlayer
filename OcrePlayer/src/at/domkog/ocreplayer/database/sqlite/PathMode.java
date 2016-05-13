package at.domkog.ocreplayer.database.sqlite;

import java.io.File;

import at.domkog.ocreplayer.OcrePlayer;

public enum PathMode {

	RELATIVE, ABSOLUTE;
	
	public static final String jarPath = OcrePlayer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	
	public String createPath(File file) {
		switch(this) {
		case ABSOLUTE:
			return file.getAbsolutePath();
		case RELATIVE:
			return file.getAbsolutePath().split(":")[0];
		default:
			return null;
		}
	}
	
	public File getPath(String path) {
		switch(this) {
		case ABSOLUTE:
			return new File(path);
		case RELATIVE:
			return buildRelativePath(path);
		default:
			return null;
		}
	}
	
	private File buildRelativePath(String path) {
		String adr = jarPath.split(":")[0];
		return new File(adr + ":" + path);
	}
	
}
