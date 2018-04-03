package ross.palmer.interstellar.simulator.galaxy;

import org.apache.commons.csv.CSVRecord;
import ross.palmer.interstellar.simulator.datanet.DataNode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class StellarSystem {

    private final long id;
    private final StarData starData;
    private final DataNode dataNode;
    private Set<StarData> starDataSet;
    private int starsInSystem;

    public StellarSystem(CSVRecord csvRecord) {
        starData = new StarData(csvRecord);
        id = starData.getId();
        dataNode = new DataNode();
        starDataSet = new HashSet<>();
    }

    public void addStarData(StarData starData) {
        starDataSet.add(starData);
        starsInSystem++;
    }

    public double calculateDistance(StellarSystem otherStellarSystem) {
        return starData.getCoordinates().distance(otherStellarSystem.getStarData().getCoordinates());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StellarSystem that = (StellarSystem) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public StarData getStarData() {
        return starData;
    }
}