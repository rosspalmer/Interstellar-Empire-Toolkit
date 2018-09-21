package ross.palmer.interstellar.simulator.galaxy;

public class Planet {

    public enum PlanetType {
        GAS_GIANT, ICE_GIANT, TERRAN, DWARF, ASTEROIDS
    }

    public enum PlanetAtmosphere {
        VERY_THIN, THIN, NORMAL, DENSE, VERY_DENSE
    }

    public enum PlanetTemperature {
        HOT, WARM, HABITABLE, COOL, COLD
    }

    private int planetNumber;
    private PlanetType planetType;
    private double orbitalRadius;

    private double gravity;
    private double radius;
    private double mass;

    private PlanetAtmosphere atmosphere;
    private PlanetTemperature temperature;

    public int getPlanetNumber() {
        return planetNumber;
    }

    public void setPlanetNumber(int planetNumber) {
        this.planetNumber = planetNumber;
    }

    public PlanetType getPlanetType() {
        return planetType;
    }

    public void setPlanetType(PlanetType planetType) {
        this.planetType = planetType;
    }

    public double getOrbitalRadius() {
        return orbitalRadius;
    }

    public void setOrbitalRadius(double orbitalRadius) {
        this.orbitalRadius = orbitalRadius;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public PlanetAtmosphere getAtmosphere() {
        return atmosphere;
    }

    public void setAtmosphere(PlanetAtmosphere atmosphere) {
        this.atmosphere = atmosphere;
    }

    public PlanetTemperature getTemperature() {
        return temperature;
    }

    public void setTemperature(PlanetTemperature temperature) {
        this.temperature = temperature;
    }
}
