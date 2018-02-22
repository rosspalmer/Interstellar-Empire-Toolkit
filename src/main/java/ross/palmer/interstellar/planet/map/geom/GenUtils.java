package ross.palmer.interstellar.planet.map.geom;

/**
 * GenUtil.java
 *
 * @author Connor
 */
public class GenUtils {

    public static boolean closeEnough(double d1, double d2, double diff) {
        return Math.abs(d1 - d2) <= diff;
    }
}
