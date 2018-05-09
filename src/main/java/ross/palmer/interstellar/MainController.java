package ross.palmer.interstellar;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {

    public Pane mainPane;
    public VBox mainSelection;

    private Pane companyPane;
    private Pane galaxyPane;

    public MainController() throws IOException {
        companyPane = new Pane();
        galaxyPane = FXMLLoader.load(getClass().getResource("/galaxy.fxml"));
    }

    public void handleCompanySelection() {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(companyPane);
    }

    public void handleGalaxySelection() {
        mainPane.getChildren().clear();
        mainPane.getChildren().add(galaxyPane);
    }

    public void handleDataSelection() {
    }

    public void handleTradeSelection() {
    }

    public void handleSurveySelection() {
    }

    public void handleBuildSelection() {
    }

    public void handleExtractSelection() {
    }
}
