package ua.mpumnia.matrix;

import ua.mpumnia.matrix.exceptions.MatrixIllegalDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixIncompatibleDimensionException;
import ua.mpumnia.matrix.exceptions.MatrixOutOfBoundsException;

import java.util.Arrays;

public class Matrix {

    private double[][] values;

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
        return null;
    }

    public Matrix multiply(double scalar) {
        return null;
    }

    public Matrix multiply(Matrix matrix) {
        return null;
    }

    public Matrix transpose() {
        return null;
    }

    public Matrix inverse() {
        return null;
    }

    public static Matrix createDiagonal(double... diagonal) {
        return null;
    }

    public static Matrix createIdentity(int rowsN, int columnsN) {
        return null;
    }

    public static Matrix createRandomRow(int columnsN) {
        return null;
    }


    public static Matrix createRandomColumn(int rowsN) {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
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