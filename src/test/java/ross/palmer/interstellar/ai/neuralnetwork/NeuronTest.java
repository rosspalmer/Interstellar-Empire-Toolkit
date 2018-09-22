package ross.palmer.interstellar.ai.neuralnetwork;

import org.junit.Test;

import static org.junit.Assert.*;

public class NeuronTest {

    @Test
    public void testCalculateActivationFunctionValue() {

        Neuron inputNeuronA = new Neuron(1);
        inputNeuronA.setActivateFunctionValue(3);
        Neuron inputNeuronB = new Neuron(2);
        inputNeuronB.setActivateFunctionValue(0.33);
        Neuron outputNeuron = new Neuron(3);
        outputNeuron.setWeight(2, 3); // Product output 0.99
        outputNeuron.setNewWeightGenerator(() -> 0.5); // Product output 1.5
        outputNeuron.setActivationFunction(x -> x);
        outputNeuron.setBias(2000);

        NeuronLayer inputLayer = new NeuronLayer(1);
        inputLayer.addNeuron(inputNeuronA);
        inputLayer.addNeuron(inputNeuronB);

        outputNeuron.calculateActivationFunctionValue(inputLayer);

        double activationValue = outputNeuron.getActivateFunctionValue();

        // 1.5 + 0.99 + 2000
        assertEquals(2002.49, activationValue, 0.0001);

    }
}