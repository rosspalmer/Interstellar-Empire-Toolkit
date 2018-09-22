package ross.palmer.interstellar.ai.neuralnetwork;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.junit.Assert.*;

public class NeuralNetworkTest {

    @Test
    public void feedForward() {

        NeuralNetwork neuralNetwork = new NeuralNetwork(1);
        neuralNetwork.setActivationFunction(x -> x);
        neuralNetwork.setNeuronGenerator(neuronId -> {
            Neuron neuron = new Neuron(neuronId);
            neuron.setNewWeightGenerator(() -> (double) neuronId);
            neuron.setBias(40);
            return neuron;
        });

        InputNeuronLayer inputNeuronLayer = new InputNeuronLayer(1, 2);
        neuralNetwork.setInputLayer(inputNeuronLayer);

        NeuronLayer hiddenNeuronLayer = neuralNetwork.generateNeuronLayer(2, 3);
        neuralNetwork.addHiddenNeuronLayer(hiddenNeuronLayer);

        Function<Long, Neuron> outputNeuronGenerator = neuronId -> {
            Neuron neuron = new Neuron(neuronId);
            neuron.setBias(5000 * neuronId);
            neuron.setNewWeightGenerator(() -> (double) (2 - neuronId));
            neuron.setActivationFunction(x -> x);
            return neuron;
        };

        OutputNeuronLayer outputNeuronLayer = new OutputNeuronLayer(3, 2, outputNeuronGenerator);
        neuralNetwork.setOutputLayer(outputNeuronLayer);

        Map<Long, Double> inputValues = new HashMap<>();
        inputValues.put((long) 1, (double) 1);
        inputValues.put((long) 2, 0.5);
        inputNeuronLayer.setInputValues(inputValues);

        neuralNetwork.feedForward();

        // Hidden activation values
        // 41.5 = ((1.0 + 0.5) * 1) + 40
        // 43.0 = ((1.0 + 0.5) * 2) + 40
        // 44.5 = ((1.0 + 0.5) * 3) + 40

        // Output neurons
        // 5129.0 = (2 - 1) * (41.5 + 43.0 + 44.5) + (5000 * 1)
        // 10000.0 = (2 - 2) * (...) + (5000 * 2)

        Map<Long, Double> outputValues = outputNeuronLayer.getOutputActivationValues();
        assertEquals(5129.0, outputValues.get((long) 1), 0.0001);
        assertEquals(10000, outputValues.get((long) 2), 0.0001);

    }
}