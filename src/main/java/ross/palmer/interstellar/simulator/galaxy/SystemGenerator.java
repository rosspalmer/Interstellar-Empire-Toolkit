package ross.palmer.interstellar.simulator.galaxy;

import ross.palmer.interstellar.utilities.RandomSelector;

import java.util.*;

public class SystemGenerator {

    private RandomSelector<Planet.PlanetType> planetTypeRandomSelector;
    private Map<StarData.StarClass, List<SystemZone>> systemZoneLibrary;
    private Map<StarData.StarClass, StarZoneRange> starZoneRanges;

    private Random random;

    public SystemGenerator() {
        random = new Random();
        generateProbabilityValues();
    }

    public void run(Galaxy galaxy) {

        generateProbabilityValues();

        for (StellarSystem stellarSystem : galaxy.getStellarSystems()) {

            StarData.StarClass starClass = stellarSystem.getStarData().getStarClass();

            StarZoneRange range = starZoneRanges.get(starClass);
            if (range != null) {

                int zoneCount = random.nextInt(range.getMaxRoll()) + range.getAddConstant();
                int planetCount = 0;

                for (int i = 0; i < zoneCount; i++) {

                    SystemZone systemZone = systemZoneLibrary.get(starClass).get(i);
                    Planet.PlanetType planetType = planetTypeRandomSelector.randomPick();

                    if (planetType != null) {

                        Planet planet = generatePlanet(planetType, systemZone);
                        planet.setPlanetNumber(++planetCount);

                        stellarSystem.addPlanet(planet);

                    }
                }
            }
        }

    }

    private Planet generatePlanet(Planet.PlanetType planetType, SystemZone systemZone) {

        Planet newPlanet = new Planet();
        newPlanet.setPlanetType(planetType);

        double orbitalRadius = Math.random() * systemZone.getMaxOrbit() + systemZone.getMinOrbit();
        newPlanet.setOrbitalRadius(orbitalRadius);

        newPlanet.setTemperature(systemZone.getTemperature());

        if (planetType == Planet.PlanetType.ICE_GIANT) {
            setIceGiantDimensions(newPlanet);
        } else if (planetType == Planet.PlanetType.GAS_GIANT) {
            setGasGiantDimensions(newPlanet);
        } else if (planetType == Planet.PlanetType.DWARF) {
            setDwarfDimensions(newPlanet);
        } else if (planetType == Planet.PlanetType.TERRAN) {
            setTerranDimensions(newPlanet);
        }

        return newPlanet;

    }

    private void setIceGiantDimensions(Planet planet) {

        planet.setGravity(Math.random() * 5);
        planet.setRadius((Math.random() * 10 + 12) / 4);
        planet.setMass(planet.getGravity() * planet.getRadius() * planet.getRadius());

    }

    private void setGasGiantDimensions(Planet planet) {

        planet.setGravity(Math.random() * 5);
        planet.setRadius((Math.random() * 10 + 15) / 2);
        planet.setMass(planet.getGravity() * planet.getRadius() * planet.getRadius());

    }

    private void setDwarfDimensions(Planet planet) {

        planet.setGravity(Math.random() * 10 / 3);
        planet.setRadius((Math.random() * 4 + 6) * 0.01);
        planet.setMass(planet.getGravity() * planet.getRadius() * planet.getRadius());

    }

    private void setTerranDimensions(Planet planet) {

        planet.setGravity((Math.random() * 8 + 2) / 10);
        planet.setRadius((Math.random() * 4 + 6) * 0.01);
        planet.setMass(planet.getGravity() * planet.getRadius() * planet.getRadius());

    }

    private void generateProbabilityValues() {
        generatePlanetTypeSelectors();
        generateSystemZones();
        generateStarZoneRanges();
    }

    private void generatePlanetTypeSelectors() {

        planetTypeRandomSelector = new RandomSelector<>();
        planetTypeRandomSelector.addSelection(null, 11);
        planetTypeRandomSelector.addSelection(Planet.PlanetType.ASTEROIDS, 11);
        planetTypeRandomSelector.addSelection(Planet.PlanetType.DWARF, 17);
        planetTypeRandomSelector.addSelection(Planet.PlanetType.TERRAN, 27);
        planetTypeRandomSelector.addSelection(Planet.PlanetType.GAS_GIANT, 11.5);
        planetTypeRandomSelector.addSelection(Planet.PlanetType.ICE_GIANT, 11.5);

    }

