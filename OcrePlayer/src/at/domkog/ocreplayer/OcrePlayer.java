package at.domkog.ocreplayer;

import at.domkog.coreplayer.ui.UIManager;
import at.domkog.coreplayer.ui.content.ContentManager;
import at.domkog.coreplayer.ui.main.MainUI;
import javafx.application.Application;
import javafx.stage.Stage;
import lombok.Getter;

public class OcrePlayer extends Application {

	private static final String TITLE = "OcrePlayer";
	private static final String VERSION = "InDev-1.0.0";
	private static final int COPYRIGHT_YEAR = 2016;
	
	public static OcrePlayer instance;
	
	@Getter
	public Stage stage;
	public static UIManager uiManager;
	public static MainUI mainUI;
	
	public static ContentManager contentManager;
	
	public static void main(String... args) {
		launch();
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		instance = this;
		stage = primaryStage;
		
		primaryStage.setTitle(TITLE + "-" + VERSION + "-" + COPYRIGHT_YEAR);
		
		//Initialize UIManager and show default scene
		uiManager = new UIManager();
		this.registerUIs();
		uiManager.show("main");
		
		primaryStage.show();
	}
	
	private void registerUIs() {
		uiManager.register(mainUI = new MainUI());
	}

}
