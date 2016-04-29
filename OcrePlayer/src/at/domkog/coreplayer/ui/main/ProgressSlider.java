package at.domkog.coreplayer.ui.main;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.layout.StackPane;

public class ProgressSlider extends StackPane {

	public ProgressBar progressBar;
	public Slider slider;
	
	public ProgressSlider(int minWidth, int minHeight, int maxWidth, int maxHeight) {
		progressBar = new ProgressBar();
		progressBar.setProgress(0D);
		slider = new Slider();
		slider.setValue(1);
		slider.setValue(0);
		
		if(minWidth != -1) this.setMinWidth(minWidth);
		if(maxWidth != -1) this.setMaxWidth(maxWidth);
		if(minHeight != -1) this.setMinHeight(minHeight);
		if(maxHeight != -1) this.setMaxHeight(maxHeight);
		
		progressBar.prefWidthProperty().bind(this.widthProperty());
		slider.prefWidthProperty().bind(this.widthProperty());
		slider.prefHeightProperty().bind(this.heightProperty());
		
		this.heightProperty().addListener((observeable, oldValue, newValue) -> {
			progressBar.prefHeightProperty().set(newValue.doubleValue() / 5);
		});
		
		slider.valueProperty().addListener((observeable, oldValue, newValue) -> {
			double progress = newValue.doubleValue() / slider.getMax();
			progressBar.setProgress(progress);
		});
		
		this.getChildren().add(progressBar);
		this.getChildren().add(slider);
		
		progressBar.getStyleClass().add("slider_Progress");
		slider.getStyleClass().add("slider_Slider");
	}
	
	public void setMinMax(double min, double max) {
		this.slider.setMin(min);
		this.slider.setMax(max);
	}
	
}
