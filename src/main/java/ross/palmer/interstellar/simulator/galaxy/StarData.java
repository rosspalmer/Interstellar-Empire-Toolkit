package ross.palmer.interstellar.simulator.galaxy;

import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.geometry.Point3D;
import org.apache.commons.csv.CSVRecord;
import ross.palmer.interstellar.utilities.IdGenerator;

import java.util.Objects;

public class StarData {

    private long id;
    private final String name;
    private final String code;

    private final double x;
    private final double y;
    private final double z;
    private final Point3D coordinates;

    private final double map_x;
    private final double map_y;

    private final String spectFull;
    private final StarClass starClass;
    private final StarSequence starSequence;

    private final double magnitude;
//    private final double luminosity;
    private final double colorIndex;

    public StarData(CSVRecord csvRecord) {

        id = IdGenerator.getNextId("starId");
        name = csvRecord.get("name").equals("") ? null : csvRecord.get("name");
        code = csvRecord.get("code");

        x = new Double(csvRecord.get("3d_x"));
        y = new Double(csvRecord.get("3d_y"));
        z = new Double(csvRecord.get("3d_z"));
        coordinates = new Point3D(x, y, z);

        map_x = new Double(csvRecord.get("x"));
        map_y = new Double(csvRecord.get("y"));

        spectFull = csvRecord.get("spectrum");
        starClass = generateStarClass(csvRecord.get("class"));
        starSequence = generateStarSequence(csvRecord.get("type"));

        magnitude = new Double(csvRecord.get("absmag"));
//        luminosity = new Double(csvRecord.get("lum"));
        colorIndex = new Double(csvRecord.get("colorindex").equals("None") ? "0.0" : csvRecord.get("colorindex"));

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

    public String getCode() {
        return code;
    }

//    public String getConstellation() {
//        return constellation;
//    }

    public String getSpectFull() {
        return spectFull;
    }

    public double getMagnitude() {
        return magnitude;
    }

//    public double getLuminosity() {
//        return luminosity;
//    }

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

    private StarClass generateStarClass(String starClassString) {
        return StarClass.valueOf(starClassString);
    }

    private StarSequence generateStarSequence(String starSequenceString) {
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
