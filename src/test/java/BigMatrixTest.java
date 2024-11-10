import org.example.utils.MatrixLoader;
import org.example.algorithms.SparseMatrixCSCMul;
import org.junit.Test;
import java.io.IOException;

public class BigMatrixTest {

	@Test
	public void testMatrixMultiplicationFromMTX() {
		String filePath = "src/main/resources/mc2depi.mtx";

		try {
			SparseMatrixCSCMul.CSCMatrix matrix = MatrixLoader.loadMatrixFromMTX(filePath);

			SparseMatrixCSCMul multiplication = new SparseMatrixCSCMul();

			long startTime = System.nanoTime();

			SparseMatrixCSCMul.CSCMatrix resultMatrix = multiplication.multiply(matrix, matrix);

			long endTime = System.nanoTime();

			double duration = (endTime - startTime) / 1_000_000_000.0; // Convert to seconds
			System.out.println("Matrix multiplication completed in: " + duration + " seconds");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
