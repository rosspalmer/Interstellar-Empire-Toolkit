package ross.palmer.interstellar.simulator.engine;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ActionQueueTest {

    private ActionQueue actionQueue;
    private Action actionA;
    private Action actionB;
    private Action actionC;

    @Before
    public void setup() {

        actionQueue = new ActionQueue();
        actionA = new Action();
        actionB = new Action();
        actionC = new Action();

        Simulator.resetTime();
        Simulator.setActionQueue(actionQueue);

    }

    @Test
    public void addToQueue() {

        assertFalse(actionA.isInQueue());
        assertFalse(actionB.isInQueue());
        assertFalse(actionQueue.isInQueue(actionA));
        assertFalse(actionQueue.isInQueue(actionB));
        assertEquals(0, actionQueue.getQueueSize());

        actionQueue.addToQueue(actionA);

        assertTrue(actionA.isInQueue());
        assertFalse(actionB.isInQueue());
        assertTrue(actionQueue.isInQueue(actionA));
        assertFalse(actionQueue.isInQueue(actionB));
        assertEquals(1, actionQueue.getQueueSize());

    }

    @Test
    public void getNextAction() {

        actionA.setActionTime(5);
        actionB.setActionTime(2);
        actionC.setActionTime(10);

        Action nextAction;
        nextAction = actionQueue.getNextAction();

        assertNotEquals(actionA, nextAction);
        assertEquals(actionB, nextAction);
        assertNotEquals(actionC, nextAction);
        assertTrue(actionA.isInQueue());
        assertFalse(actionB.isInQueue());
        assertTrue(actionC.isInQueue());
        assertEquals(0, Simulator.getCurrentTime(), 0.00001);

        nextAction = actionQueue.getNextAction();

        assertEquals(actionA, nextAction);
        assertNotEquals(actionB, nextAction);
        assertNotEquals(actionC, nextAction);
        assertFalse(actionA.isInQueue());
        assertFalse(actionB.isInQueue());
        assertTrue(actionC.isInQueue());
        assertEquals(0, Simulator.getCurrentTime(), 0.00001);

        nextAction = actionQueue.getNextAction();

        assertNotEquals(actionA, nextAction);
        assertNotEquals(actionB, nextAction);
        assertEquals(actionC, nextAction);
        assertFalse(actionA.isInQueue());
        assertFalse(actionB.isInQueue());
        assertFalse(actionC.isInQueue());
        assertEquals(0, Simulator.getCurrentTime(), 0.00001);

        nextAction = actionQueue.getNextAction();

        assertTrue(nextAction == null);

    }

    @Test
    public void removeFromQueue() {

        actionQueue.addToQueue(actionA);
        actionQueue.addToQueue(actionB);

        assertTrue(actionA.isInQueue());
        assertTrue(actionB.isInQueue());
        assertTrue(actionQueue.isInQueue(actionA));
        assertTrue(actionQueue.isInQueue(actionB));
        assertEquals(2, actionQueue.getQueueSize());

        actionQueue.removeFromQueue(actionA);

        assertFalse(actionA.isInQueue());
        assertTrue(actionB.isInQueue());
        assertFalse(actionQueue.isInQueue(actionA));
        assertTrue(actionQueue.isInQueue(actionB));
        assertEquals(1, actionQueue.getQueueSize());

    }
}