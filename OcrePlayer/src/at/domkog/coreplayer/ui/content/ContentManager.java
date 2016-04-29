package at.domkog.coreplayer.ui.content;

import javafx.scene.layout.Pane;
import lombok.Getter;

public class ContentManager {

	@Getter
	private ContentPane contentPane;
	
	private Pane currentPane;
	
	public ContentManager() {
		this.contentPane = new ContentPane();
	}
	
	public void changeContent(Pane pane) {
		removeContent();
		contentPane.getChildren().add(pane);
		this.currentPane = pane;
	}

 	public void removeContent() {
 		if(this.currentPane != null) contentPane.getChildren().remove(this.currentPane);
 	}
	
}
