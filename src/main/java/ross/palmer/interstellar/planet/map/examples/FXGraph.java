package ross.palmer.interstellar.planet.map.examples;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import ross.palmer.interstellar.planet.map.voronoi.VoronoiGraph;
import ross.palmer.interstellar.planet.map.voronoi.groundshapes.HeightAlgorithm;
import ross.palmer.interstellar.planet.map.voronoi.groundshapes.Perlin;
import ross.palmer.interstellar.planet.map.voronoi.nodename.as3delaunay.Voronoi;

import java.util.List;
import java.util.Random;

public class FXGraph extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        VoronoiGraph graph = createVoronoiGraph(600, 30, 2);
        List<Polygon> polygons = graph.getPolygons();

        Group root = new Group();
        polygons.forEach(polygon -> root.getChildren().add(polygon));

        //Creating a scene object
        Scene scene = new Scene(root, 600, 600);

        //Setting title to the Stage
        primaryStage.setTitle("Drawing a Polygon");

        //Adding scene to the stage
        primaryStage.setScene(scene);

        //Displaying the contents of the stage
        primaryStage.show();
    }

    public static VoronoiGraph createVoronoiGraph(int bounds, int numSites, int numLloydRelaxations) {

        final Random r = new Random();

        HeightAlgorithm algorithm = new Perlin(r, 0, 520, 520);;

        //make the intial underlying voronoi structure
        final Voronoi v = new Voronoi(numSites, bounds, bounds, r, null);

        //assemble the voronoi strucutre into a usable graph object representing a map
        final TestGraphImpl graph = new TestGraphImpl(v, numLloydRelaxations, r, algorithm);

        return graph;

    }

    public static void main(String args[]){
        launch(args);
    }

}
