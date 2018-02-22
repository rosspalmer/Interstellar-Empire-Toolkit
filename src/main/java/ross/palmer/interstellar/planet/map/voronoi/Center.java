package ross.palmer.interstellar.planet.map.voronoi;

import javafx.scene.shape.Polygon;
import ross.palmer.interstellar.planet.map.geom.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Center.java
 *
 * @author Connor
 */
public class Center {

    public int index;
    public Point loc;
    public ArrayList<Corner> corners = new ArrayList();//good
    public ArrayList<Center> neighbors = new ArrayList();//good
    public ArrayList<Edge> borders = new ArrayList();
    public boolean border, ocean, water, coast;
    public double elevation;
    public double moisture;
    public Enum biome;
    public double area;

    public Center() {
    }

    public Center(Point loc) {
        this.loc = loc;
    }

    public Polygon getPolygon() {
        Double[] coordinates = getCornerCoordinates();
        Polygon polygon = new Polygon();
        polygon.getPoints().addAll(coordinates);
        return polygon;
    }

    private Double[] getCornerCoordinates() {

        List<Edge> borderList = new ArrayList<>(borders);

        long firstCornerDEBUG = corners.stream()
                .filter(corner -> borders.stream()
                        .noneMatch(border -> border.v1 != null && border.equalsV1Coordinates(corner)))
                .count();

        Optional<Corner> firstCorner = corners.stream()
                .filter(corner -> borders.stream()
                        .noneMatch(border -> border.v1 != null && border.equalsV1Coordinates(corner)))
                .findFirst();

        if (!firstCorner.isPresent())
            throw new RuntimeException("WHHHHAAAAAA");

        List<Double> coordinates = new ArrayList<>();

        Edge nextBorder = borderList.stream()
                .filter(border -> border.equalsV0Coordinates(firstCorner.get().loc.x, firstCorner.get().loc.y))
                .findFirst().get();

        while (borderList.size() > 0) {

            double prevX = nextBorder.getV1Coordinates().get(0);
            double prevY = nextBorder.getV1Coordinates().get(1);

            nextBorder = borderList.stream()
                    .filter(border -> border.equalsV0Coordinates(prevX, prevY))
                    .findFirst().get();

            coordinates.addAll(nextBorder.getV1Coordinates());
            borderList.remove(nextBorder);
        }
        Double[] coordinateArray = new Double[coordinates.size()];
        return coordinates.toArray(coordinateArray);
    }

}
