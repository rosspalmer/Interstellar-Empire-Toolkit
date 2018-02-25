package ross.palmer.interstellar.simulator.engine;

import ross.palmer.interstellar.exceptions.InterstellarToolkitRuntimeException;

import java.util.Objects;

public class Action implements Comparable<Action> {

    private long id = ActionIDGenerator.getNextActionId();
    private Runnable runnable;
    private double actionTime = 0;
    private boolean inQueue = false;

    public void runRunnable() {
        Simulator.setCurrentTime(actionTime);
        runnable.run();
    }

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    public double getActionTime() {
        return actionTime;
    }

    public void removeFromQueue() {
        if (inQueue)
            Simulator.removeActionFromQueue(this);
    }

    public void setActionTime(double actionTime) {
        if (actionTime < 0)
            throw new InterstellarToolkitRuntimeException("Cannot set a negative time to an Action");
        this.actionTime = actionTime;
        if (!inQueue) {
            Simulator.addActionToQueue(this);
        }
    }

    boolean isInQueue() {
        return inQueue;
    }

    void setInQueue(boolean inQueue) {
        this.inQueue = inQueue;
    }

    @Override
    public int compareTo(Action o) {
        return Double.compare(actionTime, o.actionTime);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Action action = (Action) o;
        return id == action.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
