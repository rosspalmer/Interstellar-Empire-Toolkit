package ross.palmer.interstellar.planet.map.voronoi.groundshapes;

import ross.palmer.interstellar.planet.map.geom.Point;
import ross.palmer.interstellar.planet.map.geom.Rectangle;

import java.util.Random;

/**
 * Use implementation of this interface to find out which points in graph are water and which - ground.
 */
public interface HeightAlgorithm {

    /**
     * Uses specific algorithm to check point.
     *
     * @param p Corner location.
     * @param bounds Graph bounds.
     * @param random Voronoi's randomizer to keep identical results for user's seed.
     * @return
     */
    boolean isWater(Point p, Rectangle bounds, Random random);

}