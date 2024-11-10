import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.util.Arrays;
import java.util.List;

public class MatrixMultiplicationGraphsMemoryUsage {

	public static void main(String[] args) {
		// Define sparsity levels
		List<Double> sparsityLevels = Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0);

		// Define memory usage for each algorithm and matrix size
		List<Long> cscMemory10 = Arrays.asList(62936L, 41960L, 41960L, 41960L, 41960L);
		List<Long> csrMemory10 = Arrays.asList(41960L, 41960L, 41960L, 41960L, 41960L);
		List<Long> strassenMemory10 = Arrays.asList(125920L, 125920L, 125920L, 125920L, 125920L);
		List<Long> cacheMemory10 = Arrays.asList(125928L, 125928L, 125928L, 125928L, 125928L);
		List<Long> basicMemory10 = Arrays.asList(125928L, 125928L, 125928L, 125928L, 125928L);

		List<Long> cscMemory50 = Arrays.asList(207024L, 161568L, 222592L, 219440L, 100376L);
		List<Long> csrMemory50 = Arrays.asList(165064L, 161568L, 170304L, 219440L, 100376L);
		List<Long> strassenMemory50 = Arrays.asList(7740256L, 6644816L, 9679680L, 8047952L, 6834352L);
		List<Long> cacheMemory50 = Arrays.asList(2972496L, 1819328L, 1444608L, 5447456L, 2927584L);
		List<Long> basicMemory50 = Arrays.asList(5031936L, 5051296L, 5036176L, 2925576L, 3147792L);

		List<Long> cscMemory100 = Arrays.asList(711208L, 720608L, 719936L, 719640L, 144480L);
		List<Long> csrMemory100 = Arrays.asList(711208L, 720608L, 719936L, 719640L, 103208L);
		List<Long> strassenMemory100 = Arrays.asList(47044176L, 45145392L, 42716888L, 39196160L, 34084896L);
		List<Long> cacheMemory100 = Arrays.asList(42346240L, 4269664L, 1125856L, 42274864L, 35653648L);
		List<Long> basicMemory100 = Arrays.asList(20942304L, 35526400L, 35540920L, 27153408L, 33556496L);

		List<Long> cscMemory500 = Arrays.asList(23595008L, 23595008L, 23595008L, 20632088L, 188448L);
		List<Long> csrMemory500 = Arrays.asList(23595008L, 23595008L, 23595008L, 20977992L, 104704L);
		List<Long> strassenMemory500 = Arrays.asList(432046416L, 504883200L, 459251712L, 134112768L, 23102016L);
		List<Long> cacheMemory500 = Arrays.asList(55973096L, 194314048L, 40049680L, 228606240L, 189796864L);
		List<Long> basicMemory500 = Arrays.asList(107698304L, 102713344L, 42080576L, 251495712L, 160965136L);

		List<Long> cscMemory1000 = Arrays.asList(84934656L, 87031808L, 85458944L, 73716576L, 146376L);
		List<Long> csrMemory1000 = Arrays.asList(85335120L, 87031808L, 86507520L, 85691744L, 104560L);
		List<Long> strassenMemory1000 = Arrays.asList(1295764992L, 1165415424L, 569591808L, 765064192L, 24837168L);
		List<Long> cacheMemory1000 = Arrays.asList(214314744L, 448457632L, 140951904L, 273567120L, 141560320L);
		List<Long> basicMemory1000 = Arrays.asList(312529712L, 153137560L, 172867984L, 180389104L, 202381840L);

		createComparisonChart("Matrix Size: 10x10", sparsityLevels, cscMemory10, csrMemory10, strassenMemory10, cacheMemory10, basicMemory10);
		createComparisonChart("Matrix Size: 50x50", sparsityLevels, cscMemory50, csrMemory50, strassenMemory50, cacheMemory50, basicMemory50);
		createComparisonChart("Matrix Size: 100x100", sparsityLevels, cscMemory100, csrMemory100, strassenMemory100, cacheMemory100, basicMemory100);
		createComparisonChart("Matrix Size: 500x500", sparsityLevels, cscMemory500, csrMemory500, strassenMemory500, cacheMemory500, basicMemory500);
		createComparisonChart("Matrix Size: 1000x1000", sparsityLevels, cscMemory1000, csrMemory1000, strassenMemory1000, cacheMemory1000, basicMemory1000);
	}

	private static void createComparisonChart(String title, List<Double> sparsityLevels, List<Long> cscMemory, List<Long> csrMemory, List<Long> strassenMemory, List<Long> cacheMemory, List<Long> basicMemory) {
		XYChart chart = new XYChartBuilder().width(800).height(600).title(title).xAxisTitle("Sparsity Level").yAxisTitle("Memory Usage (bytes)").build();

		// Configure chart style
		chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
		chart.getStyler().setMarkerSize(5);

		// Add each series
		chart.addSeries("CSC", sparsityLevels, cscMemory);
		chart.addSeries("CSR", sparsityLevels, csrMemory);
		chart.addSeries("Strassen", sparsityLevels, strassenMemory);
		chart.addSeries("Cache", sparsityLevels, cacheMemory);
		chart.addSeries("Basic", sparsityLevels, basicMemory);

		// Display the chart
		new SwingWrapper<>(chart).displayChart();
	}
}

