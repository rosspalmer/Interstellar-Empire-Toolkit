package ross.palmer.interstellar.simulator.datanet;

import ross.palmer.interstellar.simulator.engine.Simulator;
import ross.palmer.interstellar.utilities.IdGenerator;

import java.util.Set;
import java.util.TreeSet;

public class DataStream {

    private final long id;
    private final String name;

    private Set<DataStreamRow> rows;

    public DataStream(String name) {
        id = IdGenerator.getNextId("DataStream");
        Simulator.addDataStreamToDataBase(this);
        this.name = name;
        rows = new TreeSet<>();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