    private void generateSystemZones() {

        systemZoneLibrary = new HashMap<>();

        List<SystemZone> starBZones = new ArrayList<>();

        starBZones.add(new SystemZone(1, 10, 14.9, Planet.PlanetTemperature.HOT));
        starBZones.add(new SystemZone(2, 15, 22.4, Planet.PlanetTemperature.HOT));
        starBZones.add(new SystemZone(3, 22.5, 33.7, Planet.PlanetTemperature.HOT));
        starBZones.add(new SystemZone(4, 33.8, 50.5, Planet.PlanetTemperature.HOT));

        starBZones.add(new SystemZone(5, 50.6, 75.8, Planet.PlanetTemperature.WARM));
        starBZones.add(new SystemZone(6, 75.9, 113.8, Planet.PlanetTemperature.WARM));
        starBZones.add(new SystemZone(7, 113.9, 170.8, Planet.PlanetTemperature.WARM));
        starBZones.add(new SystemZone(8, 170.9, 256.2, Planet.PlanetTemperature.WARM));

        starBZones.add(new SystemZone(9, 256.3, 384.3, Planet.PlanetTemperature.HABITABLE));

        starBZones.add(new SystemZone(10, 384.4, 576.6, Planet.PlanetTemperature.COOL));
        starBZones.add(new SystemZone(11, 576.7, 864.9, Planet.PlanetTemperature.COOL));
        starBZones.add(new SystemZone(12, 865, 1297.4, Planet.PlanetTemperature.COOL));

        starBZones.add(new SystemZone(13, 1297.5, 1949.1, Planet.PlanetTemperature.COLD));
        starBZones.add(new SystemZone(14, 1949.2, 2919.2, Planet.PlanetTemperature.COLD));

        systemZoneLibrary.put(StarData.StarClass.B, starBZones);

        List<SystemZone> starAZones = new ArrayList<>();

        starAZones.add(new SystemZone(1, 0.7, 1.3, Planet.PlanetTemperature.HOT));
        starAZones.add(new SystemZone(2, 1.4, 2.7, Planet.PlanetTemperature.HOT));
        starAZones.add(new SystemZone(3, 2.8, 5.5, Planet.PlanetTemperature.HOT));

        starAZones.add(new SystemZone(4, 5.6, 11.1, Planet.PlanetTemperature.WARM));
        starAZones.add(new SystemZone(5, 11.2, 22.3, Planet.PlanetTemperature.WARM));
        starAZones.add(new SystemZone(6, 22.4, 44.7, Planet.PlanetTemperature.WARM));

        starAZones.add(new SystemZone(7, 44.8, 89.5, Planet.PlanetTemperature.HABITABLE));

        starAZones.add(new SystemZone(8, 89.6, 179.1, Planet.PlanetTemperature.COOL));
        starAZones.add(new SystemZone(9, 179.2, 358.3, Planet.PlanetTemperature.COOL));
        starAZones.add(new SystemZone(10, 358.4, 716.7, Planet.PlanetTemperature.COOL));

        starAZones.add(new SystemZone(11, 716.8, 1433.5, Planet.PlanetTemperature.COLD));
        starAZones.add(new SystemZone(12, 1433.6, 2867.1, Planet.PlanetTemperature.COLD));

        systemZoneLibrary.put(StarData.StarClass.A, starAZones);

        List<SystemZone> starFZones = new ArrayList<>();

        starFZones.add(new SystemZone(1, 0.2, 0.3, Planet.PlanetTemperature.HOT));
        starFZones.add(new SystemZone(2, 0.4, 0.5, Planet.PlanetTemperature.HOT));

        starFZones.add(new SystemZone(3, 0.6, 1.0, Planet.PlanetTemperature.WARM));
        starFZones.add(new SystemZone(4, 1.1, 1.8, Planet.PlanetTemperature.WARM));

        starFZones.add(new SystemZone(5, 1.9, 3.2, Planet.PlanetTemperature.HABITABLE));

        starFZones.add(new SystemZone(6, 3.3, 5.6, Planet.PlanetTemperature.COOL));
        starFZones.add(new SystemZone(7, 5.7, 10, Planet.PlanetTemperature.COOL));
        starFZones.add(new SystemZone(8, 10.1, 17.5, Planet.PlanetTemperature.COOL));

        starFZones.add(new SystemZone(9, 17.6, 30.7, Planet.PlanetTemperature.COLD));
        starFZones.add(new SystemZone(10, 30.8, 53.8, Planet.PlanetTemperature.COLD));
        starFZones.add(new SystemZone(11, 53.9, 94.2, Planet.PlanetTemperature.COLD));
        starFZones.add(new SystemZone(12, 94.3, 164.9, Planet.PlanetTemperature.COLD));

        systemZoneLibrary.put(StarData.StarClass.F, starFZones);

        List<SystemZone> starGZones = new ArrayList<>();

        starGZones.add(new SystemZone(1, 0.4, 0.5, Planet.PlanetTemperature.HOT));

        starGZones.add(new SystemZone(2, 0.6, 0.8, Planet.PlanetTemperature.WARM));

        starGZones.add(new SystemZone(3, 0.9, 1.3, Planet.PlanetTemperature.HABITABLE));

        starGZones.add(new SystemZone(4, 1.4, 1.9, Planet.PlanetTemperature.COOL));
        starGZones.add(new SystemZone(5, 2, 2.9, Planet.PlanetTemperature.COOL));
        starGZones.add(new SystemZone(6, 3, 4.5, Planet.PlanetTemperature.COOL));
        starGZones.add(new SystemZone(7, 4.6, 6.7, Planet.PlanetTemperature.COOL));

        starGZones.add(new SystemZone(8, 6.8, 10.2, Planet.PlanetTemperature.COLD));
        starGZones.add(new SystemZone(9, 10.3, 15.3, Planet.PlanetTemperature.COLD));
        starGZones.add(new SystemZone(10, 15.4, 23, Planet.PlanetTemperature.COLD));
        starGZones.add(new SystemZone(11, 23.1, 34.5, Planet.PlanetTemperature.COLD));
        starGZones.add(new SystemZone(12, 34.6, 51.8, Planet.PlanetTemperature.COLD));

        systemZoneLibrary.put(StarData.StarClass.G, starGZones);

        List<SystemZone> starKZones = new ArrayList<>();

        starKZones.add(new SystemZone(1, 0.2, 0.3, Planet.PlanetTemperature.WARM));

        starKZones.add(new SystemZone(2, 0.4, 0.7, Planet.PlanetTemperature.HABITABLE));

        starKZones.add(new SystemZone(3, 0.8, 1.5, Planet.PlanetTemperature.COOL));
        starKZones.add(new SystemZone(4, 1.6, 3.1, Planet.PlanetTemperature.COOL));
        starKZones.add(new SystemZone(5, 3.2, 6.3, Planet.PlanetTemperature.COOL));

        starKZones.add(new SystemZone(6, 6.4, 12.7, Planet.PlanetTemperature.COLD));
        starKZones.add(new SystemZone(7, 12.8, 25.5, Planet.PlanetTemperature.COLD));

        systemZoneLibrary.put(StarData.StarClass.K, starKZones);

        List<SystemZone> starMZones = new ArrayList<>();

        starMZones.add(new SystemZone(1, 0.2, 0.3, Planet.PlanetTemperature.HABITABLE));

        starMZones.add(new SystemZone(2, 0.4, 0.7, Planet.PlanetTemperature.COOL));
        starMZones.add(new SystemZone(3, 0.8, 1.5, Planet.PlanetTemperature.COOL));

        starMZones.add(new SystemZone(4, 1.6, 3.1, Planet.PlanetTemperature.COLD));
        starMZones.add(new SystemZone(5, 3.2, 6.3, Planet.PlanetTemperature.COLD));

        systemZoneLibrary.put(StarData.StarClass.M, starMZones);

    }

