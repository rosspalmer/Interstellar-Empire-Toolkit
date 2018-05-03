package ross.palmer.interstellar.gui;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import ross.palmer.interstellar.gui.explorer.SystemExplorer;

public class SimpleLayout extends GridPane {

    private VBox verticalSelectionRibbon;
    private Node mainNode;

    private Label home;
    private SystemExplorer systemExplorer;

    public SimpleLayout() {
        super();
        generateHome();
        generateVerticalSelectionRibbon();
        add(verticalSelectionRibbon, 0, 0);

        systemExplorer = new SystemExplorer();

    }

    public void generateHome() {
        home = new Label("WelcomeEE Home Space Travel mate");
        add(home, 1, 0);
    }

    public void generateVerticalSelectionRibbon() {
        verticalSelectionRibbon = new VBox();
        Button homeButton = new Button("Home");
        Button systemExplorerButton = new Button("System Explorer");
        systemExplorerButton.setOnAction(event -> {
            add(systemExplorer, 1, 0);
        });
        verticalSelectionRibbon.getChildren().addAll(homeButton, systemExplorerButton);
    }


}
