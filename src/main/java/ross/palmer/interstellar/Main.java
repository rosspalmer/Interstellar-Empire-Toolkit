package ross.palmer.interstellar;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ross.palmer.interstellar.dataIO.CSVImports;
import ross.palmer.interstellar.gui.SimpleLayout;
import ross.palmer.interstellar.gui.explorer.StarTableView;
import ross.palmer.interstellar.simulator.galaxy.Galaxy;

public class Main extends Application {

    private static final String applicationName = "Interstellar-Empire-Toolkit";

    private static Galaxy galaxy;

    public static Galaxy getGalaxy() {
        return galaxy;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        galaxy = new Galaxy();
        CSVImports.stars(galaxy);

        SimpleLayout layout = new SimpleLayout();

        Scene scene = new Scene(layout, 1500, 1000);
        primaryStage.setTitle(applicationName);
        primaryStage.setScene(scene);

        StarTableView table = new StarTableView();

        primaryStage.show();
    }


}
