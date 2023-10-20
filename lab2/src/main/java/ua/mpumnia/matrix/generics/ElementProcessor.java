package ua.mpumnia.matrix.generics;

public interface ElementProcessor<T> {

    T sum(T value1, T value2);

    T multiply(T value1, T value2);

    T divide(T value1, T value2);

    T scale(T value, double scalar);

    boolean isZero(T value);

    T zero();

    T one();

    T inverse(T value);

    T random();

}
