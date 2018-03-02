package ross.palmer.interstellar.simulator.datanet;

import ross.palmer.interstellar.simulator.entity.Entity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DataNode {

    private HashMap<Long, DataStreamSubscription> dataStreamSubscriptions;
    private Set<Message> messageSet;

    public DataNode() {
        dataStreamSubscriptions = new HashMap<>();
        messageSet = new HashSet<>();
    }

    public void addMessage(Message message) {
        messageSet.add(message);
    }

    public void startDataStreamSubscription(DataStreamSubscription seedSubscription) {
        DataStreamSubscription newSubscription = new DataStreamSubscription(seedSubscription.getDataStream());
        newSubscription.setLastSyncTime(seedSubscription.getLastSyncTime());
        dataStreamSubscriptions.put(seedSubscription.getDataStreamId(), newSubscription);
    }

    public DataStreamSubscription getDataStreamSubscription(long dataStreamId) {
        return dataStreamSubscriptions.get(dataStreamId);
    }

    public Set<Message> getMessageSet(Entity toEntity) {
        return messageSet.stream()
                .filter(message -> message.getToEntity().equals(toEntity))
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

        dataStreamSubscriptions.values().forEach(subscription -> {
            DataStreamSubscription otherSubscription = otherDataNode.dataStreamSubscriptions.get(subscription.getDataStreamId());
            if (otherSubscription == null) {
                DataStreamSubscription newSubscription = new DataStreamSubscription(subscription);
                otherDataNode.startDataStreamSubscription(newSubscription);
            } else {
                double latestUpdateTime = Math.max(subscription.getLastSyncTime(), otherSubscription.getLastSyncTime());
                subscription.setLastSyncTime(latestUpdateTime);
                otherSubscription.setLastSyncTime(latestUpdateTime);
            }
        }
        );

        otherDataNode.dataStreamSubscriptions.values().forEach(otherSubscription -> {
            DataStreamSubscription nodeSubscription = dataStreamSubscriptions.get(otherSubscription.getDataStreamId());
            if (nodeSubscription == null) {
                startDataStreamSubscription(otherSubscription);
            } else {
                double latestUpdateTime = Math.max(nodeSubscription.getLastSyncTime(), otherSubscription.getLastSyncTime());
                nodeSubscription.setLastSyncTime(latestUpdateTime);
                otherSubscription.setLastSyncTime(latestUpdateTime);
            }
        }
        );

    }

    private void syncMessageStreams(DataNode otherDataNode) {
        purgeReceivedMessages();
        otherDataNode.purgeReceivedMessages();
        messageSet.addAll(otherDataNode.messageSet);
        otherDataNode.messageSet = new HashSet<>(messageSet);
    }

}
