package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.*;

public class NeuronLayer {

    private final long layerID;
    private Map<Long, Neuron> neuronMap;

    public NeuronLayer(long layerID) {
        this.layerID = layerID;
        neuronMap = new HashMap<>();
    }

    public void addNeuron(Neuron neuron) {
        if (neuronMap.containsKey(neuron.getNeuronId()))
            throw new RuntimeException("Trying to add neuron which already exists in set");
        neuronMap.put(neuron.getNeuronId(), neuron);
    }

    public Map<Long, Neuron> getNeuronMap() {
        return neuronMap;
    }

    public void updateActivationFunctionValues(NeuronLayer inputLayer) {
        neuronMap.values().forEach(neuron -> neuron.calculateActivationFunctionValue(inputLayer));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeuronLayer that = (NeuronLayer) o;
        return layerID == that.layerID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(layerID);
    }
}
