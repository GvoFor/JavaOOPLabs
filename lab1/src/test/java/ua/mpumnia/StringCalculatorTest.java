package ua.mpumnia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringCalculatorTest {

    private static final StringCalculator calculator = new StringCalculator();

    @Test
    void emptyStringEqualToZero() {
        int actual = calculator.add("");
        Assertions.assertEquals(0, actual);
    }

    @Test
    void singleNumberEqualToItself() {
        int actual = calculator.add("12");
        Assertions.assertEquals(12, actual);
    }

    @Test
    void addTwoNumbersSeparatedWithComma() {
        int actual = calculator.add("6,14");
        Assertions.assertEquals(20, actual);
    }

}
