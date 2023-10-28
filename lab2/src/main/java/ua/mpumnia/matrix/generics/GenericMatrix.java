package ua.mpumnia.matrix.generics;

import ua.mpumnia.matrix.Dimension;
import ua.mpumnia.matrix.exceptions.MatrixIllegalDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixIncompatibleDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixInverseException;
import ua.mpumnia.matrix.exceptions.MatrixOutOfBoundsException;

import java.util.Arrays;

public class GenericMatrix<T> {

    protected T[][] values;
    private final ElementProcessor<T> elementProcessor;

    public GenericMatrix(ElementProcessor<T> elementProcessor) {
        this(0, 0, elementProcessor);
    }

    public GenericMatrix(int rowsN, int columnsN, ElementProcessor<T> elementProcessor) {
        if (rowsN < 0 || columnsN < 0) {
            throw new MatrixIllegalDimensionException(
                    "Minimum dimension is 0x0, but %dx%d was passed"
                            .formatted(rowsN, columnsN)
            );
        }
        values = (T[][]) new Object[rowsN][columnsN];
        for (int i = 0; i < rowsN; i++) {
            for (int j = 0; j < columnsN; j++) {
                values[i][j] = elementProcessor.zero();
            }
        }
        this.elementProcessor = elementProcessor;
    }

    public GenericMatrix(Dimension dimension, ElementProcessor<T> elementProcessor) {
        this(dimension.rowsN, dimension.columnsN, elementProcessor);
    }

