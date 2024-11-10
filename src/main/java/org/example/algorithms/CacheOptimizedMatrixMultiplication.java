package org.example.algorithms;

import org.example.model.SparseMatrix;

public class CacheOptimizedMatrixMultiplication {

	private static final int BLOCK_SIZE = 32; // Define the block size to fit into cache

	public SparseMatrix multiply(SparseMatrix a, SparseMatrix b) {
		int n = a.getRows();
		if (n != a.getCols() || n != b.getRows() || n != b.getCols()) {
			throw new IllegalArgumentException("Matrix dimensions must be square and of the same size.");
		}

		SparseMatrix result = new SparseMatrix(n, n);

		// Perform blocked matrix multiplication
		for (int i = 0; i < n; i += BLOCK_SIZE) {
			for (int j = 0; j < n; j += BLOCK_SIZE) {
				for (int k = 0; k < n; k += BLOCK_SIZE) {
					// Multiply individual blocks
					multiplyBlock(a, b, result, i, j, k);
				}
			}
		}

		return result;
	}

	// Method to multiply blocks of size BLOCK_SIZE and add to result
	private void multiplyBlock(SparseMatrix a, SparseMatrix b, SparseMatrix result, int rowStart, int colStart, int kStart) {
		for (int i = rowStart; i < Math.min(rowStart + BLOCK_SIZE, a.getRows()); i++) {
			for (int k = kStart; k < Math.min(kStart + BLOCK_SIZE, a.getCols()); k++) {
				int valA = a.getElementValue(i, k); // Assume zero if element does not exist

				for (int j = colStart; j < Math.min(colStart + BLOCK_SIZE, b.getCols()); j++) {
					int valB = b.getElementValue(k, j); // Assume zero if element does not exist

					// Accumulate the product in the result matrix
					int currentVal = result.getElementValue(i, j); // Also assume zero if not set
					result.addElement(i, j, currentVal + valA * valB);
				}
			}
		}
	}
}
