package ross.palmer.interstellar.gui.old.explorer;

import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.HBox;
import ross.palmer.interstellar.Main;

public class StarMap extends HBox {

    private ScatterChart<String, Double> xyChart;
    private ScatterChart<String, Double> xzChart;

    private double bound;
    private double tick;

    public StarMap() {
        bound = 4;
        tick = 0.5;
        updatePlots();
    }

    public void updatePlots() {

        XYChart.Series<Double, Double> xyData = new XYChart.Series<>();
        XYChart.Series<Double, Double> xzData = new XYChart.Series<>();

        Main.getGalaxy().getStellarSystems().stream()
                .filter(stellarSystem -> Math.abs(stellarSystem.getStarData().getZ()) < bound)
                .forEach(stellarSystem -> {

                    xyData.getData().add(new XYChart.Data(stellarSystem.getStarData().getX(),
                            stellarSystem.getStarData().getY()));
                    xzData.getData().add(new XYChart.Data(stellarSystem.getStarData().getX(),
                            stellarSystem.getStarData().getZ()));

                }
        );

        xyChart = generateScatterChart(xyData);
        xzChart = generateScatterChart(xzData);

        getChildren().addAll(xyChart, xzChart);

    }

    public double getBound() {
        return bound;
    }

    public void setBound(double bound) {
        this.bound = bound;
    }

    public double getTick() {
        return tick;
    }

    public void setTick(double tick) {
        this.tick = tick;
    }

    private ScatterChart generateScatterChart(XYChart.Series<Double, Double> data) {

        //Defining the x axis
        NumberAxis xAxis = new NumberAxis(-bound, bound, tick);

        //Defining the y axis
        NumberAxis yAxis = new NumberAxis(-bound, bound, tick);

        ScatterChart scatterChart = new ScatterChart(xAxis, yAxis);
        scatterChart.getData().addAll(data);

        return scatterChart;

    }


}
