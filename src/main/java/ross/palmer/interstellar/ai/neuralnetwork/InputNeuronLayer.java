package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.Map;

public class InputNeuronLayer extends NeuronLayer {

    public InputNeuronLayer(long layerID, int numberOfInputs) {
        super(layerID);
        for (int i = 0; i < numberOfInputs; i++) {
            Neuron newNeuron = new Neuron((long) i + 1);
            addNeuron(newNeuron);
        }
    }

    public void setInputValues(Map<Long, Double> inputValues) {
        getNeuronMap().forEach((neuronId, neuron) -> {
            if (inputValues.containsKey(neuronId))
                neuron.setActivateFunctionValue(inputValues.get(neuronId));
        });
    }

}
