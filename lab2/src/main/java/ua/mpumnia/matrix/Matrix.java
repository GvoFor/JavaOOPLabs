package ua.mpumnia.matrix;

public class Matrix {

    private double[][] values;

    public Matrix() {

    }

    public Matrix(int rowsN, int columnsN) {

    }

    public Matrix(Matrix matrix) {

    }

    public int getRowsN() {
        return 0;
    }

    public int getColumnsN() {
        return 0;
    }

    public Dimension getDimension() {
        return null;
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