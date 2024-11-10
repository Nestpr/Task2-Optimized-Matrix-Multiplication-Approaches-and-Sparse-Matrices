import org.example.algorithms.*;
import org.example.model.SparseMatrix;
import org.example.utils.MatrixGenerator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class MatrixMultiplicationTestMemoryUsage {

	private static final List<Integer> MATRIX_SIZES = Arrays.asList(10, 50, 100, 500, 1000);
	private static final List<Double> SPARSITY_LEVELS = Arrays.asList(0.0, 0.25, 0.5, 0.75, 1.0);

	@Test
	public void compareMemoryUsageAndPrintResults() {
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

				// CSC Matrix Multiplication Memory Usage
				long cscMemory = measureMemoryUsage(() -> cscMult.multiply(cscMatrix1, cscMatrix2));
				System.out.println("  Sparsity Level: " + sparsity + " | CSC Matrix Multiplication Memory Usage: " + cscMemory + " bytes");

				// CSR Matrix Multiplication Memory Usage
				long csrMemory = measureMemoryUsage(() -> csrMult.multiply(csrMatrix1, csrMatrix2));
				System.out.println("  Sparsity Level: " + sparsity + " | CSR Matrix Multiplication Memory Usage: " + csrMemory + " bytes");

				// Strassen Matrix Multiplication Memory Usage
				long strassenMemory = measureMemoryUsage(() -> strassenMult.multiply(sparseA, sparseB));
				System.out.println("  Sparsity Level: " + sparsity + " | Strassen Matrix Multiplication Memory Usage: " + strassenMemory + " bytes");

				// Cache Optimized Matrix Multiplication Memory Usage
				long cacheMemory = measureMemoryUsage(() -> cacheMult.multiply(sparseA, sparseB));
				System.out.println("  Sparsity Level: " + sparsity + " | Cache Matrix Multiplication Memory Usage: " + cacheMemory + " bytes");

				// Basic Matrix Multiplication Memory Usage
				long basicMemory = measureMemoryUsage(() -> basicMult.multiply(sparseA, sparseB));
				System.out.println("  Sparsity Level: " + sparsity + " | Basic Matrix Multiplication Memory Usage: " + basicMemory + " bytes");

				System.out.println();
			}
			System.out.println("-----------------------------------------------------");
		}
	}

	// Utility method to measure memory usage
	private long measureMemoryUsage(Runnable task) {
		System.gc(); // Run garbage collector to minimize noise
		long beforeMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		task.run();
		long afterMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		return afterMemory - beforeMemory;
	}
}
