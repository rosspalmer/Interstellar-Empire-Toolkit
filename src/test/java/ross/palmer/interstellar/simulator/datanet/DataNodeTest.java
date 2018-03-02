package ross.palmer.interstellar.simulator.datanet;

import org.junit.Before;
import org.junit.Test;
import ross.palmer.interstellar.simulator.engine.Simulator;
import ross.palmer.interstellar.simulator.entity.Entity;

import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;


public class DataNodeTest {

    private DataBase dataBase;
    private DataNode dataNodeA;
    private DataNode dataNodeB;
    private DataNode dataNodeC;

    private Entity entityA;
    private Entity entityB;
    private Entity entityC;

    private DataStream dataStreamA;
    private DataStream dataStreamB;
    private DataStream dataStreamC;

    @Before
    public void setup() {

        dataBase = new DataBase();
        Simulator.resetTime();
        Simulator.setDataBase(dataBase);

        dataNodeA = new DataNode();
        dataNodeB = new DataNode();
        dataNodeC = new DataNode();

        entityA = new Entity("EntityA");
        entityB = new Entity("EntityB");
        entityC = new Entity("EntityC");

        dataStreamA = new DataStream("DataStreamA");
        dataStreamB = new DataStream("DataStreamB");
        dataStreamC = new DataStream("DataStreamC");

    }

    @Test
    public void startDataStreamSubscription() {

        DataStreamSubscription subscriptionAA = new DataStreamSubscription(dataStreamA);
        DataStreamSubscription subscriptionBA = new DataStreamSubscription(dataStreamB);
        DataStreamSubscription subscriptionAB = new DataStreamSubscription(dataStreamA);
        DataStreamSubscription subscriptionBB = new DataStreamSubscription(dataStreamB);

        subscriptionAA.setLastSyncTime(10);
        subscriptionBA.setLastSyncTime(15);
        subscriptionAB.setLastSyncTime(20);
        subscriptionBB.setLastSyncTime(25);

        dataNodeA.startDataStreamSubscription(subscriptionAA);
        dataNodeA.startDataStreamSubscription(subscriptionBA);
        dataNodeB.startDataStreamSubscription(subscriptionAB);
        dataNodeB.startDataStreamSubscription(subscriptionBB);

        DataStreamSubscription testSubAA = dataNodeA.getDataStreamSubscription(dataStreamA.getId());
        DataStreamSubscription testSubAB = dataNodeB.getDataStreamSubscription(dataStreamA.getId());

        assertFalse(testSubAA.equals(subscriptionAA));
        assertFalse(testSubAA.equals(subscriptionAB));
        assertFalse(testSubAB.equals(subscriptionAA));
        assertFalse(testSubAB.equals(subscriptionAB));

        assertEquals(10, testSubAA.getLastSyncTime(), 0.00001);
        assertEquals(20, testSubAB.getLastSyncTime(), 0.00001);

        assertTrue(subscriptionAA.getDataStream().equals(dataStreamA));
        assertTrue(testSubAA.getDataStream().equals(dataStreamA));
        assertTrue(subscriptionAB.getDataStream().equals(dataStreamA));
        assertTrue(testSubAB.getDataStream().equals(dataStreamA));

        DataStreamSubscription testSubBA = dataNodeA.getDataStreamSubscription(dataStreamB.getId());
        DataStreamSubscription testSubBB = dataNodeB.getDataStreamSubscription(dataStreamB.getId());

        assertFalse(testSubBA.equals(subscriptionBA));
        assertFalse(testSubBA.equals(subscriptionBB));
        assertFalse(testSubBB.equals(subscriptionBB));
        assertFalse(testSubBB.equals(subscriptionBB));

        assertEquals(15, testSubBA.getLastSyncTime(), 0.00001);
        assertEquals(25, testSubBB.getLastSyncTime(), 0.00001);

        assertTrue(subscriptionBA.getDataStream().equals(dataStreamB));
        assertTrue(testSubBA.getDataStream().equals(dataStreamB));
        assertTrue(subscriptionBB.getDataStream().equals(dataStreamB));
        assertTrue(testSubBB.getDataStream().equals(dataStreamB));

    }

