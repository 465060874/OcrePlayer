package at.domkog.ocreplayer.ui.content.importer;

import java.io.File;

import at.domkog.ocreplayer.OcrePlayer;
import at.domkog.ocreplayer.database.sqlite.PathMode;
import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.ui.content.IContent;
import at.domkog.ocreplayer.ui.content.importer.SynchronizePane.SyncMode;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

public class ImportPane extends VBox implements IContent {

	public ImportPane() {
		super(10);
		
		this.getStyleClass().add("importer");
		
		VBox.setVgrow(this, Priority.ALWAYS);
		this.setAlignment(Pos.CENTER);
		
		Text header = new Text("Import Mp3");
		header.getStyleClass().add("content_Header");
		
		Text description = new Text("Import Mp3 files from your computer to play it in OcrePlayer.");
		description.getStyleClass().add("content_Description");
		
		ListView<String> importList = new ListView<String>();
		
		importList.setMaxSize(750, 350);
		importList.getItems().addAll(OcrePlayer.library.folders);
		
		VBox.setMargin(importList, new Insets(0.0, 20.0, 0.0, 20.0));
		
		GridPane btnPane = new GridPane();
		
		btnPane.prefWidthProperty().bind(importList.widthProperty());
		btnPane.maxWidthProperty().bind(importList.widthProperty());
		btnPane.setHgap(30);
		
		Button add = new Button("Import");
		add.setMaxWidth(Double.MAX_VALUE);
		add.setOnAction((e) -> {
			DirectoryChooser dc = new DirectoryChooser();
			File initialPath = new File(PathMode.jarPath);
			if(!initialPath.isDirectory()) {
				initialPath = new File(PathMode.jarPath.substring(0, PathMode.jarPath.lastIndexOf("/")));
			}
			dc.setInitialDirectory(initialPath);
			
			File selected = dc.showDialog(OcrePlayer.instance.stage);
			
			if(selected != null) {
				if(!OcrePlayer.library.containsFolder(selected)) {
					String path = OcrePlayer.pathMode.createPath(selected);
					OcrePlayer.libraryDatabase.addFolder(path);
					OcrePlayer.library.folders.add(path);
					Platform.runLater(() -> importList.getItems().add(path));
				}
				
				OcrePlayer.contentManager.changeContent(new SynchronizePane(SyncMode.SINGLE, selected));
			}
		});
		
		Button remove = new Button("Remove");
		remove.setMaxWidth(Double.MAX_VALUE);
		remove.setOnAction((e) -> {
			if(!importList.getSelectionModel().isEmpty()) {
				File file = new File(OcrePlayer.pathMode.createPath(new File(importList.getSelectionModel().getSelectedItem())));
				if(!OcrePlayer.library.containsFolder(file)) return;
				OcrePlayer.libraryDatabase.removeFolder(file.getAbsolutePath());
				OcrePlayer.library.folders.remove(file.getAbsolutePath());
				Platform.runLater(() -> importList.getItems().remove(importList.getSelectionModel().getSelectedItem()));
				this.remove(file);
			}
		});
		
		Button sync = new Button("Synchronize");
		sync.setMaxWidth(Double.MAX_VALUE);
		
		sync.setOnAction((e) -> {
			OcrePlayer.contentManager.changeContent(new SynchronizePane(SyncMode.ALL, null));
		});
		
		btnPane.add(add, 0, 0);
		btnPane.add(remove, 1, 0);
		btnPane.add(sync, 2, 0);
		
		ColumnConstraints cc = new ColumnConstraints();
        cc.setHgrow(Priority.ALWAYS);
        btnPane.getColumnConstraints().add(cc);
        btnPane.getColumnConstraints().add(cc);
        btnPane.getColumnConstraints().add(cc);
		
		this.getChildren().add(header);
		this.getChildren().add(description);
		this.getChildren().add(importList);
		this.getChildren().add(btnPane);
	}
	
	public void remove(File file) {
		for(File f: file.listFiles()) {
			if(f.isDirectory()) remove(f);
			String path = OcrePlayer.pathMode.createPath(f);
			if(!OcrePlayer.library.contains(path)) continue;
			LibraryEntry entry = OcrePlayer.library.getFromPath(path);
			OcrePlayer.libraryDatabase.remove(entry);
			OcrePlayer.library.entries.remove(entry);
			OcrePlayer.mainUI.playerPane.reloadLibrary();
		}
	}
	
	@Override
	public String getID() {
		return "Importer";
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
