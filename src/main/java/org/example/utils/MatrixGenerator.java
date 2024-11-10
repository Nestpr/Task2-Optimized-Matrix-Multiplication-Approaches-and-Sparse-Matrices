package org.example.utils;

import org.example.model.SparseMatrix;
import java.util.Random;

public class MatrixGenerator {

	public static SparseMatrix generateSparseMatrix(int rows, int cols, double sparsity) {
		Random random = new Random();
		SparseMatrix matrix = new SparseMatrix(rows, cols);
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				// Invertimos la condición para que un mayor valor de sparsity genere más ceros
				if (random.nextDouble() >= sparsity) {
					matrix.addElement(i, j, random.nextInt(10) + 1);  // Agrega un valor aleatorio entre 1 y 10
				}
			}
		}
		return matrix;
	}

}
