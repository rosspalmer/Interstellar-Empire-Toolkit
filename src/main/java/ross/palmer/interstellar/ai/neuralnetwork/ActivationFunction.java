package ross.palmer.interstellar.ai.neuralnetwork;

import java.util.function.Function;

public class ActivationFunction {

    public static Function<Double, Double> sigmoidFunction() {
        return x -> 1 / (1 + Math.exp(-1 * x));
    }

}
