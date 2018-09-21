package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.function.Supplier;

public class NeuronGenerator {

    public static Neuron randomWeightAndBias(long neuronID) {
        Neuron neuron = new Neuron(neuronID);
        neuron.setBias(Math.random());
        neuron.setNewWeightGenerator(new Supplier<Double>() {
            @Override
            public Double get() {
                return Math.random();
            }
        });
        return neuron;
    }



}
