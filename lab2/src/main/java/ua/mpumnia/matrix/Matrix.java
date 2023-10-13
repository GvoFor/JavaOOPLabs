package ua.mpumnia.matrix;

import ua.mpumnia.matrix.exceptions.MatrixIllegalDimensionException;

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
        return 0;
    }

    public void setValue(int rowI, int columnI, double value) {

    }

    public Matrix getRow(int rowI) {
        return null;
    }

    public void setRow(int rowI, double... row) {

    }

    public void setRow(int rowI, Matrix rowMatrix) {

    }

    public Matrix getColumn(int columnI) {
        return null;
    }

    public void setColumn(int columnI, double... column) {

    }

    public void setColumn(int columnI, Matrix columnMatrix) {

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
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    public String toString() {
        return null;
    }

}