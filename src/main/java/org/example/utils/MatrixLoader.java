package org.example.utils;

import org.example.algorithms.SparseMatrixCSCMul;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MatrixLoader {

	public static SparseMatrixCSCMul.CSCMatrix loadMatrixFromMTX(String filePath) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		String line;
		int rows = 0, cols = 0, nonZeros = 0;

		// Skip comments
		while ((line = reader.readLine()) != null) {
			if (line.startsWith("%")) continue; // Comment line, skip
			else break; // Found matrix dimensions line
		}

		// Read dimensions and non-zero entries count
		String[] dims = line.trim().split("\\s+");
		rows = Integer.parseInt(dims[0]);
		cols = Integer.parseInt(dims[1]);
		nonZeros = Integer.parseInt(dims[2]);

		List<Double> valuesList = new ArrayList<>();
		List<Integer> rowIndicesList = new ArrayList<>();
		List<Integer> colPointersList = new ArrayList<>();

		int currentCol = -1;
		int colPointerStart = 0;

		// Initialize the column pointers with the starting point
		colPointersList.add(0);

		// Read non-zero values in CSC format
		while ((line = reader.readLine()) != null) {
			String[] parts = line.trim().split("\\s+");
			int row = Integer.parseInt(parts[0]) - 1; // Convert 1-based to 0-based index
			int col = Integer.parseInt(parts[1]) - 1; // Convert 1-based to 0-based index
			double value = Double.parseDouble(parts[2]);

			// If we reach a new column, update the column pointers list
			if (col != currentCol) {
				for (int i = currentCol + 1; i <= col; i++) {
					colPointersList.add(colPointerStart);
				}
				currentCol = col;
			}

			valuesList.add(value);
			rowIndicesList.add(row);
			colPointerStart++;
		}

		// Finalize the column pointers for the remaining columns
		for (int i = currentCol + 1; i <= cols; i++) {
			colPointersList.add(colPointerStart);
		}

		reader.close();

		// Convert lists to arrays for CSCMatrix creation
		double[] values = valuesList.stream().mapToDouble(Double::doubleValue).toArray();
		int[] rowIndices = rowIndicesList.stream().mapToInt(Integer::intValue).toArray();
		int[] colPointers = colPointersList.stream().mapToInt(Integer::intValue).toArray();

		return new SparseMatrixCSCMul.CSCMatrix(values, rowIndices, colPointers, rows, cols);
	}
}
