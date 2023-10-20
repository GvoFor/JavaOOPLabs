package ua.mpumnia.matrix.generics;

public class ComplexNumber {

    private final double realPart;
    private final double imaginaryPart;

    public ComplexNumber(double realPart, double imaginaryPart) {
        this.realPart = realPart;
        this.imaginaryPart = imaginaryPart;
    }

    public ComplexNumber(double phi) {
        this.realPart = Math.cos(phi);
        this.imaginaryPart = Math.sin(phi);
    }

    public double getRealPart() {
        return realPart;
    }

    public double getImaginaryPart() {
        return imaginaryPart;
    }

    public ComplexNumber add(ComplexNumber number) {
        return new ComplexNumber(
                realPart + number.realPart,
                imaginaryPart + number.imaginaryPart
        );
    }

    public ComplexNumber multiply(ComplexNumber number) {
        double resultRealPart = realPart * number.realPart
                - imaginaryPart * number.imaginaryPart;
        double resultImaginaryPart = realPart * number.imaginaryPart
                + imaginaryPart * number.realPart;
        return new ComplexNumber(resultRealPart, resultImaginaryPart);
    }

    public ComplexNumber divide(ComplexNumber number) {
        if (number.isZero()) {
            throw new ArithmeticException("Dividing by zero");
        }
        double scalar = number.realPart * number.realPart
                + number.imaginaryPart * number.imaginaryPart;
        double resultRealPart = realPart * number.realPart
                + imaginaryPart * number.imaginaryPart;
        double resultImaginaryPart = imaginaryPart * number.realPart
                - realPart * number.imaginaryPart;
        return new ComplexNumber(
                resultRealPart / scalar,
                resultImaginaryPart / scalar);
    }

    public ComplexNumber scale(double scalar) {
        return new ComplexNumber(
                realPart * scalar,
                imaginaryPart * scalar
        );
    }

    public boolean isZero() {
        return realPart == 0 && imaginaryPart == 0;
    }

    @Override
    public String toString() {
        if (imaginaryPart >= 0) {
            return "%.3f + i * %.3f".formatted(realPart, imaginaryPart);
        }
        return "%.3f - i * %.3f".formatted(realPart, -imaginaryPart);
    }

    public static ComplexNumber random() {
        return new ComplexNumber(Math.random());
    }
}
