package at.domkog.ocreplayer.ui.content.player;

import at.domkog.ocreplayer.ui.content.IContent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class PlayerPane extends VBox implements IContent {

	public PlayerPane() {
		super();
	}
	
	@Override
	public String getID() {
		return "Player";
	}

	@Override
	public Pane getPane() {
		return this;
	}
	
}
