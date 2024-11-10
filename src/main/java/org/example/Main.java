package org.example;
import org.example.algorithms.BasicMatrixMultiplication;
import org.example.algorithms.StrassenMatrixMultiplication;
import org.example.model.SparseMatrix;
import org.example.utils.MatrixGenerator;

public class Main {

	public static void main(String[] args) {
		int matrixSize = 500;  // Matrix size

		SparseMatrix sparseA = MatrixGenerator.generateSparseMatrix(matrixSize, matrixSize, 0.1);  // 10% non-zero
		SparseMatrix sparseB = MatrixGenerator.generateSparseMatrix(matrixSize, matrixSize, 0.1);

		BasicMatrixMultiplication basicMult = new BasicMatrixMultiplication();
		StrassenMatrixMultiplication strassenMult = new StrassenMatrixMultiplication();

		System.out.println("Testing Basic Matrix Multiplication...");
		//long basicTime = PerformanceAnalyzer.measureExecutionTime(() -> basicMult.multiply(denseA, denseB));
		//System.out.println("Execution Time: " + basicTime + " ns");

		System.out.println("Testing Strassen Matrix Multiplication...");
		//long strassenTime = PerformanceAnalyzer.measureExecutionTime(() -> strassenMult.multiply(denseA, denseB));
		//System.out.println("Execution Time: " + strassenTime + " ns");

		System.out.println("Testing Sparse Matrix Multiplication...");

	}
}
