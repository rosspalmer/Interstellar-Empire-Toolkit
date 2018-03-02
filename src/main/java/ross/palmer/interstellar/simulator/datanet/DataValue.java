package ross.palmer.interstellar.simulator.datanet;

public class DataValue<T> {

    private final T value;

    public DataValue(T value) {
        this.value = value;
    }

    public T get() {
        return value;
    }

}
