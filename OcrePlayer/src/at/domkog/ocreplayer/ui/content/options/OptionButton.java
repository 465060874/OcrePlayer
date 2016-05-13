package at.domkog.ocreplayer.ui.content.options;

import at.domkog.ocreplayer.OcrePlayer;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class OptionButton extends Button {

	private Image img;
	private ImageView imgView;

	private String contentID;
	
	public OptionButton(OptionPane pane, String img, String label, String contentID, boolean newInstance) {
		super();
		
		this.setAlignment(Pos.CENTER);
		this.setContentDisplay(ContentDisplay.CENTER);
		
		this.contentID = contentID;
		
		if(img != null && !img.equalsIgnoreCase("")) {
			this.img = new Image(OcrePlayer.class.getClassLoader().getResourceAsStream("resources/icons/" + img));
			this.imgView = new ImageView(this.img);
			
			this.setGraphic(this.imgView);
		}
		
		this.setMinSize(100, 100);
		this.setMaxSize(100, 100);
		
		this.getStyleClass().add("option_Button");

		DropShadow ds = new DropShadow();
        ds.setColor(Color.web("#B4B9C1"));
        ds.setSpread(0.5);
        ds.setWidth(40);
        ds.setHeight(40);
        
        this.setEffect(ds);
        
        this.setOnAction((e) -> {
        	if(newInstance)
				try {
					OcrePlayer.contentManager.changeContent(OcrePlayer.contentManager.get(contentID).getClass().newInstance());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			else OcrePlayer.contentManager.changeContent(this.contentID);
        });
        
        this.setOnMouseEntered((e) -> {
        	pane.headerTxt.setText(label);
        });
        
        this.setOnMouseExited((e) -> {
        	pane.headerTxt.setText("Menu");
        });
        
        this.setPickOnBounds(false);
	}
	
}
