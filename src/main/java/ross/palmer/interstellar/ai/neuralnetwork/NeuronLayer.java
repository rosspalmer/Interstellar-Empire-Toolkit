package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.HashSet;
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
}
