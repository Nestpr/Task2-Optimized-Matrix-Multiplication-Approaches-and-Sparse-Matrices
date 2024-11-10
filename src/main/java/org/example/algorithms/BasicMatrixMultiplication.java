package org.example.algorithms;

import org.example.model.SparseMatrix;

public class BasicMatrixMultiplication {

	public SparseMatrix multiply(SparseMatrix a, SparseMatrix b) {
		if (a.getCols() != b.getRows()) {
			throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
		}

		int rowsA = a.getRows();
		int colsB = b.getCols();
		int commonDim = a.getCols();
		SparseMatrix result = new SparseMatrix(rowsA, colsB);

		// Perform multiplication assuming dense matrix handling
		for (int i = 0; i < rowsA; i++) {
			for (int j = 0; j < colsB; j++) {
				int sum = 0;
				for (int k = 0; k < commonDim; k++) {
					int valA = a.getElementValue(i, k);

					int valB = b.getElementValue(k, j);

					sum += valA * valB;
				}
				if (sum != 0) {
					result.addElement(i, j, sum);
				}
			}
		}

		return result;
	}
}
