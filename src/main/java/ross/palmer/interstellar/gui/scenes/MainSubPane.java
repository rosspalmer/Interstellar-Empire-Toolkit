package ross.palmer.interstellar.gui.scenes;

import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.HashMap;
import java.util.Map;

public abstract class MainSubPane extends Pane {

    private HBox buttonBar;
    private AnchorPane currentViewPane;

    private Map<String, Pane> viewPanes;

    public MainSubPane() {

        super();

        AnchorPane.setBottomAnchor(this, 0.0);
        AnchorPane.setTopAnchor(this, 0.0);
        AnchorPane.setLeftAnchor(this, 0.0);
        AnchorPane.setRightAnchor(this, 0.0);

        buttonBar = new HBox();
        buttonBar.setId("buttonBar");

        currentViewPane = new AnchorPane();
        currentViewPane.setId("viewPane");
        currentViewPane.setMaxHeight(Double.MAX_VALUE);
        currentViewPane.setMinHeight(Double.MAX_VALUE);

        getChildren().addAll(buttonBar, currentViewPane);

        viewPanes = new HashMap<>();
    }

    public void addView(String buttonText, Pane subViewPane) {

        AnchorPane.setLeftAnchor(subViewPane, 0.0);
        AnchorPane.setRightAnchor(subViewPane, 0.0);
        AnchorPane.setTopAnchor(subViewPane, 0.0);
        AnchorPane.setBottomAnchor(subViewPane, 0.0);

        viewPanes.put(buttonText, subViewPane);
        Button button = new Button(buttonText);
        button.setOnAction(event -> setView(buttonText));
        buttonBar.getChildren().add(button);

    }

    private void setView(String buttonText) {

        Pane viewPane = viewPanes.get(buttonText);

        currentViewPane.getChildren().clear();
        currentViewPane.getChildren().add(viewPane);
    }

}
