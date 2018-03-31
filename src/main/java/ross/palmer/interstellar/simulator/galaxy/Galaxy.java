package ross.palmer.interstellar.simulator.galaxy;

import java.util.HashSet;
import java.util.Set;

public class Galaxy {

    private Set<StellarSystem> stellarSystems;

    public Galaxy() {
        stellarSystems = new HashSet<>();
    }

    public void addStellarSystem(StellarSystem stellarSystem) {
        stellarSystems.add(stellarSystem);
    }

    public Set<StellarSystem> getStellarSystems() {
        return stellarSystems;
    }
}
