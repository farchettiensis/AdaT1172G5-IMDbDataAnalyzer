package edu.ada.t1172.groupfive.imdbdataanalyzer.util;

import edu.ada.t1172.groupfive.imdbdataanalyzer.model.Movie;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MovieChart extends JFrame {
    private List<Movie> movies;

    public MovieChart(String title, List<Movie> movies) {
        super(title);
        this.movies = movies;
    }

    public void initForTendency() {
        ChartPanel chartPanel = new ChartPanel(createChartForTendency());
        setContentPane(chartPanel);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
    }

    private XYDataset createDatasetForTendency() {
        movies.sort(Comparator.comparing(Movie::getReleaseYear).thenComparing(Movie::getTitle));
        Map<Integer, Double> moviesGroupedByYear_Average = movies.stream()
                .collect(Collectors.groupingBy(Movie::getReleaseYear, Collectors.averagingDouble(Movie::getAverageRating)));

        XYSeries avgRate = new XYSeries("Average Rating");

        for (var entry : moviesGroupedByYear_Average.entrySet()) {
            avgRate.add(entry.getKey(), entry.getValue());
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(avgRate);

        return dataset;
    }

    private JFreeChart createChartForTendency() {
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Score tendency over the years",
                "Release year",
                "Average Rating",
                createDatasetForTendency(),
                PlotOrientation.VERTICAL,
                true, true, false);

        XYPlot plot = chart.getXYPlot();

        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        yAxis.setRange(7.5, yAxis.getUpperBound());
        return chart;
    }
}