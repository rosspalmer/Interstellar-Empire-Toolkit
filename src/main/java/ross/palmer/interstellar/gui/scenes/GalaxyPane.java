package ross.palmer.interstellar.gui.scenes;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class GalaxyPane extends MainSubPane {

    public GalaxyPane() {

        super();

        getStylesheets().add(getClass().getResource("/css/galaxyStyle.css").toExternalForm());

        addView("System Browser", new Pane());
        addView("Galaxy Map", new Pane());

    }



}
