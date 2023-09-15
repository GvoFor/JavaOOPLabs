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

    @Test
    void addFiveNumbersSeparatedWithComma() {
        int actual = calculator.add("6,14,920,57,3");
        Assertions.assertEquals(1000, actual);
    }

    @Test
    void addThreeNumbersSeparatedWithCommaAndLinebreaker() {
        int actual = calculator.add("4,18\n5");
        Assertions.assertEquals(27, actual);
    }

    @Test
    void throwIllegalArgumentExceptionIfDelimiterFollowByAnotherDelimiter() {
        Exception e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> calculator.add("4,18\n,5"));
        Assertions.assertEquals(
                "There are two delimiters following one by another",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }

}
