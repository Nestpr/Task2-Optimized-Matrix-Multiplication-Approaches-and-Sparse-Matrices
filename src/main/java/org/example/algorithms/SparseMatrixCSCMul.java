package org.example.algorithms;
import org.example.model.SparseMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SparseMatrixCSCMul {

	public static class CSCMatrix {
		double[] values;           // Non-zero values
		int[] rowIndices;          // Row indices corresponding to values
		int[] colPointers;         // Column pointers
		int rows, cols;            // Matrix dimensions

		public CSCMatrix(double[] values, int[] rowIndices, int[] colPointers, int rows, int cols) {
			this.values = values;
			this.rowIndices = rowIndices;
			this.colPointers = colPointers;
			this.rows = rows;
			this.cols = cols;
		}

		// Convert a DenseMatrix to CSC format
		public static CSCMatrix convertToCSC(SparseMatrix matrix) {
			List<Double> valuesList = new ArrayList<>();
			List<Integer> rowIndicesList = new ArrayList<>();
			List<Integer> colPointersList = new ArrayList<>();
			colPointersList.add(0);

			int rows = matrix.getRows();
			int cols = matrix.getCols();
			HashMap<Integer, HashMap<Integer, Integer>> elements = matrix.getElements();

			for (int j = 0; j < cols; j++) {
				int nonZeroCount = 0;
				for (int i = 0; i < rows; i++) {
					int value = elements.getOrDefault(i, new HashMap<>()).getOrDefault(j, 0);
					if (value != 0) {
						valuesList.add((double) value);
						rowIndicesList.add(i);
						nonZeroCount++;
					}
				}
				colPointersList.add(colPointersList.get(colPointersList.size() - 1) + nonZeroCount);
			}

			double[] values = valuesList.stream().mapToDouble(Double::doubleValue).toArray();
			int[] rowIndices = rowIndicesList.stream().mapToInt(Integer::intValue).toArray();
			int[] colPointers = colPointersList.stream().mapToInt(Integer::intValue).toArray();

			return new CSCMatrix(values, rowIndices, colPointers, rows, cols);
		}
	}

	// CSC matrix multiplication method
	public static CSCMatrix multiply(CSCMatrix A, CSCMatrix B) {
		if (A.cols != B.rows) {
			throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
		}

		List<Double> resultValues = new ArrayList<>();
		List<Integer> resultRowIndices = new ArrayList<>();
		List<Integer> resultColPointers = new ArrayList<>();
		resultColPointers.add(0);

		double[] colResult = new double[A.rows];
		int[] colNonZeros = new int[A.rows]; // Track non-zero positions in colResult

		// Perform CSC matrix multiplication (A * B)
		for (int jB = 0; jB < B.cols; jB++) {
			Arrays.fill(colResult, 0.0); // Clear colResult for each column in B
			int nonZeroCount = 0;

			// If B's column jB is empty, skip it
			if (B.colPointers[jB] == B.colPointers[jB + 1]) {
				resultColPointers.add(resultValues.size());
				continue;
			}

			// Multiply B's column jB by A
			for (int k = B.colPointers[jB]; k < B.colPointers[jB + 1]; k++) {
				int rowB = B.rowIndices[k];
				double valB = B.values[k];

				// Multiply column jB of B with the row of A
				for (int i = A.colPointers[rowB]; i < A.colPointers[rowB + 1]; i++) {
					int rowA = A.rowIndices[i];
					if (colResult[rowA] == 0) {
						colNonZeros[nonZeroCount++] = rowA; // Track non-zero position
					}
					colResult[rowA] += A.values[i] * valB;
				}
			}

			// Collect non-zero results for this column
			for (int index = 0; index < nonZeroCount; index++) {
				int row = colNonZeros[index];
				if (colResult[row] != 0) {
					resultValues.add(colResult[row]);
					resultRowIndices.add(row);
				}
			}
			resultColPointers.add(resultValues.size()); // Update column pointer
		}

		// Convert lists to arrays
		double[] resultValuesArray = resultValues.stream().mapToDouble(Double::doubleValue).toArray();
		int[] resultRowIndicesArray = resultRowIndices.stream().mapToInt(Integer::intValue).toArray();
		int[] resultColPointersArray = resultColPointers.stream().mapToInt(Integer::intValue).toArray();

		return new CSCMatrix(resultValuesArray, resultRowIndicesArray, resultColPointersArray, A.rows, B.cols);
	}
}
