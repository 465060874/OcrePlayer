package at.domkog.coreplayer.ui.main;

import at.domkog.coreplayer.ui.UI;
import at.domkog.coreplayer.ui.content.ContentManager;
import at.domkog.coreplayer.ui.content.ContentPane;
import at.domkog.ocreplayer.OcrePlayer;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;

public class MainUI extends UI {

	private Scene scene;
	private AnchorPane root;
	
	public ProgressSlider timeline, volume;
	
	@Override
	public String getID() {
		return "main";
	}

	@Override
	public void show() {
		Header header = new Header();
		root.getChildren().add(header);
		AnchorPane.setTopAnchor(header, 0.0);
		AnchorPane.setLeftAnchor(header, 0.0);
		AnchorPane.setRightAnchor(header, 0.0);
		
		Sidebar sidebar = new Sidebar();
		root.getChildren().add(sidebar);
		AnchorPane.setTopAnchor(sidebar, 50.0);
		AnchorPane.setLeftAnchor(sidebar, 0.0);
		AnchorPane.setBottomAnchor(sidebar, 0.0);
		
		OcrePlayer.contentManager = new ContentManager();
		ContentPane contentPane = OcrePlayer.contentManager.getContentPane();
		root.getChildren().add(contentPane);
		AnchorPane.setTopAnchor(contentPane, 100.0);
		AnchorPane.setLeftAnchor(contentPane, 250.0);
		AnchorPane.setRightAnchor(contentPane, 50.0);
		AnchorPane.setBottomAnchor(contentPane, 50.0);
		
		timeline = new ProgressSlider(-1, 4, 4, -1);
		header.getChildren().add(timeline);
		AnchorPane.setTopAnchor(timeline, 13.0);
		AnchorPane.setBottomAnchor(timeline, 13.0);
		AnchorPane.setRightAnchor(timeline, 100.0);
		AnchorPane.setLeftAnchor(timeline, 325.0);
		
		volume = new ProgressSlider(100, 4, 4, 100);
		header.getChildren().add(volume);
		AnchorPane.setTopAnchor(volume, 13.0);
		AnchorPane.setBottomAnchor(volume, 13.0);
		AnchorPane.setLeftAnchor(volume, 200.0);
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void init() {
		OcrePlayer.instance.stage.setMinWidth(1200);
		OcrePlayer.instance.stage.setMinHeight(675);
		root = new AnchorPane();
		root.setPrefSize(1200, 675);
		root.setMinSize(1200, 675);
		scene = new Scene(root);
		scene.getStylesheets().add("resources/main.css");
		
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
