package ua.mpumnia.matrix;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ImmutableMatrixTest {

    @Test
    void setValueShouldThrowUnsupportedOperationException() {
        Matrix matrix = new ImmutableMatrix(3, 4);
        assertThrows(UnsupportedOperationException.class,
                () -> matrix.setValue(0, 0, 1));
    }

    @Test
    void setRowUsingArrayShouldThrowUnsupportedOperationException() {
        Matrix matrix = new ImmutableMatrix(3, 4);
        assertThrows(UnsupportedOperationException.class,
                () -> matrix.setRow(0, 1, 2, 3, 4));
    }

    @Test
    void setRowUsingRowMatrixShouldThrowUnsupportedOperationException() {
        Matrix matrix = new ImmutableMatrix(3, 4);
        Matrix row = new Matrix(1, 4);
        row.setRow(0, 1, 2, 3, 4);
        assertThrows(UnsupportedOperationException.class,
                () -> matrix.setRow(0, row));
    }

    @Test
    void setColumnUsingArrayShouldThrowUnsupportedOperationException() {
        Matrix matrix = new ImmutableMatrix(3, 4);
        assertThrows(UnsupportedOperationException.class,
                () -> matrix.setColumn(0, 1, 2, 3));
    }

    @Test
    void setColumnUsingColumnMatrixShouldThrowUnsupportedOperationException() {
        Matrix matrix = new ImmutableMatrix(3, 4);
        Matrix column = new Matrix(3, 1);
        column.setColumn(0, 1, 2, 3);
        assertThrows(UnsupportedOperationException.class,
                () -> matrix.setColumn(0, column));
    }

    @Test
    void addShouldReturnNewMatrixInstance() {
        Matrix matrix = new Matrix(3, 3);
        matrix.setRow(0, 0, 1, 0);
        matrix.setRow(1, 1, 0, 1);
        matrix.setRow(2, 0, 1, 0);
        Matrix immutable = new ImmutableMatrix(Matrix.createIdentity(3));
        Matrix result = immutable.add(matrix);
        assertNotSame(result, immutable,
                "Instance of immutable matrix was returned");
        assertEquals(Matrix.createIdentity(3), immutable,
                "Immutable matrix was changed");

        Matrix expectedResult = new Matrix(3, 3);
        expectedResult.setRow(0, 1, 1, 0);
        expectedResult.setRow(1, 1, 1, 1);
        expectedResult.setRow(2, 0, 1, 1);
        assertEquals(expectedResult, result,
                "Addition is incorrect");
    }

    @Test
    void multiplyByScalarShouldReturnNewMatrixInstance() {
        Matrix immutable = new ImmutableMatrix(Matrix.createIdentity(3));
        Matrix result = immutable.multiply(2.3);
        assertNotSame(result, immutable,
                "Instance of immutable matrix was returned");
        assertEquals(Matrix.createIdentity(3), immutable,
                "Immutable matrix was changed");

        Matrix expectedResult = new Matrix(3, 3);
        expectedResult.setValue(0, 0, 2.3);
        expectedResult.setValue(1, 1, 2.3);
        expectedResult.setValue(2, 2, 2.3);
        assertEquals(expectedResult, result,
                "Multiplication is incorrect");
    }

    @Test
    void multiplyByMatrixShouldReturnNewMatrixInstance() {
        Matrix m1 = new Matrix(3, 2);
        m1.setRow(0, 1, 1);
        m1.setRow(1, 2, 0);
        m1.setRow(2, 0, 1);
        Matrix m2 = new Matrix(2, 4);
        m2.setRow(0, 1, 0, 1, 1);
        m2.setRow(1, 2, 0, 0, 1);

        Matrix immutable = new ImmutableMatrix(m1);
        Matrix result = immutable.multiply(m2);

        assertNotSame(result, immutable,
                "Instance of immutable matrix was returned");
        assertEquals(m1, immutable,
                "Immutable matrix was changed");

        Matrix expectedResult = new Matrix(3, 4);
        expectedResult.setRow(0, 3, 0, 1, 2);
        expectedResult.setRow(1, 2, 0, 2, 2);
        expectedResult.setRow(2, 2, 0, 0, 1);
        assertEquals(expectedResult, result,
                "Multiplication is incorrect");
    }

    @Test
    void transposeShouldReturnNewMatrixInstance() {
        Matrix m1 = new Matrix(3, 2);
        m1.setRow(0, 1, 1);
        m1.setRow(1, 2, 0);
        m1.setRow(2, 0, 1);

        Matrix immutable = new ImmutableMatrix(m1);
        Matrix result = immutable.transpose();

        assertNotSame(result, immutable,
                "Instance of immutable matrix was returned");
        assertEquals(m1, immutable,
                "Immutable matrix was changed");

        Matrix expectedResult = new Matrix(2, 3);
        expectedResult.setRow(0, 1, 2, 0);
        expectedResult.setRow(1, 1, 0, 1);
        assertEquals(expectedResult, result,
                "Transposition is incorrect");
    }

    @Test
    void inverseShouldReturnNewMatrixInstance() {
        Matrix m1 = new Matrix(3, 3);
        m1.setRow(0, 1, 1, 1);
        m1.setRow(1, 2, 0, 1);
        m1.setRow(2, 0, 1, 0);

        Matrix immutable = new ImmutableMatrix(m1);
        Matrix result = immutable.inverse();

        assertNotSame(result, immutable,
                "Instance of immutable matrix was returned");
        assertEquals(m1, immutable,
                "Immutable matrix was changed");

        Matrix expectedResult = new Matrix(3, 3);
        expectedResult.setRow(0, -1, 1, 1);
        expectedResult.setRow(1, 0, 0, 1);
        expectedResult.setRow(2, 2, -1, -2);
        assertEquals(expectedResult, result,
                "Inversion is incorrect");
    }

}
