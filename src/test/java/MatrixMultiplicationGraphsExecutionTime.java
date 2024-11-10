import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.style.Styler;

import java.util.Arrays;
import java.util.List;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.Arrays;
import java.util.List;

import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.Arrays;
import java.util.List;

public class MatrixMultiplicationGraphsExecutionTime {

	public static void main(String[] args) {
		// Define sparsity levels
		List<Double> sparsityLevels = Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0);

		// Define times for each algorithm and matrix size
		List<Long> cscTimes10 = Arrays.asList(1276189L, 219222L, 183691L, 153078L, 71899L);
		List<Long> csrTimes10 = Arrays.asList(1372388L, 279593L, 192302L, 169839L, 72120L);
		List<Long> strassenTimes10 = Arrays.asList(1571087L, 568460L, 531855L, 470772L, 737056L);
		List<Long> cacheTimes10 = Arrays.asList(2487187L, 1181687L, 662216L, 665075L, 1208324L);
		List<Long> basicTimes10 = Arrays.asList(635087L, 520164L, 507484L, 822292L, 425924L);

		List<Long> cscTimes50 = Arrays.asList(12308671L, 2149437L, 4643984L, 642343L, 178882L);
		List<Long> csrTimes50 = Arrays.asList(10380684L, 2406731L, 1248571L, 496142L, 104362L);
		List<Long> strassenTimes50 = Arrays.asList(29400409L, 71911405L, 35244655L, 20351417L, 16105356L);
		List<Long> cacheTimes50 = Arrays.asList(53763814L, 45881443L, 91514406L, 33806933L, 5784852L);
		List<Long> basicTimes50 = Arrays.asList(16904333L, 37885183L, 11389939L, 16477131L, 6235007L);

		List<Long> cscTimes100 = Arrays.asList(18233855L, 6849437L, 3537554L, 1046332L, 148802L);
		List<Long> csrTimes100 = Arrays.asList(29293028L, 38093315L, 1663483L, 516670L, 64176L);
		List<Long> strassenTimes100 = Arrays.asList(203915962L, 160372841L, 90597621L, 68692592L, 19454762L);
		List<Long> cacheTimes100 = Arrays.asList(235735993L, 142595235L, 112414759L, 134910023L, 21026970L);
		List<Long> basicTimes100 = Arrays.asList(303470896L, 118065424L, 94527231L, 60865118L, 20998981L);

		List<Long> cscTimes500 = Arrays.asList(712581502L, 563829081L, 97531823L, 41260292L, 62160L);
		List<Long> csrTimes500 = Arrays.asList(177738622L, 104832398L, 56267621L, 25925730L, 722238L);
		List<Long> strassenTimes500 = Arrays.asList(6278620345L, 7307488422L, 6643150701L, 6259318324L, 1935860805L);
		List<Long> cacheTimes500 = Arrays.asList(11747820266L, 13206897120L, 13999434901L, 13950619919L, 4093295755L);
		List<Long> basicTimes500 = Arrays.asList(24579055799L, 18866335154L, 15501123930L, 14935363333L, 3445753155L);

		List<Long> cscTimes1000 = Arrays.asList(2919968724L, 1506846053L, 894323812L, 777120631L, 131828L);
		List<Long> csrTimes1000 = Arrays.asList(1752448690L, 1014065564L, 399602740L, 238874952L, 2241696L);
		List<Long> strassenTimes1000 = Arrays.asList(45701080821L, 45915875913L, 41567478881L, 40252279619L, 12237095114L);
		List<Long> cacheTimes1000 = Arrays.asList(95651245921L, 110944693447L, 102742830072L, 103340847258L, 28045672116L);
		List<Long> basicTimes1000 = Arrays.asList(218515449879L, 179347044750L, 153647763992L, 141572638108L, 28812137433L);

		// Generate charts for each matrix size
		createComparisonChart("Matrix Size: 10x10", sparsityLevels, cscTimes10, csrTimes10, strassenTimes10, cacheTimes10, basicTimes10);
		createComparisonChart("Matrix Size: 50x50", sparsityLevels, cscTimes50, csrTimes50, strassenTimes50, cacheTimes50, basicTimes50);
		createComparisonChart("Matrix Size: 100x100", sparsityLevels, cscTimes100, csrTimes100, strassenTimes100, cacheTimes100, basicTimes100);
		createComparisonChart("Matrix Size: 500x500", sparsityLevels, cscTimes500, csrTimes500, strassenTimes500, cacheTimes500, basicTimes500);
		createComparisonChart("Matrix Size: 1000x1000", sparsityLevels, cscTimes1000, csrTimes1000, strassenTimes1000, cacheTimes1000, basicTimes1000);
	}

	private static void createComparisonChart(String title, List<Double> sparsityLevels, List<Long> cscTimes, List<Long> csrTimes, List<Long> strassenTimes, List<Long> cacheTimes, List<Long> basicTimes) {
		// Create the chart
		XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle("Sparsity Level").yAxisTitle("Execution Time (ns)").build();

		// Configure chart style
		chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
		chart.getStyler().setMarkerSize(5);

		// Add each series
		chart.addSeries("CSC", sparsityLevels, cscTimes);
		chart.addSeries("CSR", sparsityLevels, csrTimes);
		chart.addSeries("Strassen", sparsityLevels, strassenTimes);
		chart.addSeries("Cache", sparsityLevels, cacheTimes);
		chart.addSeries("Basic", sparsityLevels, basicTimes);

		// Display the chart
		new SwingWrapper<>(chart).displayChart();
	}
}
