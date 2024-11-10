package org.example.algorithms;

import org.example.model.SparseMatrix;

public class StrassenMatrixMultiplication {

	private static final int THRESHOLD = 32; // Use basic multiplication for submatrices <= 32x32

	public SparseMatrix multiply(SparseMatrix a, SparseMatrix b) {
		int n = a.getRows();
		if (n != a.getCols() || n != b.getRows() || n != b.getCols()) {
			throw new IllegalArgumentException("Strassen algorithm requires square matrices of equal dimensions.");
		}
		return strassenAlgorithm(a, b);
	}

	private SparseMatrix strassenAlgorithm(SparseMatrix a, SparseMatrix b) {
		int n = a.getRows();

		// Base case: use the basic multiplication for smaller matrices
		if (n <= THRESHOLD) {
			BasicMatrixMultiplication basicMult = new BasicMatrixMultiplication();
			return basicMult.multiply(a, b);
		}

		// Split matrices into 4 submatrices of size (n/2) x (n/2)
		int newSize = n / 2;
		SparseMatrix a11 = new SparseMatrix(newSize, newSize);
		SparseMatrix a12 = new SparseMatrix(newSize, newSize);
		SparseMatrix a21 = new SparseMatrix(newSize, newSize);
		SparseMatrix a22 = new SparseMatrix(newSize, newSize);

		SparseMatrix b11 = new SparseMatrix(newSize, newSize);
		SparseMatrix b12 = new SparseMatrix(newSize, newSize);
		SparseMatrix b21 = new SparseMatrix(newSize, newSize);
		SparseMatrix b22 = new SparseMatrix(newSize, newSize);

		split(a, a11, 0, 0);
		split(a, a12, 0, newSize);
		split(a, a21, newSize, 0);
		split(a, a22, newSize, newSize);

		split(b, b11, 0, 0);
		split(b, b12, 0, newSize);
		split(b, b21, newSize, 0);
		split(b, b22, newSize, newSize);

		// Strassen's multiplications
		SparseMatrix m1 = strassenAlgorithm(add(a11, a22), add(b11, b22));
		SparseMatrix m2 = strassenAlgorithm(add(a21, a22), b11);
		SparseMatrix m3 = strassenAlgorithm(a11, subtract(b12, b22));
		SparseMatrix m4 = strassenAlgorithm(a22, subtract(b21, b11));
		SparseMatrix m5 = strassenAlgorithm(add(a11, a12), b22);
		SparseMatrix m6 = strassenAlgorithm(subtract(a21, a11), add(b11, b12));
		SparseMatrix m7 = strassenAlgorithm(subtract(a12, a22), add(b21, b22));

		// Combine results
		SparseMatrix c11 = add(subtract(add(m1, m4), m5), m7);
		SparseMatrix c12 = add(m3, m5);
		SparseMatrix c21 = add(m2, m4);
		SparseMatrix c22 = add(subtract(add(m1, m3), m2), m6);

		// Join submatrices into the result matrix
		SparseMatrix result = new SparseMatrix(n, n);
		join(c11, result, 0, 0);
		join(c12, result, 0, newSize);
		join(c21, result, newSize, 0);
		join(c22, result, newSize, newSize);

		return result;
	}

	// Matrix addition for SparseMatrix
	private SparseMatrix add(SparseMatrix a, SparseMatrix b) {
		SparseMatrix result = new SparseMatrix(a.getRows(), a.getCols());
		for (int i = 0; i < a.getRows(); i++) {
			for (int j = 0; j < a.getCols(); j++) {
				result.addElement(i, j, a.getElementValue(i, j) + b.getElementValue(i, j));
			}
		}
		return result;
	}

	// Matrix subtraction for SparseMatrix
	private SparseMatrix subtract(SparseMatrix a, SparseMatrix b) {
		SparseMatrix result = new SparseMatrix(a.getRows(), a.getCols());
		for (int i = 0; i < a.getRows(); i++) {
			for (int j = 0; j < a.getCols(); j++) {
				result.addElement(i, j, a.getElementValue(i, j) - b.getElementValue(i, j));
			}
		}
		return result;
	}

	// Split matrix into submatrices
	private void split(SparseMatrix parent, SparseMatrix child, int rowOffset, int colOffset) {
		for (int i = 0; i < child.getRows(); i++) {
			for (int j = 0; j < child.getCols(); j++) {
				child.addElement(i, j, parent.getElementValue(i + rowOffset, j + colOffset));
			}
		}
	}

	// Join submatrices into a matrix
	private void join(SparseMatrix child, SparseMatrix parent, int rowOffset, int colOffset) {
		for (int i = 0; i < child.getRows(); i++) {
			for (int j = 0; j < child.getCols(); j++) {
				parent.addElement(i + rowOffset, j + colOffset, child.getElementValue(i, j));
			}
		}
	}
}