    private void generateStarZoneRanges() {

        starZoneRanges = new HashMap<>();

        starZoneRanges.put(StarData.StarClass.B, new StarZoneRange(10, 4));
        starZoneRanges.put(StarData.StarClass.A, new StarZoneRange(10, 2));
        starZoneRanges.put(StarData.StarClass.F, new StarZoneRange(10, 2));
        starZoneRanges.put(StarData.StarClass.G, new StarZoneRange(10, 2));
        starZoneRanges.put(StarData.StarClass.K, new StarZoneRange(5, 2));
        starZoneRanges.put(StarData.StarClass.M, new StarZoneRange(5, 0));

    }

    private class SystemZone {

        private final int zoneNumber;
        private final double minOrbit;
        private final double maxOrbit;
        private final Planet.PlanetTemperature temperature;

        private SystemZone(int zoneNumber, double minOrbit, double maxOrbit, Planet.PlanetTemperature temperature) {
            this.zoneNumber = zoneNumber;
            this.minOrbit = minOrbit;
            this.maxOrbit = maxOrbit;
            this.temperature = temperature;
        }

        public int getZoneNumber() {
            return zoneNumber;
        }

        public double getMinOrbit() {
            return minOrbit;
        }

        public double getMaxOrbit() {
            return maxOrbit;
        }

        public Planet.PlanetTemperature getTemperature() {
            return temperature;
        }
    }

    private class StarZoneRange {

        private final int maxRoll;
        private final int addConstant;

        private StarZoneRange(int maxRoll, int addConstant) {
            this.maxRoll = maxRoll;
            this.addConstant = addConstant;
        }

        public int getMaxRoll() {
            return maxRoll;
        }

        public int getAddConstant() {
            return addConstant;
        }
    }

}
