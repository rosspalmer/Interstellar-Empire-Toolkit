package ross.palmer.interstellar.gui.scenes;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainPaneScene extends Pane {

    private Pane companyPane;
    private GalaxyPane galaxyPane;

    public MainPaneScene(Pane pane) throws IOException {
        super(pane);
        companyPane = new Pane();
        galaxyPane = new GalaxyPane();

    }

    public Pane getCompanyPane() {
        return companyPane;
    }

    public GalaxyPane getGalaxyPane() {
        return galaxyPane;
    }

}
