package at.domkog.coreplayer.ui;

import javafx.scene.Scene;

public abstract class UI{

	public abstract String getID();
	
	public abstract void show();
	public abstract void hide();
	
	public abstract void init();
	public abstract void dispose();
	
	public abstract Scene getScene();
	
}
