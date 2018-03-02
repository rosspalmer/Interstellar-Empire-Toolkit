package ross.palmer.interstellar.simulator.datanet;

public class DataStreamSubscription {

    private final DataStream dataStream;
    private double lastSyncTime;

    public DataStreamSubscription(DataStream dataStream) {
        this.dataStream = dataStream;
    }

    public long getId() {
        return dataStream.getId();
    }

    public double getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(double lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

}
