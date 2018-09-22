package ross.palmer.interstellar.ai.neuralnetwork.solver.genetic;

import ross.palmer.interstellar.ai.neuralnetwork.Neuron;
import ross.palmer.interstellar.ai.neuralnetwork.NeuronLayer;

import java.util.HashMap;
import java.util.Map;

public class GeneticCodeLayer {

    private final long layerId;
    private Map<Long, GenePair> genePairs;

    public GeneticCodeLayer(long layerId) {
        this.layerId = layerId;
        genePairs = new HashMap<>();
    }

    public void generateFromNeuronLayers(NeuronLayer parentLayerA, NeuronLayer parentLayerB) {

        parentLayerA.getNeuronMap().forEach((neuronId, neuronA) -> {

            Neuron neuronB = parentLayerB.getNeuronMap().get(neuronId);

            GenePair genePair = new GenePair();
            genePair.parentNeuronA = neuronA;
            genePair.parentNeuronB = neuronB;
            genePair.parentNeuronADominate = Math.random() < 0.5;
            genePair.parentNeuronBDominate = Math.random() < 0.5;

            genePair.determineChildNeuron();

            genePairs.put(neuronId, genePair);

        });
    }

    public void generateFromGeneticCode(GeneticCodeLayer parentAGeneticCodeLayer, GeneticCodeLayer parentBGeneticCodeLayer) {

        parentAGeneticCodeLayer.genePairs.forEach((neuronId, pairParentA) -> {
            GenePair pairParentB = parentBGeneticCodeLayer.genePairs.get(neuronId);

            GenePair genePair = new GenePair();
            genePair.parentNeuronA = pairParentA.childNeuron;
            genePair.parentNeuronB = pairParentB.childNeuron;
            genePair.parentNeuronADominate = pairParentA.childNeuronDominate;
            genePair.parentNeuronBDominate = pairParentB.childNeuronDominate;

            genePair.determineChildNeuron();

            genePairs.put(neuronId, genePair);

        });
    }

    public long getLayerId() {
        return layerId;
    }

    private class GenePair {

        private Neuron childNeuron;
        private boolean childNeuronDominate;

        private Neuron parentNeuronA;
        private Neuron parentNeuronB;
        private boolean parentNeuronADominate;
        private boolean parentNeuronBDominate;

        public void determineChildNeuron() {

            if (parentNeuronADominate && !parentNeuronBDominate) {
                childNeuron = parentNeuronA;
                childNeuronDominate = true;

            } else if (parentNeuronBDominate && !parentNeuronADominate) {
                childNeuron = parentNeuronB;
                childNeuronDominate = true;

            } else if (parentNeuronADominate) {
                childNeuron = mergeParentNeurons();
                childNeuronDominate = true;

            } else {
                childNeuron = mergeParentNeurons();
                childNeuronDominate = false;
            }
        }

        private Neuron mergeParentNeurons() {

            Neuron newNeuron = new Neuron(parentNeuronA.getNeuronId());

            parentNeuronA.getWeights().forEach((inputNeuronId, weightA) -> {
                double weightB = parentNeuronB.getWeights().get(inputNeuronId);
                double combinedWeight = (weightA + weightB) / 2;
                newNeuron.setWeight(inputNeuronId, combinedWeight);
            });

            double combinedBias = (parentNeuronA.getBias() + parentNeuronB.getBias()) / 2;
            newNeuron.setBias(combinedBias);

            return newNeuron;

        }
    }
}
