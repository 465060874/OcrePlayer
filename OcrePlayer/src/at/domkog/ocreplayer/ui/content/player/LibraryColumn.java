package at.domkog.ocreplayer.ui.content.player;

import at.domkog.ocreplayer.libary.LibraryEntry;
import at.domkog.ocreplayer.libary.LibraryEntry.Attribute;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class LibraryColumn extends TableColumn<LibraryEntry, String> {

	public Attribute attribute;
	
	public LibraryColumn(Attribute attribute) {
		super(attribute.formatedName());
		this.attribute = attribute;
		
		this.setCellValueFactory(new Callback<CellDataFeatures<LibraryEntry, String>, ObservableValue<String>>() {
    		public ObservableValue<String> call(CellDataFeatures<LibraryEntry, String> p) {
    			if (p.getValue() != null) {
    	            return new SimpleStringProperty(p.getValue().getAttribute(attribute));
    	        } else {
    	            return new SimpleStringProperty("");
    	        }
    		}
    	});
	}
	
}
