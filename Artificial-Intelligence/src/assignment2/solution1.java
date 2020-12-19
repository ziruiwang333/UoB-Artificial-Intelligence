package assignment2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchart.*;
import org.knowm.xchart.style.markers.SeriesMarkers;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class solution1 {

    public static final String DATA_FILE = "data/Q1data.csv";

    public static void main(String[] args) {

        // -------------------------------------------------
        // Data and Graph setup.
        // -------------------------------------------------
        List<List<Double>> data = Data.dataFrom(DATA_FILE);
        Plot plt = new Plot("Height vs Finger Length", "Height", "Finger Length", data);
        sleep(500);

        // -------------------------------------------------
        // Gradient Descent
        // -------------------------------------------------
        final int epochs = 100;  // Number of iterations we want to run through the algorithm

        // We want to predict h(x) = w1 * x + w0
        double w2 = 0;
        double w1 = 0;
        double w0 = 0;

        // Learning rate
        double alpha = 0.000000001;

        // Main Gradient Descent Function for Linear Regression
        for(int i = 0; i < epochs; i++) {

            double cost = 0;

            for(int j = 0; j < data.get(0).size(); j++) {

                double x_j = data.get(0).get(j);
                double y_j = data.get(1).get(j);

                double prediction = (w2 * x_j * x_j)+(w1 * x_j) + w0;

                // cost += (y_j - h(x))^2
                cost += (y_j - prediction) * (y_j - prediction);

                // Update the parameters for our equation.
                w2 += alpha * (y_j - prediction) * x_j * x_j;
                w1 += alpha * (y_j - prediction) * x_j;
                w0 += alpha * (y_j - prediction);

            }

            System.out.println("Current Cost: " + cost);


            // ---------------------------------------------
            // Our Hypothesis Function after the epoch
            // (these values are final because of how
            // functional programming works in Java).
            final double w_2 = w2;
            final double w_1 = w1;
            final double w_0 = w0;
            HypothesisFunction h_x = (x) -> (w_2 * x * x) + (w_1 * x) + w_0;
            // ----------------------------------------------
            // Plotting prediction with current values of w
            plt.updatePlot(h_x);
            sleep(50);
            // ----------------------------------------------
        }


        System.out.println("Final Equation: h(x) = "+"(" + w2 +" * x * x) + (" + w1 + " * x) + " + w0);
    }

    static void sleep(int ticks) {
        try{ Thread.sleep(ticks); } catch(Exception e) { e.printStackTrace(); }
    }
}





class Data {
	
	

    /*
     * Reads the data from a given csv file
     */
    public static List<List<Double>> dataFrom(String fileName) {
        List<List<Double>> output = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while((line = reader.readLine()) != null) {
                String[] rawValues = line.split(",");
                for(int i = 0; i < rawValues.length; i++) {
                    if(i >= output.size()) output.add(new ArrayList<>());
                    output.get(i).add(Double.parseDouble(rawValues[i]));
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return output;
    }

    /*
     * Generates the points to plot a function.
     */
    public static List<List<Double>> plotFunction(double min, double max, double step, HypothesisFunction f_x){
        List<List<Double>> output = new ArrayList<>();
        output.add(new ArrayList<>());
        output.add(new ArrayList<>());

        for(double x = min; x <= max; x += step) {
            output.get(0).add(x);
            output.get(1).add(f_x.evaluate(x));
        }

        return output;
    }
}



class Plot {

    XYChart chart;
    double yMin;
    double yMax;
    double xMin;
    double xMax;
    JFrame frame;

    public Plot(String title, String xAxis, String yAxis, List<List<Double>> data) {

        // Create the chart
        chart = new XYChartBuilder()
                        .width(1000)
                        .height(600)
                        .title(title)
                        .xAxisTitle(xAxis)
                        .yAxisTitle(yAxis)
                        .build();

        // Finding the domain and range to plot the graph in
        this.yMin = yMin(data) - 3;
        this.yMax = yMax(data) + 3;
        this.xMin = xMin(data) - 3;
        this.xMax = xMax(data) + 3;

        // Set the domain and range of the graph.
        chart.getStyler().setYAxisMin(yMin);
        chart.getStyler().setYAxisMax(yMax);
        chart.getStyler().setXAxisMin(xMin);
        chart.getStyler().setXAxisMax(xMax);

        // Create the scatter plot of the data
        XYSeries raw = chart.addSeries("raw", data.get(0), data.get(1));
        raw.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

        // Plot the initial function with multiple points and join them with a line
        List<List<Double>> plotPoints = Data.plotFunction(xMin, xMax, .1, (x)->0);

        // To draw the function.
        XYSeries plt = chart.addSeries("plot", plotPoints.get(0), plotPoints.get(1));
        plt.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
        plt.setShowInLegend(false);
        plt.setMarker(SeriesMarkers.NONE);

        SwingUtilities.invokeLater(() -> {

                // Create and set up the window.
                frame = new JFrame(title);
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                // chart
                JPanel chartPanel = new XChartPanel<>(chart);
                frame.add(chartPanel, BorderLayout.CENTER);

                // Display the window.
                frame.pack();
                frame.setVisible(true);
        });

    }

    private double yMin(List<List<Double>> data) {
        double min =  Collections.min(data.get(1));
        return (min >= 0) ? 0 : min;
    }

    private double yMax(List<List<Double>> data) {
        return Collections.max(data.get(1));
    }

    private double xMin(List<List<Double>> data) {
        double min =  Collections.min(data.get(0));
        return (min >= 0) ? 0 : min;
    }

    private double xMax(List<List<Double>> data) {
        return Collections.max(data.get(0));
    }

    public void updatePlot(HypothesisFunction h_x) {
        List<List<Double>> plotPoints = Data.plotFunction(xMin,xMax, .1, h_x);
        chart.updateXYSeries("plot",plotPoints.get(0), plotPoints.get(1), null);
        frame.repaint();
    }

}

interface HypothesisFunction {
    double evaluate(double x);
}


	
