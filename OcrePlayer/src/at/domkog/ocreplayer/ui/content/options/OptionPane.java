package at.domkog.ocreplayer.ui.content.options;

import at.domkog.ocreplayer.ui.content.IContent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class OptionPane extends VBox implements IContent {

	public Text headerTxt;
	
	public OptionPane() {
		super();
		
		HBox header = new HBox();
		header.setAlignment(Pos.CENTER);

		headerTxt = new Text("Menu");
		headerTxt.getStyleClass().add("option_Text");
		HBox.setMargin(headerTxt, new Insets(10, 0, 0 ,0));
		
		header.getChildren().add(headerTxt);
		
		this.getChildren().add(header);
		
		FlowPane pane = new FlowPane ();
		
		pane.setAlignment(Pos.CENTER);
		
		pane.setVgap(20);
		pane.setHgap(20);
		pane.setPadding(new Insets(20, 20, 20, 20));
		
		pane.prefWidthProperty().bind(this.widthProperty());
		
		OptionButton mp3Btn = new OptionButton(this, "mp3.png", "Import Mp3", "Importer", true);
		pane.getChildren().add(mp3Btn);
		
		OptionButton stationBtn = new OptionButton(this, "station.png", "Import station", "importStation", false);
		pane.getChildren().add(stationBtn);
		
		OptionButton webBtn = new OptionButton(this, "web.png", "Web interface", "webInterface", false);
		pane.getChildren().add(webBtn);
		
		OptionButton settingsBtn = new OptionButton(this, "settings.png", "Settings", "settings", false);
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

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
}
