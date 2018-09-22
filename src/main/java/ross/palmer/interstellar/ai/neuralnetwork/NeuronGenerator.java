package ross.palmer.interstellar.ai.neuralnetwork;

public class NeuronGenerator {

    public static Neuron randomWeightAndBias(long neuronID) {
        Neuron neuron = new Neuron(neuronID);
        neuron.setBias(Math.random());
        neuron.setNewWeightGenerator(Math::random);
        return neuron;
    }

}
