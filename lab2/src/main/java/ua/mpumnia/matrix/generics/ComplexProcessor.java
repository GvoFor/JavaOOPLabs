package ua.mpumnia.matrix.generics;

public class ComplexProcessor implements ElementProcessor<ComplexNumber> {

    @Override
    public ComplexNumber sum(ComplexNumber value1, ComplexNumber value2) {
        return value1.add(value2);
    }

    @Override
    public ComplexNumber multiply(ComplexNumber value1, ComplexNumber value2) {
        return value1.multiply(value2);
    }

    @Override
    public ComplexNumber divide(ComplexNumber value1, ComplexNumber value2) {
        return value1.divide(value2);
    }

    @Override
    public ComplexNumber scale(ComplexNumber value, double scalar) {
        return value.scale(scalar);
    }

    @Override
    public boolean isZero(ComplexNumber value) {
        return value.isZero();
    }

    @Override
    public ComplexNumber zero() {
        return new ComplexNumber(0, 0);
    }

    @Override
    public ComplexNumber one() {
        return new ComplexNumber(1, 0);
    }

    @Override
    public ComplexNumber inverse(ComplexNumber value) {
        return value.scale(-1);
    }

    @Override
    public ComplexNumber random() {
        return ComplexNumber.random();
    }

}
