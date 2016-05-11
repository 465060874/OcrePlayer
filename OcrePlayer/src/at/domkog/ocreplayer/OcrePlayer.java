package at.domkog.ocreplayer;

import java.io.File;

import at.domkog.ocreplayer.database.LibraryDatabase;
import at.domkog.ocreplayer.database.StationDatabase;
import at.domkog.ocreplayer.database.sqlite.PathMode;
import at.domkog.ocreplayer.database.sqlite.SQLiteLibrary;
import at.domkog.ocreplayer.database.sqlite.SQLiteStationLibrary;
import at.domkog.ocreplayer.libary.tag.ID3TagHandler;
import at.domkog.ocreplayer.libary.tag.TagHandler;
import at.domkog.ocreplayer.media.PlayerAPI;
import at.domkog.ocreplayer.media.RadioAPI;
import at.domkog.ocreplayer.media.minim.MinimPlayer;
import at.domkog.ocreplayer.media.minim.MinimRadio;
import at.domkog.ocreplayer.ui.UIManager;
import at.domkog.ocreplayer.ui.content.ContentManager;
import at.domkog.ocreplayer.ui.main.MainUI;
import at.domkog.ocreplayer.utils.OSHelper;
import at.domkog.ocreplayer.utils.OSHelper.OS;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

public class OcrePlayer extends Application {

	public static final ApplicationMode mode = ApplicationMode.STATIC;
	public static PathMode pathMode;
	
	private static final String TITLE = "OcrePlayer";
	private static final String VERSION = "InDev-1.1.0";
	private static final int COPYRIGHT_YEAR = 2016;
	
	public static OcrePlayer instance;
	
	@Getter
	public Stage stage;
	public static UIManager uiManager;
	public static MainUI mainUI;
	
	public static ContentManager contentManager;
	
	public static LibraryDatabase libraryDatabase;
	public static TagHandler tagHandler;
	
	public static StationDatabase stationDatabase;
	
	public static PlayerAPI playerAPI;
	public static RadioAPI radioAPI;
	
	public static File workingDir;
	
	public static void main(String... args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;
		stage = primaryStage;
		
		primaryStage.setTitle(TITLE + "-" + VERSION + "-" + COPYRIGHT_YEAR);
		
		if(mode == ApplicationMode.STATIC) {
			pathMode = PathMode.ABSOLUTE;
			if(OSHelper.getOS() == OS.WINDOWS) {
				workingDir = new File(System.getenv("APPDATA") + "/OcrePlayer");
			} else {
				workingDir = new File(System.getProperty("user.home") + "/OcrePlayer");
			}
		} else {
			pathMode = PathMode.RELATIVE;
			workingDir = new File("/OcrePlayer");
		}
		
		if(!workingDir.exists()) workingDir.mkdirs();
		
		libraryDatabase = new SQLiteLibrary();
		tagHandler = new ID3TagHandler();
		
		stationDatabase = new SQLiteStationLibrary();
		
		playerAPI = new MinimPlayer();
		radioAPI = new MinimRadio();
		
		//Initialize UIManager and show default scene
		uiManager = new UIManager();
		this.registerUIs();
		uiManager.show("main");
		
		primaryStage.show();
		
		primaryStage.setOnCloseRequest((event) -> {
			libraryDatabase.shutdown();
		});		
	}
	
	private void registerUIs() {
		uiManager.register(mainUI = new MainUI());
	}

}
