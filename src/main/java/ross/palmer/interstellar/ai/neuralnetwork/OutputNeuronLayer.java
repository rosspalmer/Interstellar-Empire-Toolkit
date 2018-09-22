package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.HashMap;
import java.util.Map;

public class OutputNeuronLayer extends NeuronLayer {

    public OutputNeuronLayer(long layerID, int numberOfOutputs) {
        super(layerID);
        for (int i = 0; i < numberOfOutputs; i++) {
            Neuron newNeuron = new Neuron((long) i + 1);
            addNeuron(newNeuron);
        }
    }

    public Map<Long, Double> getOutputActivationValues() {
        Map<Long, Double> outputValues = new HashMap<>();
        getNeuronSet().forEach(outputNeuron -> outputValues.put(outputNeuron.getNeuronId(),
                outputNeuron.getActivateFunctionValue()));
        return outputValues;
    }

}