    @Test
    public void addMessage() {

        Message messageA = new Message("A", entityA, entityB);
        Message messageB = new Message("B", entityA, entityB);
        Message messageC = new Message("C", entityB, entityA);

        dataNodeA.addMessage(messageA);
        dataNodeA.addMessage(messageB);
        dataNodeB.addMessage(messageB);
        dataNodeB.addMessage(messageC);


        Set<Message> messagesNodeA_EntityA = dataNodeA.getMessageSet(entityA);
        Set<Message> messagesNodeA_EntityB = dataNodeA.getMessageSet(entityB);
        Set<Message> messagesNodeB_EntityA = dataNodeB.getMessageSet(entityA);
        Set<Message> messagesNodeB_EntityB = dataNodeB.getMessageSet(entityB);

        assertEquals(messagesNodeA_EntityA.size(), 0);
        assertEquals(messagesNodeA_EntityB.size(), 2);
        assertTrue(messagesNodeA_EntityB.contains(messageA));
        assertTrue(messagesNodeA_EntityB.contains(messageB));
        assertFalse(messagesNodeA_EntityB.contains(messageC));

        assertEquals(messagesNodeB_EntityA.size(), 1);
        assertFalse(messagesNodeB_EntityA.contains(messageA));
        assertFalse(messagesNodeB_EntityA.contains(messageB));
        assertTrue(messagesNodeB_EntityA.contains(messageC));
        assertEquals(messagesNodeB_EntityB.size(), 1);
        assertFalse(messagesNodeB_EntityB.contains(messageA));
        assertTrue(messagesNodeB_EntityB.contains(messageB));
        assertFalse(messagesNodeB_EntityB.contains(messageC));

    }

    @Test
    public void syncDataNode_DataStreams_Forwards() {

        DataStreamSubscription subscriptionA = new DataStreamSubscription(dataStreamA);
        DataStreamSubscription subscriptionB = new DataStreamSubscription(dataStreamB);
        DataStreamSubscription subscriptionC = new DataStreamSubscription(dataStreamC);

        subscriptionA.setLastSyncTime(10);
        dataNodeA.startDataStreamSubscription(subscriptionA);
        dataNodeA.syncDataNode(dataNodeB);

        assertTrue(dataStreamA.equals(dataNodeA.getDataStreamSubscription(dataStreamA.getId()).getDataStream()));
        assertTrue(dataStreamA.equals(dataNodeB.getDataStreamSubscription(dataStreamA.getId()).getDataStream()));
        assertEquals(10, dataNodeA.getDataStreamSubscription(dataStreamA.getId()).getLastSyncTime(), 0.00001);
        assertEquals(10, dataNodeB.getDataStreamSubscription(dataStreamA.getId()).getLastSyncTime(), 0.00001);

        assertTrue(dataNodeA.getDataStreamSubscription(dataStreamB.getId()) == null);
        assertTrue(dataNodeB.getDataStreamSubscription(dataStreamB.getId()) == null);
        assertTrue(dataNodeA.getDataStreamSubscription(dataStreamC.getId()) == null);
        assertTrue(dataNodeB.getDataStreamSubscription(dataStreamC.getId()) == null);

        subscriptionB.setLastSyncTime(7.5);
        dataNodeA.startDataStreamSubscription(subscriptionB);

        subscriptionB.setLastSyncTime(6.8);
        subscriptionC.setLastSyncTime(9.9);
        dataNodeB.startDataStreamSubscription(subscriptionB);
        dataNodeB.startDataStreamSubscription(subscriptionC);

        dataNodeA.syncDataNode(dataNodeB);

        assertTrue(dataStreamA.equals(dataNodeA.getDataStreamSubscription(dataStreamA.getId()).getDataStream()));
        assertTrue(dataStreamA.equals(dataNodeB.getDataStreamSubscription(dataStreamA.getId()).getDataStream()));
        assertEquals(10, dataNodeA.getDataStreamSubscription(dataStreamA.getId()).getLastSyncTime(), 0.00001);
        assertEquals(10, dataNodeB.getDataStreamSubscription(dataStreamA.getId()).getLastSyncTime(), 0.00001);

        assertTrue(dataStreamB.equals(dataNodeA.getDataStreamSubscription(dataStreamB.getId()).getDataStream()));
        assertTrue(dataStreamB.equals(dataNodeB.getDataStreamSubscription(dataStreamB.getId()).getDataStream()));
        assertEquals(7.5, dataNodeA.getDataStreamSubscription(dataStreamB.getId()).getLastSyncTime(), 0.00001);
        assertEquals(7.5, dataNodeB.getDataStreamSubscription(dataStreamB.getId()).getLastSyncTime(), 0.00001);

        assertTrue(dataStreamC.equals(dataNodeA.getDataStreamSubscription(dataStreamC.getId()).getDataStream()));
        assertTrue(dataStreamC.equals(dataNodeB.getDataStreamSubscription(dataStreamC.getId()).getDataStream()));
        assertEquals(9.9, dataNodeA.getDataStreamSubscription(dataStreamC.getId()).getLastSyncTime(), 0.00001);
        assertEquals(9.9, dataNodeB.getDataStreamSubscription(dataStreamC.getId()).getLastSyncTime(), 0.00001);

    }

