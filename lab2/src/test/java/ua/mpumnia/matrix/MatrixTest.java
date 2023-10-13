package ua.mpumnia.matrix;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ua.mpumnia.matrix.exceptions.MatrixIllegalDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixIncompatibleDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixOutOfBoundsException;

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

    @Test
    void setAndGetValueInMatrixBoundsShouldWorkWithoutAnyException() {
        Matrix matrix = new Matrix(4, 3);
        matrix.setValue(3, 2, 5);
        assertEquals(5, matrix.getValue(3, 2),
                () -> "Set or get works incorrectly");
    }

    @Test
    void setValueOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        Matrix matrix = new Matrix(4, 3);
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setValue(4, 0, 0),
                () -> "MatrixOutOfBoundsException wasn't thrown");
    }

    @Test
    void getValueOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        Matrix matrix = new Matrix(4, 3);
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.getValue(0, 3),
                () -> "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setRowUsingArray")
    @Test
    void setRowUsingArrayWhichFitsMatrixShouldWorkWithoutAnyException() {
        Matrix matrix = new Matrix(4, 3);
        matrix.setRow(1, 1, 2, 3);
        assertEquals(1, matrix.getValue(1, 0));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(3, matrix.getValue(1, 2));
    }

    @Tag("setRowUsingArray")
    @Test
    void setRowUsingArrayOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        Matrix matrix = new Matrix(4, 3);
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setRow(-1, 1, 2, 3),
                () -> "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setRowUsingArray")
    @Test
    void setRowUsingArrayWhichDoesNotFitMatrixShouldThrowMatrixIncompatibleDimensionException() {
        Matrix matrix = new Matrix(4, 3);
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> matrix.setRow(1, 1, 2, 3, 4),
                () -> "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Tag("setColumnUsingArray")
    @Test
    void setColumnUsingArrayWhichFitsMatrixShouldWorkWithoutAnyException() {
        Matrix matrix = new Matrix(4, 3);
        matrix.setColumn(1, 1, 2, 3, 4);
        assertEquals(1, matrix.getValue(0, 1));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(3, matrix.getValue(2, 1));
        assertEquals(4, matrix.getValue(3, 1));
    }

    @Tag("setColumnUsingArray")
    @Test
    void setColumnUsingArrayOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        Matrix matrix = new Matrix(4, 3);
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setColumn(-1, 1, 2, 3, 4),
                () -> "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setColumnUsingArray")
    @Test
    void setColumnUsingArrayWhichDoesNotFitMatrixShouldThrowMatrixIncompatibleDimensionException() {
        Matrix matrix = new Matrix(4, 3);
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> matrix.setColumn(1, 1, 2, 3),
                () -> "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Tag("setRowUsingRowMatrix")
    @Test
    void setRowUsingRowMatrixWhichFitsMatrixShouldWorkWithoutAnyException() {
        Matrix matrix = new Matrix(4, 3);
        Matrix rowToSet = new Matrix(1, 3);
        rowToSet.setValue(0, 0, 1);
        rowToSet.setValue(0, 1, 2);
        rowToSet.setValue(0, 2, 3);
        matrix.setRow(1, rowToSet);
        assertEquals(1, matrix.getValue(1, 0));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(3, matrix.getValue(1, 2));
    }

    @Tag("setRowUsingRowMatrix")
    @Test
    void setRowUsingRowMatrixOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        Matrix matrix = new Matrix(4, 3);
        Matrix rowToSet = new Matrix(1, 3);
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setRow(-1, rowToSet),
                () -> "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setRowUsingRowMatrix")
    @Test
    void setRowUsingRowMatrixWhichDoesNotFitMatrixShouldThrowMatrixIncompatibleDimensionException() {
        Matrix matrix = new Matrix(4, 3);
        Matrix rowToSet = new Matrix(1, 4);
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> matrix.setRow(1, rowToSet),
                () -> "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Tag("setColumnUsingColumnMatrix")
    @Test
    void setColumnUsingColumnMatrixWhichFitsMatrixShouldWorkWithoutAnyException() {
        Matrix matrix = new Matrix(4, 3);
        Matrix columnToSet = new Matrix(4, 1);
        columnToSet.setValue(0, 0, 1);
        columnToSet.setValue(1, 0, 2);
        columnToSet.setValue(2, 0, 3);
        columnToSet.setValue(3, 0, 4);
        matrix.setColumn(1, columnToSet);
        assertEquals(1, matrix.getValue(0, 1));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(3, matrix.getValue(2, 1));
        assertEquals(4, matrix.getValue(3, 1));
    }

    @Tag("setColumnUsingColumnMatrix")
    @Test
    void setColumnUsingColumnMatrixOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        Matrix matrix = new Matrix(4, 3);
        Matrix columnToSet = new Matrix(4, 1);
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setColumn(-1, columnToSet),
                () -> "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setColumnUsingColumnMatrix")
    @Test
    void setColumnUsingColumnMatrixWhichDoesNotFitMatrixShouldThrowMatrixIncompatibleDimensionException() {
        Matrix matrix = new Matrix(4, 3);
        Matrix columnToSet = new Matrix(3, 1);
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> matrix.setColumn(1, columnToSet),
                () -> "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Tag("getRow")
    @Test
    void getRowInMatrixBoundsShouldWorkWithoutAnyException() {
        Matrix matrix = new Matrix(4, 3);
        matrix.setRow(1, 1, 2, 3);
        Matrix row = matrix.getRow(1);
        assertEquals(1, row.getValue(0, 0));
        assertEquals(2, row.getValue(0, 1));
        assertEquals(3, row.getValue(0, 2));
    }

    @Tag("getRow")
    @Test
    void getRowOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        Matrix matrix = new Matrix(4, 3);
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.getRow(5),
                () -> "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("getColumn")
    @Test
    void getColumnInMatrixBoundsShouldWorkWithoutAnyException() {
        Matrix matrix = new Matrix(4, 3);
        matrix.setColumn(2, 1, 2, 3, 4);
        Matrix column = matrix.getColumn(2);
        assertEquals(1, column.getValue(0, 0));
        assertEquals(2, column.getValue(1, 0));
        assertEquals(3, column.getValue(2, 0));
        assertEquals(4, column.getValue(3, 0));
    }

    @Tag("getColumn")
    @Test
    void getColumnOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        Matrix matrix = new Matrix(4, 3);
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.getColumn(3),
                () -> "MatrixOutOfBoundsException wasn't thrown");
    }

    @Test
    void testCopyConstructor() {
        Matrix matrix = new Matrix(3, 5);
        matrix.setRow(0, 1, 2, 3, 4, 5);
        matrix.setRow(1, 6, 7, 8, 9, 1);
        matrix.setRow(2, 2, 3, 4, 5, 6);
        Matrix copy = new Matrix(matrix);
        assertEquals(matrix, copy, () -> "Matrix wasn't copied");
    }

}
