package ross.palmer.interstellar;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ross.palmer.interstellar.simulator.galaxy.Galaxy;

public class Main extends Application {

    private static final String applicationName = "Interstellar-Empire-Toolkit";

    private static Galaxy galaxy;

    public static Galaxy getGalaxy() {
        return galaxy;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

//        galaxy = new Galaxy();
//        CSVImports.stars(galaxy);

        primaryStage.setTitle("PhleidesDeveloperToolkit");
        Pane rootPane = FXMLLoader.load(getClass().getResource("/main.fxml"));
        Scene rootScene = new Scene(rootPane);
        primaryStage.setScene(rootScene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
