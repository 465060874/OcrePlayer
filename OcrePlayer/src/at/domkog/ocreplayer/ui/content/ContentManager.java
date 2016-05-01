package at.domkog.ocreplayer.ui.content;

import java.util.HashMap;

import lombok.Getter;

public class ContentManager {

	@Getter
	private ContentPane contentPane;
	
	@Getter
	private IContent currentPane;
	
	private HashMap<String, IContent> contents = new HashMap<String, IContent>();
	
	public ContentManager() {
		this.contentPane = new ContentPane();
	}
	
	public void changeContent(IContent content) {
		removeContent();
		contentPane.getChildren().add(content.getPane());
		this.currentPane = content;
	}
	
	public void changeContent(String ID) {
		this.changeContent(this.get(ID));
	}

 	public void removeContent() {
 		if(this.currentPane != null) {
 			contentPane.getChildren().remove(this.currentPane.getPane());
 		}
 	}
 	
 	public void register(IContent content) {
 		contents.put(content.getID(), content);
 	}
 	
 	public IContent get(String ID) {
 		return contents.get(ID);
 	}
	
}
