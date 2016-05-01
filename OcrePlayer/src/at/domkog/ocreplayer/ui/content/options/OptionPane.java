package at.domkog.ocreplayer.ui.content.options;

import at.domkog.ocreplayer.ui.content.IContent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class OptionPane extends VBox implements IContent {

	public OptionPane() {
		super();
		
		FlowPane pane = new FlowPane ();
		
		pane.setAlignment(Pos.CENTER);
		
		pane.setVgap(20);
		pane.setHgap(20);
		pane.setPadding(new Insets(20, 20, 20, 20));
		
		pane.prefWidthProperty().bind(this.widthProperty());
		
		OptionButton mp3Btn = new OptionButton("mp3.png", "Import Mp3", "importMp3");
		pane.getChildren().add(mp3Btn);
		
		OptionButton stationBtn = new OptionButton("station.png", "Import station", "importStation");
		pane.getChildren().add(stationBtn);
		
		OptionButton webBtn = new OptionButton("web.png", "Web interface", "webInterface");
		pane.getChildren().add(webBtn);
		
		OptionButton settingsBtn = new OptionButton("settings.png", "Settings", "settings");
		pane.getChildren().add(settingsBtn);
		
		
		this.getChildren().add(pane);
		
		pane.prefWidthProperty().bind(this.widthProperty());
		pane.prefHeightProperty().bind(this.heightProperty());
		
		VBox.setVgrow(this, Priority.ALWAYS);
	}

	@Override
	public String getID() {
		return "Options";
	}

	@Override
	public Pane getPane() {
		return this;
	}
	
}
