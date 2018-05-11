package ross.palmer.interstellar.utilities;

import java.util.ArrayList;
import java.util.List;

public class RandomSelector<T> {

    private List<Selection<T>> selections;

    public RandomSelector() {
        selections = new ArrayList<>();
    }

    public void addSelection(T item, double relativeProbability) {
        selections.add(new Selection<>(item, relativeProbability));
    }

    public T randomPick() {

        double relativeProbabilitySum = selections.stream().mapToDouble(Selection::getRelativeProbability).sum();
        selections.forEach(selection -> selection.setProbability(relativeProbabilitySum));

        double random = Math.random();
        double cumulativeProbability = 0;

        Selection<T> pick = null;

        for (Selection<T> selection : selections) {
            cumulativeProbability += selection.getProbability();
            if (cumulativeProbability >= random)
                pick = selection;
        }

        return pick.getItem();

    }

    private class Selection<T> {

        private final T item;
        private final double relativeProbability;
        private double probability;

        Selection(T item, double relativeProbability) {
            this.item = item;
            this.relativeProbability = relativeProbability;
        }

        public T getItem() {
            return item;
        }

        public double getRelativeProbability() {
            return relativeProbability;
        }

        public double getProbability() {
            return probability;
        }

        public void setProbability(double relativeProbabilitySum) {
            this.probability = relativeProbability / relativeProbabilitySum;
        }

    }

}
