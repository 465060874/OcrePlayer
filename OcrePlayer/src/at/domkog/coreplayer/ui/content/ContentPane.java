package at.domkog.coreplayer.ui.content;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class ContentPane extends VBox {

	public ContentPane() {
		super();
		this.getStyleClass().add("contentPane");
		
		DropShadow ds = new DropShadow();
        ds.setColor(Color.web("#B4B9C1"));
        ds.setSpread(0.6);
        ds.setWidth(40);
        ds.setHeight(40);
        
        this.setEffect(ds);
	}
	
}
