package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public class NeuralNetwork {

    private final long networkId;
    private NeuronLayer inputLayer;
    private NeuronLayer outputLayer;
    private List<NeuronLayer> hiddenNeuronLayers;

    private Function<Double, Double> activationFunction;
    private Function<Long, Neuron> neuronGenerator;

    public NeuralNetwork(long networkId) {
        this.networkId = networkId;
        hiddenNeuronLayers = new ArrayList<>();
    }

    public void addHiddenNeuronLayer(NeuronLayer neuronLayer) {
        hiddenNeuronLayers.add(neuronLayer);
    }

    public NeuronLayer generateNeuronLayer(long layerId, int numberOfNeurons) {
        NeuronLayer newNeuronLayer = new NeuronLayer(layerId);
        for (int i = 0; i < numberOfNeurons; i++) {
            Neuron newNeuron = neuronGenerator.apply((long) i + 1);
            newNeuron.setActivationFunction(activationFunction);
            newNeuronLayer.addNeuron(newNeuron);
        }
        return newNeuronLayer;
    }

    public void feedForward() {

        if (hiddenNeuronLayers.size() > 0) {

            NeuronLayer firstHiddenLayer = hiddenNeuronLayers.get(0);
            firstHiddenLayer.updateActivationFunctionValues(inputLayer);

            if (hiddenNeuronLayers.size() > 1) {
                for (int i = 0; i < hiddenNeuronLayers.size(); i++) {
                    NeuronLayer previousLayer = hiddenNeuronLayers.get(i);
                    NeuronLayer nextLayer = hiddenNeuronLayers.get(i + 1);
                    nextLayer.updateActivationFunctionValues(previousLayer);
                }
            }

            NeuronLayer lastHiddenLayer = hiddenNeuronLayers.get(hiddenNeuronLayers.size() - 1);
            outputLayer.updateActivationFunctionValues(lastHiddenLayer);

        } else {
            outputLayer.updateActivationFunctionValues(inputLayer);
        }

    }

    public void setActivationFunction(Function<Double, Double> activationFunction) {
        this.activationFunction = activationFunction;
    }

    public NeuronLayer getInputLayer() {
        return inputLayer;
    }

    public void setInputLayer(NeuronLayer inputLayer) {
        this.inputLayer = inputLayer;
    }

    public long getNetworkId() {
        return networkId;
    }

    public void setNeuronGenerator(Function<Long, Neuron> neuronGenerator) {
        this.neuronGenerator = neuronGenerator;
    }

    public NeuronLayer getOutputLayer() {
        return outputLayer;
    }

    public void setOutputLayer(NeuronLayer outputLayer) {
        this.outputLayer = outputLayer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NeuralNetwork that = (NeuralNetwork) o;
        return networkId == that.networkId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(networkId);
    }
}
