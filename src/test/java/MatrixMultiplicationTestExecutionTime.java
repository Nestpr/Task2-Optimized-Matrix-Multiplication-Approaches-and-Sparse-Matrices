import org.example.algorithms.*;
import org.example.model.SparseMatrix;
import org.example.utils.MatrixGenerator;
import org.example.utils.PerformanceAnalyzer;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MatrixMultiplicationTestExecutionTime {

	private static final List<Integer> MATRIX_SIZES = Arrays.asList(10, 50, 100, 500, 1000);
	private static final List<Double> SPARSITY_LEVELS = Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0);

	@Test
	public void comparePerformanceAndPrintResults() {
		for (int size : MATRIX_SIZES) {
			System.out.println("Matrix Size: " + size + "x" + size);
			for (double sparsity : SPARSITY_LEVELS) {
				// Generate sparse matrices with the specified sparsity level
				SparseMatrix sparseA = MatrixGenerator.generateSparseMatrix(size, size, sparsity);
				SparseMatrix sparseB = MatrixGenerator.generateSparseMatrix(size, size, sparsity);

				SparseMatrixCSRMul.CSRMatrix csrMatrix1 = SparseMatrixCSRMul.CSRMatrix.convertToCSR(sparseA);
				SparseMatrixCSRMul.CSRMatrix csrMatrix2 = SparseMatrixCSRMul.CSRMatrix.convertToCSR(sparseB);
				SparseMatrixCSCMul.CSCMatrix cscMatrix1 = SparseMatrixCSCMul.CSCMatrix.convertToCSC(sparseA);
				SparseMatrixCSCMul.CSCMatrix cscMatrix2 = SparseMatrixCSCMul.CSCMatrix.convertToCSC(sparseB);

				// Initialize algorithms
				SparseMatrixCSCMul cscMult = new SparseMatrixCSCMul();
				SparseMatrixCSRMul csrMult = new SparseMatrixCSRMul();
				StrassenMatrixMultiplication strassenMult = new StrassenMatrixMultiplication();
				CacheOptimizedMatrixMultiplication cacheMult = new CacheOptimizedMatrixMultiplication();
				BasicMatrixMultiplication basicMult = new BasicMatrixMultiplication();

				// CSC Matrix Multiplication
				long cscTime = PerformanceAnalyzer.measureExecutionTime(() -> cscMult.multiply(cscMatrix1, cscMatrix2));
				System.out.println("  Sparsity Level: " + sparsity + " | CSC Matrix Multiplication Time: " + cscTime + " ns");

				// CSR Matrix Multiplication
				long csrTime = PerformanceAnalyzer.measureExecutionTime(() -> csrMult.multiply(csrMatrix1, csrMatrix2));
				System.out.println("  Sparsity Level: " + sparsity + " | CSR Matrix Multiplication Time: " + csrTime + " ns");

				// Strassen Matrix Multiplication (on sparse matrices)
				long strassenTime = PerformanceAnalyzer.measureExecutionTime(() -> strassenMult.multiply(sparseA, sparseB));
				System.out.println("  Sparsity Level: " + sparsity + " | Strassen Matrix Multiplication Time: " + strassenTime + " ns");

				long cacheTime = PerformanceAnalyzer.measureExecutionTime(() -> cacheMult.multiply(sparseA, sparseB));
				System.out.println("  Sparsity Level: " + sparsity + " | Cache Matrix Multiplication Time: " + cacheTime + " ns");

				long basicTime = PerformanceAnalyzer.measureExecutionTime(() -> basicMult.multiply(sparseA, sparseB));
				System.out.println("  Sparsity Level: " + sparsity + " | Basic Matrix Multiplication Time: " + basicTime + " ns");



				System.out.println();
			}
			System.out.println("-----------------------------------------------------");
		}
	}
}


