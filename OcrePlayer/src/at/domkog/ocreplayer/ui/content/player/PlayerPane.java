package at.domkog.ocreplayer.ui.content.player;

import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;
import at.domkog.ocreplayer.ui.content.IContent;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PlayerPane extends VBox implements IContent {

	@SuppressWarnings("unchecked")
	public PlayerPane() {
		super();
		
		this.getStyleClass().add("contentPane");
		
		VBox.setVgrow(this, Priority.ALWAYS);
		HBox hBox = new HBox();
		hBox.getStyleClass().add("contentPane");
		HBox.setHgrow(hBox, Priority.ALWAYS);
		
		TableView<LibraryEntry> table = new TableView<LibraryEntry>();

		table.prefWidthProperty().bind(this.widthProperty());
		table.prefHeightProperty().bind(this.heightProperty());
		
		LibraryColumn title = new LibraryColumn(Attribute.TITLE);
		LibraryColumn artist = new LibraryColumn(Attribute.ARTIST);
		LibraryColumn length = new LibraryColumn(Attribute.LENGTH);
		LibraryColumn album = new LibraryColumn(Attribute.ALBUM);
		LibraryColumn genre = new LibraryColumn(Attribute.GENRE);

		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		
		table.getColumns().addAll(title, artist, length, album, genre);
		
		table.getStyleClass().add("player_Table");
		
		hBox.getChildren().add(table);
		this.getChildren().add(hBox);
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
