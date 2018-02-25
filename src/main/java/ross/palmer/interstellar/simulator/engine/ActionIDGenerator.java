package ross.palmer.interstellar.simulator.engine;

public class ActionIDGenerator {

    private static long currentActionId = 0;

    public static long getNextActionId() {
        currentActionId++;
        return currentActionId;
    }

}
