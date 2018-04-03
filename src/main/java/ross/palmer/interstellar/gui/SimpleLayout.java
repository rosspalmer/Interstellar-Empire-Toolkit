package ross.palmer.interstellar.gui;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class SimpleLayout extends GridPane {

    private VBox verticalSelectionRibbon;

    public SimpleLayout() {
        super();
        generateVerticalSelectionRibbon();
        generateStarTable();
    }

    public void generateVerticalSelectionRibbon() {
        verticalSelectionRibbon = new VBox();
        Button galaxyBrowser = new Button("Galaxy Browser");
        Button otherOptions = new Button("Otherz");
        verticalSelectionRibbon.getChildren().addAll(galaxyBrowser, otherOptions);
        add(verticalSelectionRibbon, 0, 0);

    }

    public void generateStarTable() {
        StarTableView table = new StarTableView();
        add(table, 1, 0);
    }

}