    public GenericMatrix(GenericMatrix<T> matrix) {
        this(matrix.getRowsN(), matrix.getColumnsN(), matrix.elementProcessor);
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            System.arraycopy(matrix.values[rowI], 0, values[rowI], 0, getColumnsN());
        }
    }

    private int getRowsN() {
        return values.length;
    }

    private int getColumnsN() {
        if (getRowsN() == 0) {
            return 0;
        }
        return values[0].length;
    }

    public Dimension getDimension() {
        return new Dimension(getRowsN(), getColumnsN());
    }

    public T getValue(int rowI, int columnI) {
        checkOutOfBounds(rowI, columnI);
        return values[rowI][columnI];
    }

    public void setValue(int rowI, int columnI, T value) {
        checkOutOfBounds(rowI, columnI);
        values[rowI][columnI] = value;
    }

    public GenericMatrix<T> getRow(int rowI) {
        checkOutOfBounds(rowI, 0);
        GenericMatrix<T> row = new GenericMatrix<>(1, getColumnsN(), elementProcessor);
        System.arraycopy(values[rowI], 0, row.values[0], 0, getColumnsN());
        return row;
    }

    public void setRow(int rowI, T... row) {
        checkOutOfBounds(rowI, 0);
        if (getColumnsN() != row.length) {
            throw new MatrixIncompatibleDimensionException();
        }
        System.arraycopy(row, 0, values[rowI], 0, getColumnsN());
    }

    public void setRow(int rowI, GenericMatrix<T> rowMatrix) {
        if (rowMatrix.getRowsN() != 1) {
            throw new MatrixIncompatibleDimensionException();
        }
        setRow(rowI, rowMatrix.values[0]);
    }

    public GenericMatrix<T> getColumn(int columnI) {
        checkOutOfBounds(0, columnI);
        GenericMatrix<T> column = new GenericMatrix<>(getRowsN(), 1, elementProcessor);
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            column.values[rowI][0] = values[rowI][columnI];
        }
        return column;
    }

    public void setColumn(int columnI, T... column) {
        checkOutOfBounds(0, columnI);
        if (getRowsN() != column.length) {
            throw new MatrixIncompatibleDimensionException();
        }
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            values[rowI][columnI] = column[rowI];
        }
    }

    public void setColumn(int columnI, GenericMatrix<T> columnMatrix) {
        checkOutOfBounds(0, columnI);
        if (getRowsN() != columnMatrix.getRowsN() || columnMatrix.getColumnsN() != 1) {
            throw new MatrixIncompatibleDimensionException();
        }
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            values[rowI][columnI] = columnMatrix.values[rowI][0];
        }
    }

    public GenericMatrix<T> add(GenericMatrix<T> matrix) {
        if (matrix.getRowsN() != getRowsN() || matrix.getColumnsN() != getColumnsN()) {
            throw new MatrixIncompatibleDimensionException();
        }
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            for (int columnI = 0; columnI < getColumnsN(); columnI++) {
                values[rowI][columnI] = elementProcessor
                        .sum(values[rowI][columnI], matrix.values[rowI][columnI]);
            }
        }
        return this;
    }

    public GenericMatrix<T>  multiply(double scalar) {
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            for (int columnI = 0; columnI < getColumnsN(); columnI++) {
                values[rowI][columnI] = elementProcessor
                        .scale(values[rowI][columnI], scalar);
            }
        }
        return this;
    }

    public GenericMatrix<T>  multiply(GenericMatrix<T>  matrix) {
        if (getColumnsN() != matrix.getRowsN()) {
            throw new MatrixIncompatibleDimensionException();
        }
        T[][] result = (T[][]) new Object[getRowsN()][matrix.getColumnsN()];
        int elementsN = getColumnsN();
        for (int rowI = 0; rowI < result.length; rowI++) {
            for (int columnI = 0; columnI < result[0].length; columnI++) {
                result[rowI][columnI] = elementProcessor.zero();
                for (int i = 0; i < elementsN; i++) {
                    result[rowI][columnI] = elementProcessor
                            .sum(result[rowI][columnI], elementProcessor
                            .multiply(values[rowI][i], matrix.values[i][columnI]));
                }
            }
        }
        values = result;
        return this;
    }

    public GenericMatrix<T>  transpose() {
        T[][] transposed = (T[][]) new Object[getColumnsN()][getRowsN()];
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            for (int columnI = 0; columnI < getColumnsN(); columnI++) {
                transposed[columnI][rowI] = values[rowI][columnI];
            }
        }
        values = transposed;
        return this;
    }

    public GenericMatrix<T>  inverse() {
        if (getRowsN() != getColumnsN()) {
            throw new MatrixInverseException("Cannot take inverse of non-square matrix");
        }

        int dim = getRowsN();
        T[][] result = (T[][]) new Object[dim][dim];
        T[][] buffer = (T[][]) new Object[dim][dim];

        // Prepare arrays
        for (int i = 0; i < buffer.length; i++) {
            for (int j = 0; j < buffer[0].length; j++) {
                result[i][j] = elementProcessor.zero();
            }
            result[i][i] = elementProcessor.one();
            System.arraycopy(values[i], 0, buffer[i], 0, buffer[0].length);
        }

        // Make zeros under main diagonal
        for (int j = 0; j < dim; j++) {
            int nonZeroI = j;
            while (nonZeroI < dim && elementProcessor.isZero(buffer[nonZeroI][j])) {
                nonZeroI++;
            }
            if (nonZeroI == dim) {
                throw new MatrixInverseException("Inverse matrix does not exist. Determinant is 0");
            }

            swapRows(buffer, nonZeroI, j);
            swapRows(result, nonZeroI, j);

            T scalar = buffer[j][j];
            for (int j2 = 0; j2 < dim; j2++) {
                result[j][j2] = elementProcessor.divide(result[j][j2], scalar);
                buffer[j][j2] = elementProcessor.divide(buffer[j][j2], scalar);
            }

            for (int i = j+1; i < dim; i++) {
                scalar = elementProcessor.inverse(buffer[i][j]);
                for (int j2 = 0; j2 < dim; j2++) {
                    result[i][j2] = elementProcessor.sum(result[i][j2], elementProcessor
                            .multiply(result[j][j2], scalar));
                    buffer[i][j2] = elementProcessor.sum(buffer[i][j2], elementProcessor
                            .multiply(buffer[j][j2], scalar));
                }
            }
        }

        // Make zeros below main diagonal
        for (int j = dim-1; j > 0; j--) {
            for (int i = j-1; i >= 0; i--) {
                T scalar = elementProcessor.inverse(buffer[i][j]);
                for (int j2 = 0; j2 < dim; j2++) {
                    result[i][j2] = elementProcessor.sum(result[i][j2], elementProcessor
                            .multiply(result[j][j2], scalar));
                    //buffer[i][j2] = elementProcessor.sum(buffer[i][j2], elementProcessor
                    //        .multiply(buffer[j][j2], scalar));
                }
            }
        }

        values = result;
        return this;
    }

    private void swapRows(T[][] array, int r1, int r2) {
        T[] temp = array[r1];
        array[r1] = array[r2];
        array[r2] = temp;
    }

    public static <T> GenericMatrix<T> createDiagonal(ElementProcessor<T> elementProcessor, T... diagonal) {
        GenericMatrix<T> result = new GenericMatrix<>(diagonal.length, diagonal.length, elementProcessor);
        for (int i = 0; i < diagonal.length; i++) {
            result.values[i][i] = diagonal[i];
        }
        return result;
    }

    public static <T> GenericMatrix<T> createIdentity(ElementProcessor<T> elementProcessor, int rowsN) {
        if (rowsN < 0) {
            throw new MatrixIllegalDimensionException();
        }
        GenericMatrix<T> result = new GenericMatrix<>(rowsN, rowsN, elementProcessor);
        for (int rowI = 0; rowI < rowsN; rowI++) {
            result.values[rowI][rowI] = elementProcessor.one();
        }
        return result;
    }

    public static <T> GenericMatrix<T> createRandomRow(ElementProcessor<T> elementProcessor, int columnsN) {
        if (columnsN < 0) {
            throw new MatrixIllegalDimensionException();
        }
        GenericMatrix<T> result = new GenericMatrix<>(1, columnsN, elementProcessor);
        for (int columnI = 0; columnI < columnsN; columnI++) {
            result.values[0][columnI] = elementProcessor.random();
        }
        return result;
    }

    public static <T> GenericMatrix<T> createRandomColumn(ElementProcessor<T> elementProcessor, int rowsN) {
        if (rowsN < 0) {
            throw new MatrixIllegalDimensionException();
        }
        GenericMatrix<T> result = new GenericMatrix<>(rowsN, 1, elementProcessor);
        for (int rowI = 0; rowI < rowsN; rowI++) {
            result.values[rowI][0] = elementProcessor.random();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericMatrix matrix)) return false;
        return Arrays.deepEquals(values, matrix.values);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(values);
    }

    public String toString() {
        return Arrays.deepToString(values).replace("],", "]\n");
    }

    private void checkOutOfBounds(int rowI, int columnI) {
        if (rowI < 0 || rowI >= getRowsN() ||
                columnI < 0 || columnI >= getColumnsN()) {
            throw new MatrixOutOfBoundsException();
        }
    }

}