package ua.mpumnia.matrix;

public final class ImmutableMatrix extends Matrix {

    public ImmutableMatrix() {
        super();
    }

    public ImmutableMatrix(int rowsN, int columnsN) {
        super(rowsN, columnsN);
    }

    public ImmutableMatrix(Dimension dimension) {
        super(dimension);
    }

    public ImmutableMatrix(Matrix matrix) {
        super(matrix);
    }

    @Override
    public void setValue(int rowI, int columnI, double value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRow(int rowI, double... row) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRow(int rowI, Matrix row) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setColumn(int columnI, double... column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setColumn(int columnI, Matrix column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Matrix add(Matrix matrix) {
        return new Matrix(this).add(matrix);
    }

    @Override
    public Matrix multiply(double scalar) {
        return new Matrix(this).multiply(scalar);
    }

    @Override
    public Matrix multiply(Matrix matrix) {
        return new Matrix(this).multiply(matrix);
    }

    @Override
    public Matrix transpose() {
        return new Matrix(this).transpose();
    }

    @Override
    public Matrix inverse() {
        return new Matrix(this).inverse();
    }

}
