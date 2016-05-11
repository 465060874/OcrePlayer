package at.domkog.ocreplayer.ui.main;

import at.domkog.ocreplayer.OcrePlayer;
import at.domkog.ocreplayer.media.InputController;
import at.domkog.ocreplayer.ui.UI;
import at.domkog.ocreplayer.ui.content.ContentManager;
import at.domkog.ocreplayer.ui.content.ContentPane;
import at.domkog.ocreplayer.ui.content.options.OptionPane;
import at.domkog.ocreplayer.ui.content.player.PlayerPane;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class MainUI extends UI {

	private Scene scene;
	private AnchorPane root;
	
	public ProgressSlider timeline, volume;
	public Text currentTime, endTime;
	
	@Override
	public String getID() {
		return "main";
	}

	@Override
	public void show() {
		//Header
		Header header = new Header();
		root.getChildren().add(header);
		AnchorPane.setTopAnchor(header, 0.0);
		AnchorPane.setLeftAnchor(header, 0.0);
		AnchorPane.setRightAnchor(header, 0.0);
		
		//Sidebar
		Sidebar sidebar = new Sidebar();
		root.getChildren().add(sidebar);
		AnchorPane.setTopAnchor(sidebar, 50.0);
		AnchorPane.setLeftAnchor(sidebar, 0.0);
		AnchorPane.setBottomAnchor(sidebar, 0.0);
		
		//ContentPane
		OcrePlayer.contentManager = new ContentManager();
		this.registerContentPanes();
		ContentPane contentPane = OcrePlayer.contentManager.getContentPane();
		root.getChildren().add(contentPane);
		AnchorPane.setTopAnchor(contentPane, 100.0);
		AnchorPane.setLeftAnchor(contentPane, 250.0);
		AnchorPane.setRightAnchor(contentPane, 50.0);
		AnchorPane.setBottomAnchor(contentPane, 50.0);
		
		//Previous
		Button previousBtn = new Button();
		previousBtn.setMinSize(16, 16);
		previousBtn.setMaxSize(16, 16);
		previousBtn.getStyleClass().add("previous_Button");
		previousBtn.setOnAction((e) -> InputController.onPressPrevious());
		header.getChildren().add(previousBtn);
		AnchorPane.setTopAnchor(previousBtn, 17.0);
		AnchorPane.setBottomAnchor(previousBtn, 17.0);
		AnchorPane.setLeftAnchor(previousBtn, 58.0);
		
		//Play/Pause
		Button playBtn = new Button();
		playBtn.setMinSize(32, 32);
		playBtn.setMaxSize(32, 32);
		playBtn.getStyleClass().add("play_Button");
		playBtn.setOnAction((e) -> InputController.onPressPlay());
		header.getChildren().add(playBtn);
		AnchorPane.setTopAnchor(playBtn, 9.0);
		AnchorPane.setBottomAnchor(playBtn, 9.0);
		AnchorPane.setLeftAnchor(playBtn, 84.0);
		
		//Previous
		Button nextBtn = new Button();
		nextBtn.setMinSize(16, 16);
		nextBtn.setMaxSize(16, 16);
		nextBtn.getStyleClass().add("next_Button");
		nextBtn.setOnAction((e) -> InputController.onPressNext());
		header.getChildren().add(nextBtn);
		AnchorPane.setTopAnchor(nextBtn, 17.0);
		AnchorPane.setBottomAnchor(nextBtn, 17.0);
		AnchorPane.setLeftAnchor(nextBtn, 126.0);
		
		//Header > Volume
		volume = new ProgressSlider(100, 4, 4, 100);
		header.getChildren().add(volume);
		AnchorPane.setTopAnchor(volume, 13.0);
		AnchorPane.setBottomAnchor(volume, 13.0);
		AnchorPane.setLeftAnchor(volume, 200.0);
		
		Button volumeBtn = new Button();
		volumeBtn.setMinSize(12, 12);
		volumeBtn.setMaxSize(12, 12);
		volumeBtn.getStyleClass().add("speaker_Button");
		volumeBtn.setOnAction((e) -> InputController.onPressMute());
		header.getChildren().add(volumeBtn);
		AnchorPane.setTopAnchor(volumeBtn, 34.0);
		AnchorPane.setLeftAnchor(volumeBtn, 200.0);
		
		//Header > Timeline
		timeline = new ProgressSlider(-1, 4, 4, -1);
		header.getChildren().add(timeline);
		AnchorPane.setTopAnchor(timeline, 13.0);
		AnchorPane.setBottomAnchor(timeline, 13.0);
		AnchorPane.setRightAnchor(timeline, 100.0);
		AnchorPane.setLeftAnchor(timeline, 325.0);
		
		//Seperator
		Separator s1 = new Separator();
		s1.setOrientation(Orientation.VERTICAL);
			header.getChildren().add(s1);
		AnchorPane.setTopAnchor(s1, 7.5);
		AnchorPane.setBottomAnchor(s1, 7.5);
		AnchorPane.setLeftAnchor(s1, 312.5);
		
		//Header > Time
		currentTime = new Text("0:00");
		currentTime.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12; -fx-fill: #D0D0D2;");
		header.getChildren().add(currentTime);
		AnchorPane.setTopAnchor(currentTime, 32.0);
		AnchorPane.setLeftAnchor(currentTime, 325.0);
		
		endTime = new Text("0:00");
		endTime.setStyle("-fx-font-family: 'Segoe UI'; -fx-font-size: 12; -fx-fill: #D0D0D2;");
		header.getChildren().add(endTime);
		AnchorPane.setTopAnchor(endTime, 32.0);
		AnchorPane.setRightAnchor(endTime, 100.0);
		
		//Header > Menu
		Button menuBtn = new Button();
		menuBtn.setMinSize(32, 32);
		menuBtn.setMaxSize(32, 32);
		header.getChildren().add(menuBtn);
		menuBtn.getStyleClass().add("menu_Button");
		AnchorPane.setTopAnchor(menuBtn, 9.0);
		AnchorPane.setRightAnchor(menuBtn, 10.0);
		AnchorPane.setBottomAnchor(menuBtn, 9.0);
		
		menuBtn.setOnAction((e) -> {
			ContentManager contentManager = OcrePlayer.contentManager;
			if(contentManager.getCurrentPane().getID().equalsIgnoreCase("Options")) {
				contentManager.changeContent("Player");
			} else contentManager.changeContent("Options");
		});
		
		OcrePlayer.contentManager.changeContent(OcrePlayer.contentManager.get("Player"));
	}

	private void registerContentPanes() {
		OcrePlayer.contentManager.register(new PlayerPane());
		OcrePlayer.contentManager.register(new OptionPane());
	}
	
	@Override
	public void hide() {
		
	}

	@Override
	public void init() {
		OcrePlayer.instance.stage.setMinWidth(800);
		OcrePlayer.instance.stage.setMinHeight(675);
		root = new AnchorPane();
		root.setPrefSize(1200, 675);
		root.setMinSize(1200, 675);
		scene = new Scene(root);
		scene.getStylesheets().add("resources/main.css");
		//scene.getStylesheets().add("https://fonts.googleapis.com/css?family=Ubuntu");
		
		root.getStyleClass().add("background");
	}

	@Override
	public void dispose() {
		
	}

	@Override
	public Scene getScene() {
		return scene;
	}	

}
