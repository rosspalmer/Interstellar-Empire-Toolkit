package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class NeuronLayer {

    private final long layerID;
    private Set<Neuron> neuronSet;

    public NeuronLayer(long layerID) {
        this.layerID = layerID;
        neuronSet = new HashSet<>();
    }

    public void addNeuron(Neuron neuron) {
        if (neuronSet.contains(neuron))
            throw new RuntimeException("Trying to add neuron which already exists in set");
        neuronSet.add(neuron);
    }

    public Set<Neuron> getNeuronSet() {
        return neuronSet;
    }

    public void updateActivationFunctionValues(NeuronLayer inputLayer) {
        neuronSet.forEach(neuron -> neuron.calculateActivationFunctionValue(inputLayer));
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
