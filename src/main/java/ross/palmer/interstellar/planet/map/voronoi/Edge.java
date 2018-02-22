package ross.palmer.interstellar.planet.map.voronoi;

import ross.palmer.interstellar.planet.map.geom.Point;

import java.awt.geom.Arc2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Edge.java
 *
 * @author Connor
 */
public class Edge {

    public int index;
    public Center d0, d1;  // Delaunay edge
    public Corner v0, v1;  // Voronoi edge
    public Point midpoint;  // halfway between v0,v1
    public int river;

    public void setVornoi(Corner v0, Corner v1) {
        this.v0 = v0;
        this.v1 = v1;
        midpoint = new Point((v0.loc.x + v1.loc.x) / 2, (v0.loc.y + v1.loc.y) / 2);
    }

    public boolean equalsV0Coordinates(Corner corner) {
        return this.v0.equals(corner);
    }

    public boolean equalsV0Coordinates(double x, double y) {
        double resolution = 0.00001;
        return Math.abs(x - v0.loc.x) <= resolution && Math.abs(y - v0.loc.y) <= resolution;
    }

    public boolean equalsV1Coordinates(Object corner) {
        return this.v1.equals(corner);
    }

    public List<Double> getV0Coordinates() {
        List<Double> coordinates = new ArrayList<>(4);
        coordinates.add(v0.loc.x);
        coordinates.add(v0.loc.y);
        return coordinates;
    }

    public List<Double> getV1Coordinates() {
        List<Double> coordinates = new ArrayList<>(4);
        coordinates.add(v1.loc.x);
        coordinates.add(v1.loc.y);
        return coordinates;
    }

}
