package ua.mpumnia.matrix;

import ua.mpumnia.matrix.exceptions.MatrixIllegalDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixIncompatibleDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixInverseException;
import ua.mpumnia.matrix.exceptions.MatrixOutOfBoundsException;

import java.util.Arrays;

public class Matrix {

    protected double[][] values;

    public Matrix() {
        this(0, 0);
    }

    public Matrix(int rowsN, int columnsN) {
        if (rowsN < 0 || columnsN < 0) {
            throw new MatrixIllegalDimensionException(
                    "Minimum dimension is 0x0, but %dx%d was passed"
                            .formatted(rowsN, columnsN)
            );
        }
        values = new double[rowsN][columnsN];
    }

    public Matrix(Dimension dimension) {
        this(dimension.rowsN, dimension.columnsN);
    }

    public Matrix(Matrix matrix) {
        this(matrix.getRowsN(), matrix.getColumnsN());
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

    public double getValue(int rowI, int columnI) {
        checkOutOfBounds(rowI, columnI);
        return values[rowI][columnI];
    }

    public void setValue(int rowI, int columnI, double value) {
        checkOutOfBounds(rowI, columnI);
        values[rowI][columnI] = value;
    }

    public Matrix getRow(int rowI) {
        checkOutOfBounds(rowI, 0);
        Matrix row = new Matrix(1, getColumnsN());
        System.arraycopy(values[rowI], 0, row.values[0], 0, getColumnsN());
        return row;
    }

    public void setRow(int rowI, double... row) {
        checkOutOfBounds(rowI, 0);
        if (getColumnsN() != row.length) {
            throw new MatrixIncompatibleDimensionException();
        }
        System.arraycopy(row, 0, values[rowI], 0, getColumnsN());
    }

    public void setRow(int rowI, Matrix rowMatrix) {
        if (rowMatrix.getRowsN() != 1) {
            throw new MatrixIncompatibleDimensionException();
        }
        setRow(rowI, rowMatrix.values[0]);
    }

    public Matrix getColumn(int columnI) {
        checkOutOfBounds(0, columnI);
        Matrix column = new Matrix(getRowsN(), 1);
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            column.values[rowI][0] = values[rowI][columnI];
        }
        return column;
    }

