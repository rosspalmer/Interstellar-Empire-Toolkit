package ross.palmer.interstellar.simulator.datanet;

import ross.palmer.interstellar.simulator.entity.Entity;

import java.util.Set;
import java.util.TreeSet;

public class DataView {

    private final Entity entity;
    private Set<DataStreamSubscription> dataStreamSet;
    private Set<Message> messageSet;

    public DataView(Entity entity) {
        this.entity = entity;
        dataStreamSet = new TreeSet<>();
        messageSet = new TreeSet<>();
    }

    public void updateData(DataNode dataNode) {
        updateDataStreamSubscriptions(dataNode);
        updateMessages(dataNode);
    }

    private void updateDataStreamSubscriptions(DataNode dataNode) {
        dataStreamSet.forEach(subscription -> {
            DataStreamSubscription otherSubscription = dataNode.getDataStreamSubscription(subscription.getId());
            if (otherSubscription != null) {
                subscription.setLastSyncTime(
                        Math.max(subscription.getLastSyncTime(), otherSubscription.getLastSyncTime()));
            }
        });
    }

    private void updateMessages(DataNode dataNode) {
        Set<Message> newMessages = dataNode.getMessageSet(entity);
        messageSet.addAll(newMessages);
        messageSet.forEach(message -> {
            message.setReceived(true);
            if (message.hasRunnable())
                message.getRunnable().run();
        });
    }

}
