package ross.palmer.interstellar.ai.neuralnetwork.solver.genetic;

import ross.palmer.interstellar.ai.neuralnetwork.NeuralNetwork;

import java.util.HashMap;
import java.util.Map;

public class GeneticNeuralNetwork extends NeuralNetwork {

    private Map<Long, GeneticCodeLayer> geneticCodeLayers;
    private double objectiveValue;

    public GeneticNeuralNetwork(long networkId) {
        super(networkId);
        geneticCodeLayers = new HashMap<>();
    }

    public void addGeneticCodeLayer(GeneticCodeLayer geneticCodeLayer) {
        geneticCodeLayers.put(geneticCodeLayer.getLayerId(), geneticCodeLayer);
    }

    public GeneticNeuralNetwork mate(GeneticNeuralNetwork otherParent, long childNetworkId) {

        GeneticNeuralNetwork childNetwork = new GeneticNeuralNetwork(childNetworkId);

        geneticCodeLayers.forEach((layerId, layerCode) -> {
            GeneticCodeLayer layerCodeOtherParent = otherParent.geneticCodeLayers.get(layerId);
            GeneticCodeLayer layerCodeChild = new GeneticCodeLayer(layerId);
            layerCodeChild.generateFromGeneticCode(layerCode, layerCodeOtherParent);
            childNetwork.addGeneticCodeLayer(layerCodeChild);
        });

        return childNetwork;
    }

    public double getObjectiveValue() {
        return objectiveValue;
    }

    public void setObjectiveValue(double objectiveValue) {
        this.objectiveValue = objectiveValue;
    }
}
