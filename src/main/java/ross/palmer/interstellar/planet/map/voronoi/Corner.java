package ross.palmer.interstellar.planet.map.voronoi;

import ross.palmer.interstellar.planet.map.geom.Point;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Corner.java
 *
 * @author Connor
 */
public class Corner {

    public ArrayList<Center> touches = new ArrayList(); //good
    public ArrayList<Corner> adjacent = new ArrayList(); //good
    public ArrayList<Edge> protrudes = new ArrayList();
    public Point loc;
    public int index;
    public boolean border;
    public double elevation;
    public boolean water, ocean, coast;
    public Corner downslope;
    public int river;
    public double moisture;

    public List<Double> getXYCoordinates() {
        List<Double> coordinates = new ArrayList<>();
        coordinates.add(loc.x);
        coordinates.add(loc.y);
        return coordinates;
    }

}
