package ua.mpumnia.matrix.generics;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ua.mpumnia.matrix.Dimension;
import ua.mpumnia.matrix.exceptions.MatrixIllegalDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixIncompatibleDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixInverseException;
import ua.mpumnia.matrix.exceptions.MatrixOutOfBoundsException;

public class GenericMatrixTest {

    @Test
    void defaultMatrixConstructorShouldCreateMatrix0x0() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(new DoubleProcessor());
        assertEquals(new Dimension(0, 0),
                matrix.getDimension(),
                "Dimension mismatch");
    }

    @Test
    void shouldCreateMatrix3x5() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(3, 5, new DoubleProcessor());
        assertEquals(new Dimension(3, 5),
                matrix.getDimension(),
                "Dimension mismatch");
    }

    @Test
    void testConstructorWithDimensionShouldCreateMatrix3x5() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(new Dimension(3, 5), new DoubleProcessor());
        assertEquals(new Dimension(3, 5),
                matrix.getDimension(),
                "Dimension mismatch");
    }

    @Test
    void shouldThrowMatrixIllegalDimensionException() {
        assertThrows(MatrixIllegalDimensionException.class,
                () -> new GenericMatrix<>(-3, 5, new DoubleProcessor()),
                "MatrixIllegalDimensionException wasn't thrown");
    }

    @Test
    void setAndGetValueInMatrixBoundsShouldWorkWithoutAnyException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        matrix.setValue(3, 2, 5.);
        assertEquals(5, matrix.getValue(3, 2),
                "Set or get works incorrectly");
    }

    @Test
    void setValueOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setValue(4, 0, 0.),
                "MatrixOutOfBoundsException wasn't thrown");
    }

    @Test
    void getValueOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.getValue(0, 3),
                "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setRowUsingArray")
    @Test
    void setRowUsingArrayWhichFitsMatrixShouldWorkWithoutAnyException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        matrix.setRow(1, 1., 2., 3.);
        assertEquals(1, matrix.getValue(1, 0));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(3, matrix.getValue(1, 2));
    }

    @Tag("setRowUsingArray")
    @Test
    void setRowUsingArrayOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setRow(-1, 1., 2., 3.),
                "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setRowUsingArray")
    @Test
    void setRowUsingArrayWhichDoesNotFitMatrixShouldThrowMatrixIncompatibleDimensionException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> matrix.setRow(1, 1., 2., 3., 4.),
                "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Tag("setColumnUsingArray")
    @Test
    void setColumnUsingArrayWhichFitsMatrixShouldWorkWithoutAnyException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        matrix.setColumn(1, 1., 2., 3., 4.);
        assertEquals(1, matrix.getValue(0, 1));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(3, matrix.getValue(2, 1));
        assertEquals(4, matrix.getValue(3, 1));
    }

    @Tag("setColumnUsingArray")
    @Test
    void setColumnUsingArrayOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setColumn(-1, 1., 2., 3., 4.),
                "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setColumnUsingArray")
    @Test
    void setColumnUsingArrayWhichDoesNotFitMatrixShouldThrowMatrixIncompatibleDimensionException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> matrix.setColumn(1, 1., 2., 3.),
                "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Tag("setRowUsingRowMatrix")
    @Test
    void setRowUsingRowMatrixWhichFitsMatrixShouldWorkWithoutAnyException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        GenericMatrix<Double> rowToSet = new GenericMatrix<>(1, 3, new DoubleProcessor());
        rowToSet.setValue(0, 0, 1.);
        rowToSet.setValue(0, 1, 2.);
        rowToSet.setValue(0, 2, 3.);
        matrix.setRow(1, rowToSet);
        assertEquals(1, matrix.getValue(1, 0));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(3, matrix.getValue(1, 2));
    }

    @Tag("setRowUsingRowMatrix")
    @Test
    void setRowUsingRowMatrixOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        GenericMatrix<Double> rowToSet = new GenericMatrix<>(1, 3, new DoubleProcessor());
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setRow(-1, rowToSet),
                "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setRowUsingRowMatrix")
    @Test
    void setRowUsingRowMatrixWhichDoesNotFitMatrixShouldThrowMatrixIncompatibleDimensionException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        GenericMatrix<Double> rowToSet = new GenericMatrix<>(1, 4, new DoubleProcessor());
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> matrix.setRow(1, rowToSet),
                "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Tag("setColumnUsingColumnMatrix")
    @Test
    void setColumnUsingColumnMatrixWhichFitsMatrixShouldWorkWithoutAnyException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        GenericMatrix<Double> columnToSet = new GenericMatrix<>(4, 1, new DoubleProcessor());
        columnToSet.setValue(0, 0, 1.);
        columnToSet.setValue(1, 0, 2.);
        columnToSet.setValue(2, 0, 3.);
        columnToSet.setValue(3, 0, 4.);
        matrix.setColumn(1, columnToSet);
        assertEquals(1, matrix.getValue(0, 1));
        assertEquals(2, matrix.getValue(1, 1));
        assertEquals(3, matrix.getValue(2, 1));
        assertEquals(4, matrix.getValue(3, 1));
    }

    @Tag("setColumnUsingColumnMatrix")
    @Test
    void setColumnUsingColumnMatrixOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        GenericMatrix<Double> columnToSet = new GenericMatrix<>(4, 1, new DoubleProcessor());
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.setColumn(-1, columnToSet),
                "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("setColumnUsingColumnMatrix")
    @Test
    void setColumnUsingColumnMatrixWhichDoesNotFitMatrixShouldThrowMatrixIncompatibleDimensionException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        GenericMatrix<Double> columnToSet = new GenericMatrix<>(3, 1, new DoubleProcessor());
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> matrix.setColumn(1, columnToSet),
                "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Tag("getRow")
    @Test
    void getRowInMatrixBoundsShouldWorkWithoutAnyException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        matrix.setRow(1, 1., 2., 3.);
        GenericMatrix<Double> row = matrix.getRow(1);
        assertEquals(1, row.getValue(0, 0));
        assertEquals(2, row.getValue(0, 1));
        assertEquals(3, row.getValue(0, 2));
    }

    @Tag("getRow")
    @Test
    void getRowOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.getRow(5),
                "MatrixOutOfBoundsException wasn't thrown");
    }

    @Tag("getColumn")
    @Test
    void getColumnInMatrixBoundsShouldWorkWithoutAnyException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        matrix.setColumn(2, 1., 2., 3., 4.);
        GenericMatrix<Double> column = matrix.getColumn(2);
        assertEquals(1, column.getValue(0, 0));
        assertEquals(2, column.getValue(1, 0));
        assertEquals(3, column.getValue(2, 0));
        assertEquals(4, column.getValue(3, 0));
    }

    @Tag("getColumn")
    @Test
    void getColumnOutOfMatrixBoundsShouldThrowMatrixOutOfBoundsException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixOutOfBoundsException.class,
                () -> matrix.getColumn(3),
                "MatrixOutOfBoundsException wasn't thrown");
    }

    @Test
    void testCopyConstructor() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(3, 5, new DoubleProcessor());
        matrix.setRow(0, 1., 2., 3., 4., 5.);
        matrix.setRow(1, 6., 7., 8., 9., 1.);
        matrix.setRow(2, 2., 3., 4., 5., 6.);
        GenericMatrix<Double> copy = new GenericMatrix<>(matrix);
        assertEquals(matrix, copy, "GenericMatrix<Double> wasn't copied");
    }

    @Test
    void addMatricesWithCompatibleDimensions() {
        GenericMatrix<Double> m1 = new GenericMatrix<>(3, 3, new DoubleProcessor());
        m1.setRow(0, 2., 4., 6.);
        m1.setRow(1, 1., 5., 9.);
        m1.setRow(2, 8., 3., 5.);
        GenericMatrix<Double> m2 = new GenericMatrix<>(3, 3, new DoubleProcessor());
        m2.setRow(0, 1., 4., -3.);
        m2.setRow(1, 1., -4., 3.);
        m2.setRow(2, -1., 4., 3.);
        GenericMatrix<Double> sum = m1.add(m2);
        assertSame(sum, m1, "Returned matrix is new object");
        GenericMatrix<Double> expected = new GenericMatrix<>(3, 3, new DoubleProcessor());
        expected.setRow(0, 3., 8., 3.);
        expected.setRow(1, 2., 1., 12.);
        expected.setRow(2, 7., 7., 8.);
        assertEquals(expected, sum, "Incorrect matrix sum");
    }

    @Test
    void addMatricesWithIncompatibleDimensionsShouldThrowMatrixIncompatibleDimensionException() {
        GenericMatrix<Double> m1 = new GenericMatrix<>(3, 3, new DoubleProcessor());
        m1.setRow(0, 2., 4., 6.);
        m1.setRow(1, 1., 5., 9.);
        m1.setRow(2, 8., 3., 5.);
        GenericMatrix<Double> m2 = new GenericMatrix<>(2, 3, new DoubleProcessor());
        m2.setRow(0, 1., 4., -3.);
        m2.setRow(1, 1., -4., 3.);
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> m1.add(m2),
                "MatrixIncompatibleDimensionException wasn't thrown");

    }

    @Test
    void multiplyMatrixByScalar() {
        GenericMatrix<Double> m1 = new GenericMatrix<>(3, 3, new DoubleProcessor());
        m1.setRow(0, 2., 4., 6.);
        m1.setRow(1, 1., 5., 9.);
        m1.setRow(2, 8., 3., 5.);
        GenericMatrix<Double> scaled = m1.multiply(2);
        assertSame(scaled, m1, "Returned matrix is new object");
        GenericMatrix<Double> expected = new GenericMatrix<>(3, 3, new DoubleProcessor());
        expected.setRow(0, 4., 8., 12.);
        expected.setRow(1, 2., 10., 18.);
        expected.setRow(2, 16., 6., 10.);
        assertEquals(expected, scaled, "Incorrect matrix scalar multiplication");
    }

    @Test
    void testTranspose() {
        GenericMatrix<Double> m1 = new GenericMatrix<>(4, 3, new DoubleProcessor());
        m1.setRow(0, 2., 4., 6.);
        m1.setRow(1, 1., 5., 9.);
        m1.setRow(2, 8., 3., 5.);
        m1.setRow(3, 6., 2., 1.);
        GenericMatrix<Double> transposed = m1.transpose();
        assertSame(transposed, m1, "Returned matrix is new object");
        GenericMatrix<Double> expected = new GenericMatrix<>(3, 4, new DoubleProcessor());
        expected.setRow(0, 2., 1., 8., 6.);
        expected.setRow(1, 4., 5., 3., 2.);
        expected.setRow(2, 6., 9., 5., 1.);
        assertEquals(expected, transposed, "Incorrect matrix transposition");
    }

    @Test
    void multiplyMatricesWithCompatibleDimensions() {
        GenericMatrix<Double> m1 = new GenericMatrix<>(3, 2, new DoubleProcessor());
        m1.setRow(0, 1., 1.);
        m1.setRow(1, 0., 1.);
        m1.setRow(2, 1., 0.);
        GenericMatrix<Double> m2 = new GenericMatrix<>(2, 4, new DoubleProcessor());
        m2.setRow(0, 0., 0., 1., 1.);
        m2.setRow(1, 1., 1., 0., 1.);
        GenericMatrix<Double> product = m1.multiply(m2);
        assertSame(product, m1, "Returned matrix is new object");
        GenericMatrix<Double> expected = new GenericMatrix<>(3, 4, new DoubleProcessor());
        expected.setRow(0, 1., 1., 1., 2.);
        expected.setRow(1, 1., 1., 0., 1.);
        expected.setRow(2, 0., 0., 1., 1.);
        assertEquals(expected, product, "Incorrect matrix multiplication");
    }


    @Test
    void multiplyMatricesWithIncompatibleDimensionsShouldThrowMatrixIncompatibleDimensionException() {
        GenericMatrix<Double> m1 = new GenericMatrix<>(3, 2, new DoubleProcessor());
        m1.setRow(0, 1., 1.);
        m1.setRow(1, 0., 1.);
        m1.setRow(2, 1., 0.);
        GenericMatrix<Double> m2 = new GenericMatrix<>(3, 3, new DoubleProcessor());
        m2.setRow(0, 0., 0., 1.);
        m2.setRow(1, 1., 1., 0.);
        m2.setRow(2, 0., 1., 0.);
        assertThrows(MatrixIncompatibleDimensionException.class,
                () -> m1.multiply(m2),
                "MatrixIncompatibleDimensionException wasn't thrown");
    }

    @Test
    void testCreateDiagonal() {
        GenericMatrix<Double> diagonal = GenericMatrix.createDiagonal(new DoubleProcessor(), 1., 2., 3., 4.);
        GenericMatrix<Double> expected = new GenericMatrix<>(4, 4, new DoubleProcessor());
        expected.setValue(0, 0, 1.);
        expected.setValue(1, 1, 2.);
        expected.setValue(2, 2, 3.);
        expected.setValue(3, 3, 4.);
        assertEquals(expected, diagonal, "Incorrect diagonal matrix");
    }

    @Test
    void testCreateIdentity() {
        GenericMatrix<Double> identity = GenericMatrix.createIdentity(new DoubleProcessor(), 4);
        GenericMatrix<Double> expected = new GenericMatrix<>(4, 4, new DoubleProcessor());
        expected.setValue(0, 0, 1.);
        expected.setValue(1, 1, 1.);
        expected.setValue(2, 2, 1.);
        expected.setValue(3, 3, 1.);
        assertEquals(expected, identity, "Incorrect identity matrix");
    }

    @Test
    void testCreateIdentityWithNegativeDimensionShouldThrowMatrixIllegalDimensionException() {
        assertThrows(MatrixIllegalDimensionException.class,
                () -> GenericMatrix.createIdentity(new DoubleProcessor(), -4),
                "MatrixIllegalDimensionException wasn't thrown");
    }

    @Test
    void testCreateRandomRow() {
        GenericMatrix<Double> row = GenericMatrix.createRandomRow(new DoubleProcessor(), 4);
        GenericMatrix<Double> notExpected = new GenericMatrix<>(1, 4, new DoubleProcessor());
        assertEquals(new Dimension(1, 4), row.getDimension(), "Dimension mismatch");
        assertNotEquals(notExpected, row, "GenericMatrix<Double> isn't random");
    }

    @Test
    void testCreateRandomRowWithNegativeDimensionShouldThrowMatrixIllegalDimensionException() {
        assertThrows(MatrixIllegalDimensionException.class,
                () -> GenericMatrix.createRandomRow(new DoubleProcessor(), -1),
                "MatrixIllegalDimensionException wasn't thrown");
    }

    @Test
    void testCreateRandomColumn() {
        GenericMatrix<Double> column = GenericMatrix.createRandomColumn(new DoubleProcessor(), 3);
        GenericMatrix<Double> notExpected = new GenericMatrix<>(3, 1, new DoubleProcessor());
        assertEquals(new Dimension(3, 1), column.getDimension(), "Dimension mismatch");
        assertNotEquals(notExpected, column, "GenericMatrix<Double> isn't random");
    }

    @Test
    void testCreateRandomColumnWithNegativeDimensionShouldThrowMatrixIllegalDimensionException() {
        assertThrows(MatrixIllegalDimensionException.class,
                () -> GenericMatrix.createRandomColumn(new DoubleProcessor(), -1),
                "MatrixIllegalDimensionException wasn't thrown");
    }

    @Test
    void testInverse() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(3, 3, new DoubleProcessor());
        matrix.setRow(0, 1., 0., 1.);
        matrix.setRow(1, 0., 1., 1.);
        matrix.setRow(2, 1., 1., 0.);
        GenericMatrix<Double> expected = new GenericMatrix<>(3, 3, new DoubleProcessor());
        expected.setRow(0, 0.5, -0.5, 0.5);
        expected.setRow(1, -0.5, 0.5, 0.5);
        expected.setRow(2, 0.5, 0.5, -0.5);
        assertEquals(expected, matrix.inverse(), "Incorrect inverse matrix");
    }

    @Test
    void inverseOfNonSquareMatrixShouldThrowMatrixInverseException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(4, 3, new DoubleProcessor());
        assertThrows(MatrixInverseException.class,
                matrix::inverse,
                "MatrixInverseException wasn't thrown");
    }

    @Test
    void inverseMatrixWithZeroDeterminantShouldThrowMatrixInverseException() {
        GenericMatrix<Double> matrix = new GenericMatrix<>(3, 3, new DoubleProcessor());
        matrix.setRow(0, 1., 2., 1.);
        matrix.setRow(1, 2., 4., 2.);
        matrix.setRow(2, 1., 1., 0.);
        assertThrows(MatrixInverseException.class,
                matrix::inverse,
                "MatrixInverseException wasn't thrown");
    }

}
