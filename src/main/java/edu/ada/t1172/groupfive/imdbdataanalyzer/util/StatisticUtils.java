package edu.ada.t1172.groupfive.imdbdataanalyzer.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StatisticUtils {
    public static double calculateCorrelation(List<Double> xList, List<Double> yList) {
        if (xList == null || yList == null || xList.size() != yList.size() || xList.isEmpty()) {
            throw new IllegalArgumentException("As listas não podem ser nulas, devem ter o mesmo tamanho e não podem ser vazias.");
        }

        int n = xList.size();
        double sumX = 0.0, sumY = 0.0, sumXy = 0.0;
        double sumX2 = 0.0, sumY2 = 0.0;

        for (int i = 0; i < n; i++) {
            double x = xList.get(i);
            double y = yList.get(i);

            sumX += x;
            sumY += y;
            sumXy += x * y;
            sumX2 += x * x;
            sumY2 += y * y;
        }

        double numerator = n * sumXy - sumX * sumY;
        double denominator = Math.sqrt((n * sumX2 - sumX * sumX) * (n * sumY2 - sumY * sumY));

        if (denominator == 0) {
            return 0;
        }

        return numerator / denominator;
    }

    public static String interpretCorrelation(double correlation) {
        double absCorrelation = Math.abs(correlation);

        if (absCorrelation >= 0.9) {
            return "Correlação muito forte";
        } else if (absCorrelation >= 0.7) {
            return "Correlação forte";
        } else if (absCorrelation >= 0.5) {
            return "Correlação moderada";
        } else if (absCorrelation >= 0.3) {
            return "Correlação fraca";
        } else if (absCorrelation > 0) {
            return "Correlação muito fraca";
        } else {
            return "Sem correlação";
        }
    }

    public static double percentilCalc(List<? extends Number> valores, double percentil) {
        List<Double> valoresDouble = valores.stream().map(Number::doubleValue).collect(Collectors.toList());
        Collections.sort(valoresDouble);
        return valoresDouble.get((int) Math.ceil(percentil * valoresDouble.size()) - 1);

    }

    public static double linearRegression(List<Double> xList, List<Double> yList) {
        if (xList == null || yList == null || xList.size() != yList.size() || xList.isEmpty()) {
            throw new IllegalArgumentException("As listas não podem ser nulas, devem ter o mesmo tamanho e não podem ser vazias.");
        }

        int n = xList.size();
        double sumX = 0.0, sumY = 0.0, sumXy = 0.0;
        double sumX2 = 0.0;

        for (int i = 0; i < n; i++) {
            double x = xList.get(i);
            double y = yList.get(i);

            sumX += x;
            sumY += y;
            sumXy += x * y;
            sumX2 += x * x;
        }

        return (n * sumXy - sumX * sumY) / (n * sumX2 - Math.pow(sumX, 2));
    }
}
