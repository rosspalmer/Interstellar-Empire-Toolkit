package ross.palmer.interstellar.dataIO;

import org.junit.Test;
import ross.palmer.interstellar.simulator.galaxy.Galaxy;

import java.io.IOException;

import static org.junit.Assert.*;

public class CSVImportsTest {

    @Test
    public void stars() {

        Galaxy galaxy = new Galaxy();
        try {
            CSVImports.stars(galaxy);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(77776, galaxy.getStellarSystems().size());
        assertEquals(4658227648L, (long) galaxy.getStellarSystems().stream()
                .mapToDouble(stellarSystem -> stellarSystem.getStarData().getId())
                .sum());

        assertEquals(133, galaxy.getStellarSystems().stream()
                .filter(stellarSystem -> stellarSystem.getStarData().getName().isPresent()).count());
//        assertEquals(2846, galaxy.getStellarSystems().stream()
//                .filter(stellarSystem -> stellarSystem.getStarData().getBfDesignation().isPresent()).count());

    }
}