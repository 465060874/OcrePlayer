package at.domkog.ocreplayer.ui.content.importer;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import at.domkog.ocreplayer.OcrePlayer;
import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;
import at.domkog.ocreplayer.ui.content.IContent;
import at.domkog.ocreplayer.utils.FileUtils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SynchronizePane extends VBox implements IContent {

	public ProgressBar progressbar;
	public Text time, current;
	
	private ArrayList<String> toSync;
	private long startTime;
	private String path;
	private int counter = 0;
	
	private Thread syncThread;
	
	private Timer timer;
	
	public enum SyncMode {
		SINGLE, ALL;
	}
	
	public SynchronizePane(SyncMode mode, File file) {
		super(30);
		
		VBox.setVgrow(this, Priority.ALWAYS);
		this.setAlignment(Pos.CENTER);
		
		Text header = new Text("Synchronizing...");
		header.getStyleClass().add("content_Header");
		
		HBox hWrap = new HBox();
		HBox.setHgrow(hWrap, Priority.ALWAYS);
		
		progressbar = new ProgressBar();
		progressbar.prefWidthProperty().bind(hWrap.widthProperty());
		progressbar.getStyleClass().add("sync_Bar");
		
		hWrap.getChildren().add(progressbar);
		
		VBox vbox = new VBox(3);
		
		time = new Text("0:00");
		time.getStyleClass().add("content_Text");
		time.wrappingWidthProperty().bind(progressbar.widthProperty());
		
		current = new Text("");
		current.getStyleClass().add("content_Text");
		current.wrappingWidthProperty().bind(progressbar.widthProperty());
		
		vbox.getChildren().add(time);
		vbox.getChildren().add(current);
		
		Button cancel = new Button("Cancel");
		cancel.getStyleClass().add("btnCancel");
		cancel.setOnAction((e) -> {
			if(syncThread != null) syncThread.interrupt();
			if(timer != null) timer.cancel();
			OcrePlayer.contentManager.changeContent(new ImportPane());
		});
		
		this.getChildren().add(header);
		this.getChildren().add(hWrap);
		this.getChildren().add(vbox);
		this.getChildren().add(cancel);
		
		VBox.setMargin(hWrap, new Insets(0.0, 40.0, 0.0, 40.0));
		VBox.setMargin(vbox, new Insets(0.0, 40.0, 0.0, 40.0));
		
		this.sync(mode, file);
	}
	
	public void sync(SyncMode mode, File file) {
		syncThread = new Thread(() -> {
			//Iterate all folders and get Mp3 files
			
			if(mode == SyncMode.SINGLE) {
				this.toSync = searchMp3(file);
			} else if(mode == SyncMode.ALL) {
				toSync = new ArrayList<String>();
				OcrePlayer.library.entries.forEach((entry) -> toSync.add(entry.getPath()));
			}
			
			startTime = System.currentTimeMillis();
			SynchronizePane.this.startTimer();
			
			for(String path: toSync) {
				counter++;
				LibraryEntry e;
				boolean exist = false;
				
				this.path = path;
				
				if(OcrePlayer.library.contains(path)) {
					e = OcrePlayer.library.getFromPath(path);
					exist = true;
				} else {
					e = new LibraryEntry();
					e.setAttribute(Attribute.PATH, path);
				}
				
				if(!e.syncTags()) continue;
				
				if(!exist) {
					OcrePlayer.libraryDatabase.add(e);
					OcrePlayer.library.entries.add(e);
				}
				else OcrePlayer.libraryDatabase.update(e);
			}
			
			SynchronizePane.this.endTimer();
			OcrePlayer.contentManager.changeContent(new ImportPane());
			OcrePlayer.mainUI.playerPane.reloadLibrary();
		}, "Sync");
		
		syncThread.start();
	}
	
	public void startTimer() {
		timer = new java.util.Timer();

		timer.schedule(new TimerTask() {
		    public void run() {
		         Platform.runLater(new Runnable() {
		            public void run() {
		            	long millis = System.currentTimeMillis() - startTime;
		            	long hour = TimeUnit.MILLISECONDS.toHours(millis);
		            	long minute = TimeUnit.MILLISECONDS.toMinutes(millis - hour * (1000 * 60 * 60));
						long second = TimeUnit.MILLISECONDS.toSeconds(millis  - minute * (1000 * 60));
						String time;
						if(hour > 0) time = String.format("%02d:%02d:%02d", hour, minute, second);
						else time = String.format("%02d:%02d", minute, second);

						Platform.runLater(new UpdateTask((double)counter / (double)toSync.size(), counter + "/" + toSync.size() + " - " + time, path));
		            }
		        });
		    }
		}, 0L, 10L);
	}
	
	public void endTimer() {
		timer.cancel();
	}
	
	public ArrayList<String> searchMp3(File file) {
		ArrayList<String> result = new ArrayList<>();
		for(File f: file.listFiles()) {
			if(f.isDirectory()) result.addAll(searchMp3(f));
			else if(FileUtils.isMp3(f)) result.add(f.getAbsolutePath());
		}
		return result;
	}
	
	@Override
	public String getID() {
		return "synchronize";
	}

	@Override
	public Pane getPane() {
		return this;
	}

	public class UpdateTask implements Runnable {

		private double value;
		private String time;
		private String path;
		
		public UpdateTask(double value, String time, String path) {
			this.value = value;
			this.time = time;
			this.path = path;
		}
		
		@Override
		public void run() {
			SynchronizePane.this.progressbar.setProgress(value);
			SynchronizePane.this.time.setText(time);
			SynchronizePane.this.current.setText(path);
		}
		
	}

	@Override
	public void dispose() {
		if(syncThread != null) syncThread.interrupt();
		if(timer != null) timer.cancel();
	}
	
}
