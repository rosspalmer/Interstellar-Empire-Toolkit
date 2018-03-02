package ross.palmer.interstellar.simulator.datanet;

import java.util.HashSet;
import java.util.Set;

public class DataBase {

    private Set<DataStream> dataStreams;
    private Set<Message> messages;

    public DataBase() {
        dataStreams = new HashSet<>();
        messages = new HashSet<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public void addDataStream(DataStream dataStream) {
        dataStreams.add(dataStream);
    }

}