    @Test
    public void syncDataNode_DataStreams_Backwards() {

        DataStreamSubscription subscriptionA = new DataStreamSubscription(dataStreamA);
        DataStreamSubscription subscriptionB = new DataStreamSubscription(dataStreamB);
        DataStreamSubscription subscriptionC = new DataStreamSubscription(dataStreamC);

        subscriptionA.setLastSyncTime(10);
        dataNodeA.startDataStreamSubscription(subscriptionA);
        dataNodeB.syncDataNode(dataNodeA);

        assertTrue(dataStreamA.equals(dataNodeA.getDataStreamSubscription(dataStreamA.getId()).getDataStream()));
        assertTrue(dataStreamA.equals(dataNodeB.getDataStreamSubscription(dataStreamA.getId()).getDataStream()));
        assertEquals(10, dataNodeA.getDataStreamSubscription(dataStreamA.getId()).getLastSyncTime(), 0.00001);
        assertEquals(10, dataNodeB.getDataStreamSubscription(dataStreamA.getId()).getLastSyncTime(), 0.00001);

        assertTrue(dataNodeA.getDataStreamSubscription(dataStreamB.getId()) == null);
        assertTrue(dataNodeB.getDataStreamSubscription(dataStreamB.getId()) == null);
        assertTrue(dataNodeA.getDataStreamSubscription(dataStreamC.getId()) == null);
        assertTrue(dataNodeB.getDataStreamSubscription(dataStreamC.getId()) == null);

        subscriptionB.setLastSyncTime(7.5);
        dataNodeA.startDataStreamSubscription(subscriptionB);

        subscriptionB.setLastSyncTime(6.8);
        subscriptionC.setLastSyncTime(9.9);
        dataNodeB.startDataStreamSubscription(subscriptionB);
        dataNodeB.startDataStreamSubscription(subscriptionC);

        dataNodeB.syncDataNode(dataNodeA);

        assertTrue(dataStreamA.equals(dataNodeA.getDataStreamSubscription(dataStreamA.getId()).getDataStream()));
        assertTrue(dataStreamA.equals(dataNodeB.getDataStreamSubscription(dataStreamA.getId()).getDataStream()));
        assertEquals(10, dataNodeA.getDataStreamSubscription(dataStreamA.getId()).getLastSyncTime(), 0.00001);
        assertEquals(10, dataNodeB.getDataStreamSubscription(dataStreamA.getId()).getLastSyncTime(), 0.00001);

        assertTrue(dataStreamB.equals(dataNodeA.getDataStreamSubscription(dataStreamB.getId()).getDataStream()));
        assertTrue(dataStreamB.equals(dataNodeB.getDataStreamSubscription(dataStreamB.getId()).getDataStream()));
        assertEquals(7.5, dataNodeA.getDataStreamSubscription(dataStreamB.getId()).getLastSyncTime(), 0.00001);
        assertEquals(7.5, dataNodeB.getDataStreamSubscription(dataStreamB.getId()).getLastSyncTime(), 0.00001);

        assertTrue(dataStreamC.equals(dataNodeA.getDataStreamSubscription(dataStreamC.getId()).getDataStream()));
        assertTrue(dataStreamC.equals(dataNodeB.getDataStreamSubscription(dataStreamC.getId()).getDataStream()));
        assertEquals(9.9, dataNodeA.getDataStreamSubscription(dataStreamC.getId()).getLastSyncTime(), 0.00001);
        assertEquals(9.9, dataNodeB.getDataStreamSubscription(dataStreamC.getId()).getLastSyncTime(), 0.00001);

    }

    @Test
    public void syncDataNode_Messages() {

        Message messageA = new Message("A", entityA, entityB);
        Message messageB = new Message("B", entityB, entityC);
        Message messageC = new Message("C", entityC, entityA);

        dataNodeA.addMessage(messageA);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        dataNodeA.syncDataNode(dataNodeB);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        dataNodeB.addMessage(messageB);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        dataNodeA.syncDataNode(dataNodeB);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        dataNodeC.addMessage(messageC);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        dataNodeA.syncDataNode(dataNodeC);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        dataNodeC.syncDataNode(dataNodeB);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        messageC.receiveMessage();

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        dataNodeC.syncDataNode(dataNodeA);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

        dataNodeB.syncDataNode(dataNodeC);

        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeA.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeA.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeA.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeA.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeB.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeB.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeB.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeB.getMessageSet(entityC).contains(messageC));

        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageA));
        assertTrue(dataNodeC.getMessageSet(entityB).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageA));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageB));
        assertTrue(dataNodeC.getMessageSet(entityC).contains(messageB));
        assertFalse(dataNodeC.getMessageSet(entityA).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityB).contains(messageC));
        assertFalse(dataNodeC.getMessageSet(entityC).contains(messageC));

    }

}