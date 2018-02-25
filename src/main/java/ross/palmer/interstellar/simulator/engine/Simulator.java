package ross.palmer.interstellar.simulator.engine;

import ross.palmer.interstellar.exceptions.InterstellarToolkitRuntimeException;

public class Simulator {

    private static ActionQueue actionQueue;
    private static double currentTime;

    public static void addActionToQueue(Action action) {
        actionQueue.addToQueue(action);
    }

    public static double getCurrentTime() {
        return currentTime;
    }

    public static void removeActionFromQueue(Action action) {
        actionQueue.removeFromQueue(action);
    }

    public static void resetTime() {
        currentTime = 0;
    }

    public static void run() {
        while(actionQueue.getQueueSize() > 0) {
            runNextAction();
        }
    }

    public static void runNextAction() {
        Action nextAction = actionQueue.getNextAction();
        if (nextAction != null) {
            if (nextAction.getActionTime() < getCurrentTime())
                throw new InterstellarToolkitRuntimeException("Next Action has a time less than current time");
            nextAction.runRunnable();
        }
    }

    public static void setActionQueue(ActionQueue actionQueue) {
        Simulator.actionQueue = actionQueue;
    }

    public static void setCurrentTime(double currentTime) {
        Simulator.currentTime = currentTime;
    }

}
