package ua.mpumnia.matrix.generics;

public class DoubleProcessor implements ElementProcessor<Double> {

    @Override
    public Double sum(Double value1, Double value2) {
        return value1 + value2;
    }

    @Override
    public Double multiply(Double value1, Double value2) {
        return value1 * value2;
    }

    @Override
    public Double divide(Double value1, Double value2) {
        return value1 / value2;
    }

    @Override
    public Double scale(Double value, double scalar) {
        return value * scalar;
    }

    @Override
    public boolean isZero(Double value) {
        return value == 0;
    }

    @Override
    public Double zero() {
        return 0.0;
    }

    @Override
    public Double one() {
        return 1.0;
    }

    @Override
    public Double inverse(Double value) {
        return -value;
    }

    @Override
    public Double random() {
        return Math.random();
    }
}
