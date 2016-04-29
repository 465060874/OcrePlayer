package at.domkog.ocreplayer.ui.main;

import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

public class Header extends AnchorPane {

	public Header() {
		super();
		this.setMinWidth(1200);
		this.setMinHeight(50);
		this.setMaxHeight(50);
		this.getStyleClass().add("header");
		
		DropShadow ds = new DropShadow();
		ds.setColor(Color.web("#8B929B"));
        ds.setSpread(0.6);
        ds.setHeight(40);
        
        this.setEffect(ds);
	}
	
}
