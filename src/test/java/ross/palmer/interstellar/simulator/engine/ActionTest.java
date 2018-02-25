package ross.palmer.interstellar.simulator.engine;

import org.junit.Before;
import org.junit.Test;
import ross.palmer.interstellar.exceptions.InterstellarToolkitRuntimeException;

import static org.junit.Assert.*;

public class ActionTest {

    private ActionQueue actionQueue;
    private Action action;

    @Before
    public void setup() {
        actionQueue = new ActionQueue();
        action = new Action();
        Simulator.setActionQueue(actionQueue);
        Simulator.resetTime();
    }

    @Test
    public void removeFromQueue() {

        actionQueue.addToQueue(action);

        assertTrue(action.isInQueue());
        assertTrue(actionQueue.isInQueue(action));

        action.removeFromQueue();

        assertFalse(action.isInQueue());
        assertFalse(actionQueue.isInQueue(action));

    }

    @Test
    public void setActionTime() {

        assertEquals(0, action.getActionTime(), 0.00001);
        assertFalse(action.isInQueue());
        assertFalse(actionQueue.isInQueue(action));

        action.setActionTime(10.78);

        assertEquals(10.78, action.getActionTime(), 0.00001);
        assertTrue(action.isInQueue());
        assertTrue(actionQueue.isInQueue(action));

        action.setActionTime(17.8);

        assertEquals(17.8, action.getActionTime(), 0.00001);
        assertTrue(action.isInQueue());
        assertTrue(actionQueue.isInQueue(action));

    }

    @Test(expected = InterstellarToolkitRuntimeException.class)
    public void setNegativeActionTime() {
        action.setActionTime(-19);
    }

}