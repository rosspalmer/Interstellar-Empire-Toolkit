package ross.palmer.interstellar.simulator.datanet;

import ross.palmer.interstellar.simulator.engine.Simulator;

import java.util.HashMap;
import java.util.Map;

class DataStreamRow implements Comparable<DataStreamRow> {

    private final double timeStamp;
    private Map<String, DataValue> values;

    DataStreamRow() {
        timeStamp = Simulator.getCurrentTime();
        values = new HashMap<>();
    }

    double getTimeStamp() {
        return timeStamp;
    }

    boolean getValueBoolean(String valueId) {
        return (Boolean) values.get(valueId).get();
    }

    double getValueDouble(String valueId) {
        return (Double) values.get(valueId).get();
    }

    Enum getValueEnum(String valueId) {
        return (Enum) values.get(valueId).get();
    }

    long getValueLong(String valueId) {
        return (Long) values.get(valueId).get();
    }

    String getValueString(String valueId) {
        return (String) values.get(valueId).get();
    }

    void setValue(String valueId, boolean value) {
        DataValue<Boolean> dataValue = new DataValue<>(value);
        values.put(valueId, dataValue);
    }

    void setValue(String valueId, double value) {
        DataValue<Double> dataValue = new DataValue<>(value);
        values.put(valueId, dataValue);
    }

    void setValue(String valueId, Enum value) {
        DataValue<Enum> dataValue = new DataValue<>(value);
        values.put(valueId, dataValue);
    }

    void setValue(String valueId, long value) {
        DataValue<Long> dataValue = new DataValue<>(value);
        values.put(valueId, dataValue);
    }

    void setValue(String valueId, String value) {
        DataValue<String> dataValue = new DataValue<>(value);
        values.put(valueId, dataValue);
    }

    @Override
    public int compareTo(DataStreamRow o) {
        return Double.compare(timeStamp, o.timeStamp);
    }

}
