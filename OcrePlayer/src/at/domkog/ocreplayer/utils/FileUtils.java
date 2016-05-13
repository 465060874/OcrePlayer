package at.domkog.ocreplayer.utils;

import java.io.File;

import com.google.common.io.Files;

public class FileUtils {

	public static boolean isMp3(File file) {
		return Files.getFileExtension(file.getAbsolutePath()).equalsIgnoreCase("mp3");
	}
	
}
