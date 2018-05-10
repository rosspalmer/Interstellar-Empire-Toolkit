package ross.palmer.interstellar.gui.controllers;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ross.palmer.interstellar.gui.scenes.MainPaneScene;

import java.io.IOException;

public class MainController {

    private MainPaneScene mainPaneBuilder;

    public Pane viewPane;
    public VBox mainSelection;


    public MainController() throws IOException {
        viewPane = new Pane();
        mainPaneBuilder = new MainPaneScene(viewPane);
    }

    public void handleCompanySelection() {
        setViewPane(mainPaneBuilder.getCompanyPane());
    }

    public void handleGalaxySelection() {
        setViewPane(mainPaneBuilder.getGalaxyPane());
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

    private void setViewPane(Pane newPane) {
        viewPane.getChildren().clear();
        viewPane.getChildren().add(newPane);
    }

}
