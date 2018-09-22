package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class OutputNeuronLayer extends NeuronLayer {

    public OutputNeuronLayer(long layerID, int numberOfOutputs, Function<Long, Neuron> neuronGenerator) {
        super(layerID);
        for (int i = 0; i < numberOfOutputs; i++) {
            addNeuron(neuronGenerator.apply((long) i + 1));
        }
    }

    public Map<Long, Double> getOutputActivationValues() {
        Map<Long, Double> outputValues = new HashMap<>();
        getNeuronMap().values().forEach(outputNeuron -> outputValues.put(outputNeuron.getNeuronId(),
                outputNeuron.getActivateFunctionValue()));
        return outputValues;
    }

}
