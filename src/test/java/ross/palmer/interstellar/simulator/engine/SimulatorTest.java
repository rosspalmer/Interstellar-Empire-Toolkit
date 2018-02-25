package ross.palmer.interstellar.simulator.engine;

import org.junit.Before;
import org.junit.Test;
import ross.palmer.interstellar.exceptions.InterstellarToolkitRuntimeException;

import static org.junit.Assert.*;

public class SimulatorTest {

    private ActionQueue actionQueue;
    private Action actionA;
    private Action actionB;
    private Action actionC;
    private BooleanDummy stateA;
    private BooleanDummy stateB;
    private BooleanDummy stateC;

    @Before
    public void setup() {

        Simulator.resetTime();

        actionQueue = new ActionQueue();
        Simulator.setActionQueue(actionQueue);

        actionA = new Action();
        actionB = new Action();
        actionC = new Action();

        stateA = new BooleanDummy();
        stateB = new BooleanDummy();
        stateC = new BooleanDummy();

    }

    @Test
    public void addActionToQueue() {

        assertFalse(actionA.isInQueue());
        assertFalse(actionB.isInQueue());
        assertFalse(actionQueue.isInQueue(actionA));
        assertFalse(actionQueue.isInQueue(actionB));
        assertEquals(0, actionQueue.getQueueSize());

        Simulator.addActionToQueue(actionA);

        assertTrue(actionA.isInQueue());
        assertFalse(actionB.isInQueue());
        assertTrue(actionQueue.isInQueue(actionA));
        assertFalse(actionQueue.isInQueue(actionB));
        assertEquals(1, actionQueue.getQueueSize());

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

    @Test
    public void run() {

        actionA.setRunnable(new SetState(stateA, true));
        actionB.setRunnable(new SetState(stateB, true));

        actionA.setActionTime(5.89);
        actionB.setActionTime(1.7);

        assertFalse(stateA.getValue());
        assertFalse(stateB.getValue());
        assertFalse(stateC.getValue());
        assertEquals(0, Simulator.getCurrentTime(), 0.00001);

        Simulator.run();

        assertTrue(stateA.getValue());
        assertTrue(stateB.getValue());
        assertFalse(stateC.getValue());
        assertEquals(5.89, Simulator.getCurrentTime(), 0.00001);

        actionA.setRunnable(new SetState(stateA, false));
        actionC.setRunnable(new SetState(stateC, true));

        actionA.setActionTime(7.3);
        actionC.setActionTime(12.67);

        Simulator.run();

        assertFalse(stateA.getValue());
        assertTrue(stateB.getValue());
        assertTrue(stateC.getValue());
        assertEquals(12.67, Simulator.getCurrentTime(), 0.00001);

        Simulator.run();

        assertFalse(stateA.getValue());
        assertTrue(stateB.getValue());
        assertTrue(stateC.getValue());
        assertEquals(12.67, Simulator.getCurrentTime(), 0.00001);

    }

    @Test
    public void runNextAction() {

        actionA.setRunnable(new SetState(stateA, true));
        actionB.setRunnable(new SetState(stateB, true));
        actionC.setRunnable(new SetState(stateC, true));

        actionA.setActionTime(5);
        actionB.setActionTime(4.9);
        actionC.setActionTime(5.01);

        assertFalse(stateA.getValue());
        assertFalse(stateB.getValue());
        assertFalse(stateC.getValue());
        assertEquals(0, Simulator.getCurrentTime(), 0.00001);

        Simulator.runNextAction();

        assertFalse(stateA.getValue());
        assertTrue(stateB.getValue());
        assertFalse(stateC.getValue());
        assertEquals(4.9, Simulator.getCurrentTime(), 0.00001);

        Simulator.runNextAction();

        assertTrue(stateA.getValue());
        assertTrue(stateB.getValue());
        assertFalse(stateC.getValue());
        assertEquals(5, Simulator.getCurrentTime(), 0.00001);

        Simulator.runNextAction();

        assertTrue(stateA.getValue());
        assertTrue(stateB.getValue());
        assertTrue(stateC.getValue());
        assertEquals(5.01, Simulator.getCurrentTime(), 0.00001);

        Simulator.runNextAction();

        assertTrue(stateA.getValue());
        assertTrue(stateB.getValue());
        assertTrue(stateC.getValue());
        assertEquals(5.01, Simulator.getCurrentTime(), 0.00001);

    }

    @Test(expected = InterstellarToolkitRuntimeException.class)
    public void runNextAction_SettingTimeInPast() {

        actionA.setRunnable(new SetState(stateA, true));
        actionB.setRunnable(new SetState(stateB, true));

        actionA.setActionTime(5);

        assertFalse(stateA.getValue());
        assertFalse(stateB.getValue());
        assertEquals(0, Simulator.getCurrentTime(), 0.00001);

        Simulator.runNextAction();

        assertTrue(stateA.getValue());
        assertFalse(stateB.getValue());
        assertEquals(5, Simulator.getCurrentTime(), 0.00001);

        actionB.setActionTime(4.999);

        Simulator.runNextAction();

    }

    private class SetState implements Runnable {

        private BooleanDummy stateToAlter;
        private boolean stateToSet;

        private SetState(BooleanDummy stateToAlter, boolean stateToSet) {
            this.stateToAlter = stateToAlter;
            this.stateToSet = stateToSet;
        }

        @Override
        public void run() {
            stateToAlter.setValue(stateToSet);
        }

    }

    private class BooleanDummy {

        private boolean value = false;

        public boolean getValue() {
            return value;
        }

        public void setValue(boolean newValue) {
            value = newValue;
        }

    }

}