package SEIR;

import org.knowm.xchart.XYChart;
import org.knowm.xchart.SwingWrapper;
import SEIR.Seir;
    public class main {
        public static void main(String[] args) {
            double r0 = 2.4, gamma = 0.003, s = 1000000;
            Seir seir = new Seir(3000, 1, s, 4, 1, 0, 0.005, 0.000000072, gamma, 0.03);
            double[][] res = seir.find_answer();
            double[] temp = check(res);
            XYChart xyChart = new XYChart(500, 500);
            xyChart.setXAxisTitle("day");
            xyChart.setYAxisTitle("number of people");
            xyChart.addSeries("S", res[0]);
            xyChart.addSeries("E", res[1]);
            xyChart.addSeries("I", res[2]);
            xyChart.addSeries("R", res[3]);
            xyChart.addSeries("N", temp);
            new SwingWrapper(xyChart).displayChart();
        }
        private static double[] check(double[][] res){
            double[] arr = new double[res[0].length];
            for(int i = 0 ; i < res[0].length; ++i){
                for(int j = 0; j < 4; ++j){
                    arr[i] += res[j][i];
                }
            }
            return arr;
        }
    }
