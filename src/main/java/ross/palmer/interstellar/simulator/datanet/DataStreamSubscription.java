package ross.palmer.interstellar.simulator.datanet;

import ross.palmer.interstellar.utilities.IdGenerator;

import java.util.Objects;

public class DataStreamSubscription {

    private long id;
    private final DataStream dataStream;
    private double lastSyncTime;

    public DataStreamSubscription(DataStream dataStream) {
        id = IdGenerator.getNextId("DataStreamSubscription");
        this.dataStream = dataStream;
    }

    public DataStreamSubscription(DataStreamSubscription dataStreamSubscription) {
        this(dataStreamSubscription.getDataStream());
    }

    DataStream getDataStream() {
        return dataStream;
    }

    public long getDataStreamId() {
        return dataStream.getId();
    }

    public double getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(double lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DataStreamSubscription that = (DataStreamSubscription) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