    public void setColumn(int columnI, double... column) {
        checkOutOfBounds(0, columnI);
        if (getRowsN() != column.length) {
            throw new MatrixIncompatibleDimensionException();
        }
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            values[rowI][columnI] = column[rowI];
        }
    }

    public void setColumn(int columnI, Matrix columnMatrix) {
        checkOutOfBounds(0, columnI);
        if (getRowsN() != columnMatrix.getRowsN() || columnMatrix.getColumnsN() != 1) {
            throw new MatrixIncompatibleDimensionException();
        }
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            values[rowI][columnI] = columnMatrix.values[rowI][0];
        }
    }

    public Matrix add(Matrix matrix) {
        if (matrix.getRowsN() != getRowsN() || matrix.getColumnsN() != getColumnsN()) {
            throw new MatrixIncompatibleDimensionException();
        }
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            for (int columnI = 0; columnI < getColumnsN(); columnI++) {
                values[rowI][columnI] += matrix.values[rowI][columnI];
            }
        }
        return this;
    }

    public Matrix multiply(double scalar) {
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            for (int columnI = 0; columnI < getColumnsN(); columnI++) {
                values[rowI][columnI] *= scalar;
            }
        }
        return this;
    }

    public Matrix multiply(Matrix matrix) {
        if (getColumnsN() != matrix.getRowsN()) {
            throw new MatrixIncompatibleDimensionException();
        }
        double[][] result = new double[getRowsN()][matrix.getColumnsN()];
        int elementsN = getColumnsN();
        for (int rowI = 0; rowI < result.length; rowI++) {
            for (int columnI = 0; columnI < result[0].length; columnI++) {
                for (int i = 0; i < elementsN; i++) {
                    result[rowI][columnI] += values[rowI][i] * matrix.values[i][columnI];
                }
            }
        }
        values = result;
        return this;
    }

    public Matrix transpose() {
        double[][] transposed = new double[getColumnsN()][getRowsN()];
        for (int rowI = 0; rowI < getRowsN(); rowI++) {
            for (int columnI = 0; columnI < getColumnsN(); columnI++) {
                transposed[columnI][rowI] = values[rowI][columnI];
            }
        }
        values = transposed;
        return this;
    }

    public Matrix inverse() {
        if (getRowsN() != getColumnsN()) {
            throw new MatrixInverseException("Cannot take inverse of non-square matrix");
        }

        int dim = getRowsN();
        double[][] result = new double[dim][dim];
        double[][] buffer = new double[dim][dim];

        // Prepare arrays
        for (int i = 0; i < buffer.length; i++) {
            result[i][i] = 1;
            System.arraycopy(values[i], 0, buffer[i], 0, buffer[0].length);
        }

        // Make zeros under main diagonal
        for (int j = 0; j < dim; j++) {
            int nonZeroI = j;
            while (nonZeroI < dim && buffer[nonZeroI][j] == 0) {
                nonZeroI++;
            }
            if (nonZeroI == dim) {
                throw new MatrixInverseException("Inverse matrix does not exist. Determinant is 0");
            }

            swapRows(buffer, nonZeroI, j);
            swapRows(result, nonZeroI, j);

            double scalar = buffer[j][j];
            for (int j2 = 0; j2 < dim; j2++) {
                result[j][j2] /= scalar;
                buffer[j][j2] /= scalar;
            }

            for (int i = j+1; i < dim; i++) {
                scalar = buffer[i][j];
                for (int j2 = 0; j2 < dim; j2++) {
                    result[i][j2] -= result[j][j2] * scalar;
                    buffer[i][j2] -= buffer[j][j2] * scalar;
                }
            }
        }

        // Make zeros below main diagonal
        for (int j = dim-1; j > 0; j--) {
            for (int i = j-1; i >= 0; i--) {
                double scalar = buffer[i][j];
                for (int j2 = 0; j2 < dim; j2++) {
                    result[i][j2] -= result[j][j2] * scalar;
                    //buffer[i][j2] -= buffer[j][j2] * scalar;
                }
            }
        }

        values = result;
        return this;
    }

    private void swapRows(double[][] array, int r1, int r2) {
        double[] temp = array[r1];
        array[r1] = array[r2];
        array[r2] = temp;
    }

    public static Matrix createDiagonal(double... diagonal) {
        Matrix result = new Matrix(diagonal.length, diagonal.length);
        for (int i = 0; i < diagonal.length; i++) {
            result.values[i][i] = diagonal[i];
        }
        return result;
    }

    public static Matrix createIdentity(int rowsN, int columnsN) {
        if (rowsN < 0 || columnsN < 0) {
            throw new MatrixIllegalDimensionException();
        }
        Matrix result = new Matrix(rowsN, columnsN);
        for (int rowI = 0; rowI < rowsN; rowI++) {
            for (int columnI = 0; columnI < columnsN; columnI++) {
                result.values[rowI][columnI] = 1;
            }
        }
        return result;
    }

    public static Matrix createRandomRow(int columnsN) {
        if (columnsN < 0) {
            throw new MatrixIllegalDimensionException();
        }
        Matrix result = new Matrix(1, columnsN);
        for (int columnI = 0; columnI < columnsN; columnI++) {
            result.values[0][columnI] = Math.random();
        }
        return result;
    }

    public static Matrix createRandomColumn(int rowsN) {
        if (rowsN < 0) {
            throw new MatrixIllegalDimensionException();
        }
        Matrix result = new Matrix(rowsN, 1);
        for (int rowI = 0; rowI < rowsN; rowI++) {
            result.values[rowI][0] = Math.random();
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Matrix matrix)) return false;
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