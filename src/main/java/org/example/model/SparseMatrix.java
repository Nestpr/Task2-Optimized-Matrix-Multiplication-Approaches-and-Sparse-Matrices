package org.example.model;

import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;

import java.util.HashMap;

public class SparseMatrix {
	private final int rows;
	private final int cols;
	private final HashMap<Integer, HashMap<Integer, Integer>> elements;

	public SparseMatrix(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.elements = new HashMap<>();
	}

	public void addElement(int row, int col, int value) {
		if (value != 0) {
			elements.computeIfAbsent(row, k -> new HashMap<>()).put(col, value);
		}
	}
	public int getElementValue(int row, int col) {
		return elements.getOrDefault(row, new HashMap<>()).getOrDefault(col, 0);
	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public HashMap<Integer, HashMap<Integer, Integer>> getElements() {
		return elements;
	}
}
