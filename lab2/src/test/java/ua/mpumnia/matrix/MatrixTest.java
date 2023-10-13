package ua.mpumnia.matrix;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ua.mpumnia.matrix.exceptions.MatrixIllegalDimensionException;

public class MatrixTest {

    @Test
    void defaultMatrixConstructorShouldCreateMatrix0x0() {
        Matrix matrix = new Matrix();
        assertEquals(new Dimension(0, 0),
                matrix.getDimension(),
                () -> "Dimension mismatch");
    }

    @Test
    void shouldCreateMatrix3x5() {
        Matrix matrix = new Matrix(3, 5);
        assertEquals(new Dimension(3, 5),
                matrix.getDimension(),
                () -> "Dimension mismatch");
    }

    @Test
    void testConstructorWithDimensionShouldCreateMatrix3x5() {
        Matrix matrix = new Matrix(new Dimension(3, 5));
        assertEquals(new Dimension(3, 5),
                matrix.getDimension(),
                () -> "Dimension mismatch");
    }

    @Test
    void shouldThrowMatrixIllegalDimensionException() {
        assertThrows(MatrixIllegalDimensionException.class,
                () -> new Matrix(-3, 5),
                () -> "MatrixIllegalDimensionException wasn't thrown");
    }

}
