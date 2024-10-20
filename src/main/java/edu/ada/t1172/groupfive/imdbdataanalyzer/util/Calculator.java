package edu.ada.t1172.groupfive.imdbdataanalyzer.util;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Calculator {

    public static double averageCalc(List<? extends Number> lista) {
        return lista.stream().mapToDouble(Number::doubleValue).average().getAsDouble();
    }

    public static double percentilCalc(List<? extends Number> valores, double percentil) {
        List<Double> valoresDouble = valores.stream().map(Number::doubleValue).collect(Collectors.toList());
        Collections.sort(valoresDouble);
        return valoresDouble.get((int) Math.ceil(percentil * valoresDouble.size()) - 1);

    }
}
