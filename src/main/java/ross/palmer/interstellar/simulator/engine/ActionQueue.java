package ross.palmer.interstellar.simulator.engine;

import java.util.ArrayList;
import java.util.List;

class ActionQueue {

    private List<Action> queue;

    ActionQueue() {
        queue = new ArrayList<>();
    }

    void addToQueue(Action action) {
        action.setInQueue(true);
        queue.add(action);
    }

    boolean isInQueue(Action action) {
        return queue.contains(action);
    }

    Action getNextAction() {
        if (getQueueSize() > 0) {
            Action nextAction = queue.stream().sorted().findFirst().get();
            removeFromQueue(nextAction);
            return nextAction;
        } else {
            return null;
        }
    }

    int getQueueSize() {
        return queue.size();
    }

    void removeFromQueue(Action action) {
        action.setInQueue(false);
        queue.remove(action);
    }

}
