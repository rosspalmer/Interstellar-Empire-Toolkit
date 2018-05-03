package ross.palmer.interstellar.simulator.galaxy;

import javafx.geometry.Point3D;
import org.apache.commons.csv.CSVRecord;
import ross.palmer.interstellar.gui.explorer.SystemExplorer;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StarData {

    private final long id;
    private String name;
    private final String bayer; // Bayer designation
    private final String flamsteed; // Flamsteed designation
    private final long primaryStarId;
    private final int starsInSystem;

    private final double x;
    private final double y;
    private final double z;
    private final Point3D coordinates;
    private final String constellation;

    private final String spectFull;
    private final StarClass starClass;
    private final StarSequence starSequence;

    private final double magnitude;
    private final double luminosity;
//    private final double colorIndex;

    private final boolean complete;

    public StarData(CSVRecord csvRecord) {

        id = new Long(csvRecord.get("id"));
        name = csvRecord.get("proper").equals("") ? null : csvRecord.get("proper");
        bayer = csvRecord.get("bayer").equals("") ? null : csvRecord.get("bayer");
        flamsteed = csvRecord.get("flam").equals("") ? null : csvRecord.get("flam");
        primaryStarId = new Long(csvRecord.get("comp_primary"));
        starsInSystem = new Integer(csvRecord.get("comp"));

        x = new Double(csvRecord.get("x"));
        y = new Double(csvRecord.get("y"));
        z = new Double(csvRecord.get("z"));
        coordinates = new Point3D(x, y, z);
        constellation = csvRecord.get("con");

        spectFull = csvRecord.get("spect");
        starClass = generateStarClass();
        starSequence = generateStarSequence();

        magnitude = new Double(csvRecord.get("absmag"));
        luminosity = new Double(csvRecord.get("lum"));
//        colorIndex = new Double(csvRecord.get("ci"));

        complete = starClass != null && starSequence != null;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }



    public Point3D getCoordinates() {
        return coordinates;
    }

    public long getPrimaryStarId() {
        return primaryStarId;
    }

    public int getStarsInSystem() {
        return starsInSystem;
    }

    public String getConstellation() {
        return constellation;
    }

    public String getSpectFull() {
        return spectFull;
    }

    public double getMagnitude() {
        return magnitude;
    }

    public double getLuminosity() {
        return luminosity;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public StarClass getStarClass() {
        return starClass;
    }

    public StarSequence getStarSequence() {
        return starSequence;
    }

    public boolean isComplete() {
        return complete;
    }

    private StarClass generateStarClass() {

        Pattern pattern = Pattern.compile("^[OABFGKM]");
        Matcher matcher = pattern.matcher(spectFull);

        if (!matcher.find())
            return null;

        String starClassString = spectFull.substring(matcher.start(), matcher.end());
        if (matcher.start() == 0 && matcher.end() == 0)
            return null;

        return StarClass.valueOf(starClassString);

    }

    private StarSequence generateStarSequence() {

        Pattern pattern = Pattern.compile("[VI]+");
        Matcher matcher = pattern.matcher(spectFull);

        if (!matcher.find())
            return null;

        String starSequenceString = spectFull.substring(matcher.start(), matcher.end());
        if (matcher.start() == 0 && matcher.end() == 0)
            return null;

        StarSequence starSequence = null;
        for (StarSequence sequenceEnum : StarSequence.values()) {
            if (sequenceEnum.stringValue.equals(starSequenceString)) {
                starSequence = sequenceEnum;
                break;
            }
        }
        return starSequence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StarData starData = (StarData) o;
        return id == starData.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public enum StarClass {

        O ("O"),
        B ("B"),
        A ("A"),
        F ("F"),
        G ("G"),
        K ("K"),
        M ("M");

        StarClass(String stringValue) {
            this.stringValue = stringValue;
        }

        private String stringValue;

    }

    public enum StarSequence {

        SUPER_GIANT ("I"),
        BRIGHT_GIANT ("II"),
        GIANT ("III"),
        SUB_GIANT ("IV"),
        MAIN ("V");

        StarSequence(String stringValue) {
            this.stringValue = stringValue;
        }

        private String stringValue;
    }

}
