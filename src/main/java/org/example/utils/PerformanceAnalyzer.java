package org.example.utils;
public class PerformanceAnalyzer {

	public static long measureExecutionTime(Runnable task) {
		long start = System.nanoTime();
		task.run();
		return System.nanoTime() - start;
	}
}
