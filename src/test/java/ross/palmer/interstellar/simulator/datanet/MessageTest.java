package ross.palmer.interstellar.simulator.datanet;

import org.junit.Before;
import org.junit.Test;
import ross.palmer.interstellar.simulator.engine.Simulator;
import ross.palmer.interstellar.simulator.entity.Entity;

import static org.junit.Assert.*;

public class MessageTest {

    private DataBase dataBase;
    private Entity entityFrom;
    private Entity entityTo;
    private Message message;
    private boolean runnableRan;

    @Before
    public void setup() {

        dataBase = new DataBase();
        Simulator.setDataBase(dataBase);

        Simulator.resetTime();
        Simulator.setCurrentTime(300.5);

        entityFrom = new Entity("from");
        entityTo = new Entity("to");
        message = new Message("Howdy!", entityFrom, entityTo);

        Runnable testRunnable = () -> runnableRan = true;
        message.setRunnable(testRunnable);

    }

    @Test
    public void messageSent() {
        assertEquals(300.5, message.getSentTimestamp(), 0.00001);
        assertEquals(0, message.getReceivedTimestamp(), 0.00001);
        assertTrue(entityFrom.equals(message.getFromEntity()));
        assertTrue(entityTo.equals(message.getToEntity()));
        assertTrue(message.notReceived());
        assertFalse(runnableRan);
    }

    @Test
    public void messageReceived() {

        Simulator.setCurrentTime(456);
        message.receiveMessage();

        assertEquals(300.5, message.getSentTimestamp(), 0.00001);
        assertEquals(456, message.getReceivedTimestamp(), 0.00001);
        assertFalse(message.notReceived());
        assertTrue(runnableRan);

    }
}