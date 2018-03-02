package ross.palmer.interstellar.simulator.datanet;

import ross.palmer.interstellar.simulator.entity.Entity;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DataNode {

    private HashMap<Long, DataStreamSubscription> dataStreamSubscriptions;
    private Set<Message> messageSet;

    public DataNode() {
        dataStreamSubscriptions = new HashMap<>();
        messageSet = new TreeSet<>();
    }

    public DataStreamSubscription getDataStreamSubscription(long dataStreamId) {
        return dataStreamSubscriptions.get(dataStreamId);
    }

    public Set<Message> getMessageSet(Entity entity) {
        return messageSet.stream()
                .filter(message -> message.getToEntity().equals(entity))
                .collect(Collectors.toSet());
    }

    public void syncDataNode(DataNode otherDataNode) {
        syncDataStreams(otherDataNode);
        syncMessageStreams(otherDataNode);
    }

    private void purgeReceivedMessages() {
        messageSet = messageSet.stream()
                .filter(Message::notReceived)
                .collect(Collectors.toSet());
    }

    private void syncDataStreams(DataNode otherDataNode) {
        otherDataNode.dataStreamSubscriptions.values().forEach(otherSubscription -> {
            DataStreamSubscription nodeSubscription = dataStreamSubscriptions.get(otherSubscription.getId());
            double latestUpdateTime = Math.max(nodeSubscription.getLastSyncTime(), otherSubscription.getLastSyncTime());
            nodeSubscription.setLastSyncTime(latestUpdateTime);
            otherSubscription.setLastSyncTime(latestUpdateTime);
            }
        );
    }

    private void syncMessageStreams(DataNode otherDataNode) {
        purgeReceivedMessages();
        otherDataNode.purgeReceivedMessages();
        messageSet.addAll(otherDataNode.messageSet);
        otherDataNode.messageSet = messageSet;
    }

}
