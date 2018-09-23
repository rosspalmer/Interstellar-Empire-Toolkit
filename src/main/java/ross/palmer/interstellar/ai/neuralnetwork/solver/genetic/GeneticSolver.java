package ross.palmer.interstellar.ai.neuralnetwork.solver.genetic;

import ross.palmer.interstellar.ai.neuralnetwork.NeuralNetwork;
import ross.palmer.interstellar.ai.neuralnetwork.NeuronLayer;
import ross.palmer.interstellar.utilities.IdGenerator;

import java.util.*;
import java.util.function.Function;

public class GeneticSolver {

    private NeuralNetwork networkDesign;
    private Map<Long, GeneticNeuralNetwork> currentNetworkMap;
    private int currentNetworkSetSize;
    private int matingN;
    private int numberOfRandomsPerCycle;
    private int numberOfInitialRandoms;

    private Function<Collection<GeneticNeuralNetwork>, List<GeneticNeuralNetwork>> evaluationFunction;
    private Function<Double, Integer> childNumberGenerator;

    private String ID_GENERATOR = "GeneticNetwork";

    public GeneticSolver() {
        currentNetworkMap = new HashMap<>();
    }

    public void addToCurrentNetworkSet(GeneticNeuralNetwork geneticNeuralNetwork) {
        currentNetworkMap.put(geneticNeuralNetwork.getNetworkId(), geneticNeuralNetwork);
    }

    public void prepareToRun() {
        currentNetworkMap = new HashMap<>();
        addRandomNetworks(numberOfInitialRandoms);
    }

    public void run(long numberOfCycles) {
        for (int i = 0; i < numberOfCycles; i++) {
            runCycle();
        }
    }

    public Function<Double, Integer> getChildNumberGenerator() {
        return childNumberGenerator;
    }

    public void setChildNumberGenerator(Function<Double, Integer> childNumberGenerator) {
        this.childNumberGenerator = childNumberGenerator;
    }

    public int getCurrentNetworkSetSize() {
        return currentNetworkSetSize;
    }

    public void setCurrentNetworkSetSize(int currentNetworkSetSize) {
        this.currentNetworkSetSize = currentNetworkSetSize;
    }

    public Function<Collection<GeneticNeuralNetwork>, List<GeneticNeuralNetwork>> getEvaluationFunction() {
        return evaluationFunction;
    }

    public void setEvaluationFunction(Function<Collection<GeneticNeuralNetwork>, List<GeneticNeuralNetwork>> evaluationFunction) {
        this.evaluationFunction = evaluationFunction;
    }

    public int getMatingN() {
        return matingN;
    }

    public void setMatingN(int matingN) {
        this.matingN = matingN;
    }

    public int getNumberOfInitialRandoms() {
        return numberOfInitialRandoms;
    }

    public void setNumberOfInitialRandoms(int numberOfInitialRandoms) {
        this.numberOfInitialRandoms = numberOfInitialRandoms;
    }

    public int getNumberOfRandomsPerCycle() {
        return numberOfRandomsPerCycle;
    }

    public void setNumberOfRandomsPerCycle(int numberOfRandomsPerCycle) {
        this.numberOfRandomsPerCycle = numberOfRandomsPerCycle;
    }

    private void runCycle() {
        List<GeneticNeuralNetwork> networksSorted = getEvaluationFunction().apply(currentNetworkMap.values());
        currentNetworkMap = new HashMap<>();
        networksSorted.subList(0, getCurrentNetworkSetSize() - 1)
                .forEach(network -> currentNetworkMap.put(network.getNetworkId(), network));
        mateTopNetworks(networksSorted);
        addRandomNetworks(numberOfRandomsPerCycle);
    }

    private void mateTopNetworks(List<GeneticNeuralNetwork> networksSorted) {

        Set<GeneticNeuralNetwork> topNNetworks = new HashSet<>(networksSorted.subList(0, matingN - 1));
        Set<MatingNetworkPair> alreadyMated = new HashSet<>();

        for (GeneticNeuralNetwork outerNetwork : topNNetworks) {
            for (GeneticNeuralNetwork innerNetwork : topNNetworks) {
                MatingNetworkPair newPair = new MatingNetworkPair(outerNetwork, innerNetwork);
                if (!alreadyMated.contains(newPair)) {
                    newPair.generateChildren().forEach(child -> currentNetworkMap.put(child.getNetworkId(), child));
                    alreadyMated.add(newPair);
                }
            }
        }
    }

    private void addRandomNetworks(int numberOfRandoms) {

        for (int i = 0; i < numberOfRandoms; i++) {

            GeneticNeuralNetwork childNetwork = new GeneticNeuralNetwork(IdGenerator.getNextId(ID_GENERATOR));

            for (int j = 0; j < networkDesign.getHiddenNeuronLayers().size(); j++) {
                NeuronLayer parentANeuronLayer = networkDesign
                        .generateNeuronLayer(IdGenerator.getNextId(ID_GENERATOR),
                                networkDesign.getHiddenNeuronLayers().get(j).getNumberOfNeurons());
                NeuronLayer parentBNeuronLayer = networkDesign
                        .generateNeuronLayer(IdGenerator.getNextId(ID_GENERATOR),
                                networkDesign.getHiddenNeuronLayers().get(j).getNumberOfNeurons());
                GeneticCodeLayer childLayer = new GeneticCodeLayer(j + 1);
                childLayer.generateFromNeuronLayers(parentANeuronLayer, parentBNeuronLayer);
            }
            currentNetworkMap.put(childNetwork.getNetworkId(), childNetwork);
        }
    }

    private class MatingNetworkPair {

        private final GeneticNeuralNetwork parentA;
        private final GeneticNeuralNetwork parentB;

        private MatingNetworkPair(GeneticNeuralNetwork parentA, GeneticNeuralNetwork parentB) {
            this.parentA = parentA;
            this.parentB = parentB;
        }

        private Set<GeneticNeuralNetwork> generateChildren() {

            double combinedObjectiveValue = parentA.getObjectiveValue() + parentB.getObjectiveValue();
            int numberOfChildren = getChildNumberGenerator().apply(combinedObjectiveValue);

            Set<GeneticNeuralNetwork> children = new HashSet<>();

            for (int i = 0; i < numberOfChildren; i++) {
                GeneticNeuralNetwork child = parentA.mate(parentB, IdGenerator.getNextId(ID_GENERATOR));
                children.add(child);
            }

            return children;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MatingNetworkPair that = (MatingNetworkPair) o;
            return parentA.equals(that.parentA) &&
                    parentB.equals(that.parentB);
        }

        @Override
        public int hashCode() {
            return Objects.hash(parentA, parentB);
        }
    }

}
