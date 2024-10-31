package org.example;

import org.example.algorithms.BasicMatrixMultiplication;
import org.example.algorithms.SparseMatrixMultiplication;
import org.example.algorithms.StrassenMatrixMultiplication;
import org.example.model.DenseMatrix;
import org.example.model.SparseMatrix;
import org.example.utils.MatrixGenerator;
import org.example.utils.PerformanceAnalyzer;

public class Main {

	public static void main(String[] args) {
		int matrixSize = 500;  // Tamaño de la matriz

		DenseMatrix denseA = MatrixGenerator.generateDenseMatrix(matrixSize, matrixSize);
		DenseMatrix denseB = MatrixGenerator.generateDenseMatrix(matrixSize, matrixSize);

		SparseMatrix sparseA = MatrixGenerator.generateSparseMatrix(matrixSize, matrixSize, 0.1);  // 10% no cero
		SparseMatrix sparseB = MatrixGenerator.generateSparseMatrix(matrixSize, matrixSize, 0.1);

		BasicMatrixMultiplication basicMult = new BasicMatrixMultiplication();
		StrassenMatrixMultiplication strassenMult = new StrassenMatrixMultiplication();
		SparseMatrixMultiplication sparseMult = new SparseMatrixMultiplication();

		System.out.println("Testing Basic Matrix Multiplication...");
		long basicTime = PerformanceAnalyzer.measureExecutionTime(() -> basicMult.multiply(denseA, denseB));
		System.out.println("Execution Time: " + basicTime + " ns");

		System.out.println("Testing Strassen Matrix Multiplication...");
		long strassenTime = PerformanceAnalyzer.measureExecutionTime(() -> strassenMult.multiply(denseA, denseB));
		System.out.println("Execution Time: " + strassenTime + " ns");

		System.out.println("Testing Sparse Matrix Multiplication...");
		long sparseTime = PerformanceAnalyzer.measureExecutionTime(() -> sparseMult.multiply(sparseA, sparseB));
		System.out.println("Execution Time: " + sparseTime + " ns");
	}
}