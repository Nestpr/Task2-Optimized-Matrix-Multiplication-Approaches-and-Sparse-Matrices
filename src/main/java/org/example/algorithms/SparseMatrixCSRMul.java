package org.example.algorithms;

import org.example.model.SparseMatrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SparseMatrixCSRMul {

	public static class CSRMatrix {
		double[] values;          // Non-zero values
		int[] columnIndices;      // Column indices corresponding to values
		int[] rowPointers;        // Row pointers
		int rows, cols;           // Number of rows and columns in the matrix

		public CSRMatrix(double[] values, int[] columnIndices, int[] rowPointers, int rows, int cols) {
			this.values = values;
			this.columnIndices = columnIndices;
			this.rowPointers = rowPointers;
			this.rows = rows;
			this.cols = cols;
		}

		// Static method to convert DenseMatrix to CSR format
		public static CSRMatrix convertToCSR(SparseMatrix matrix) {
			List<Double> valuesList = new ArrayList<>();
			List<Integer> columnIndicesList = new ArrayList<>();
			List<Integer> rowPointersList = new ArrayList<>();
			rowPointersList.add(0);

			int rows = matrix.getRows();
			int cols = matrix.getCols();
			HashMap<Integer, HashMap<Integer, Integer>> elements = matrix.getElements();

			for (int i = 0; i < rows; i++) {
				int nonZeroCount = 0;
				HashMap<Integer, Integer> row = elements.getOrDefault(i, new HashMap<>());
				for (int j = 0; j < cols; j++) {
					int value = row.getOrDefault(j, 0);
					if (value != 0) {
						valuesList.add((double) value);
						columnIndicesList.add(j);
						nonZeroCount++;
					}
				}
				rowPointersList.add(rowPointersList.get(rowPointersList.size() - 1) + nonZeroCount);
			}

			double[] values = valuesList.stream().mapToDouble(Double::doubleValue).toArray();
			int[] columnIndices = columnIndicesList.stream().mapToInt(Integer::intValue).toArray();
			int[] rowPointers = rowPointersList.stream().mapToInt(Integer::intValue).toArray();

			return new CSRMatrix(values, columnIndices, rowPointers, rows, cols);
		}
	}
	public static CSRMatrix multiply(CSRMatrix A, CSRMatrix B) {
		if (A.cols != B.rows) {
			throw new IllegalArgumentException("Matrix dimensions do not match for multiplication.");
		}

		List<Double> resultValues = new ArrayList<>();
		List<Integer> resultColumnIndices = new ArrayList<>();
		List<Integer> resultRowPointers = new ArrayList<>();
		resultRowPointers.add(0);

		double[] rowResult = new double[B.cols];

		// Perform CSR matrix multiplication (A * B)
		for (int i = 0; i < A.rows; i++) {
			Arrays.fill(rowResult, 0.0);

			// For each non-zero element in row i of matrix A
			for (int j = A.rowPointers[i]; j < A.rowPointers[i + 1]; j++) {
				int colA = A.columnIndices[j];
				double valA = A.values[j];

				// Multiply row of A by corresponding row in B
				for (int k = B.rowPointers[colA]; k < B.rowPointers[colA + 1]; k++) {
					int colB = B.columnIndices[k];
					double valB = B.values[k];
					rowResult[colB] += valA * valB;
				}
			}

			int nonZeroCount = 0;
			for (int j = 0; j < B.cols; j++) {
				if (rowResult[j] != 0.0) {
					resultValues.add(rowResult[j]);
					resultColumnIndices.add(j);
					nonZeroCount++;
				}
			}
			resultRowPointers.add(resultRowPointers.get(resultRowPointers.size() - 1) + nonZeroCount);
		}

		double[] resultValuesArray = resultValues.stream().mapToDouble(Double::doubleValue).toArray();
		int[] resultColumnIndicesArray = resultColumnIndices.stream().mapToInt(Integer::intValue).toArray();
		int[] resultRowPointersArray = resultRowPointers.stream().mapToInt(Integer::intValue).toArray();

		return new CSRMatrix(resultValuesArray, resultColumnIndicesArray, resultRowPointersArray, A.rows, B.cols);
	}
}
