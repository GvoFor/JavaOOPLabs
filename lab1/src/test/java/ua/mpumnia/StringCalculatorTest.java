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

    @Test
    void testCustomDelimiter() {
        int actual = calculator.add("//;\n5;3");
        Assertions.assertEquals(8, actual);
    }

    @Test
    void testCustomDelimiterWithDefaultDelimiters() {
        int actual = calculator.add("//+\n5+3,9\n1+2");
        Assertions.assertEquals(20, actual);
    }

    @Test
    void throwIllegalArgumentExceptionIfSomeNumberIsNegative() {
        Exception e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> calculator.add("-10,5,24,-3,6,-7"));
        Assertions.assertEquals(
                "Unsupported negative numbers. [-10, -3, -7] were passed",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }

    @Test
    void numbersGreaterThanThousandShouldBeenIgnored() {
        int actual = calculator.add("1000,1001,999");
        Assertions.assertEquals(1999, actual);
    }

    @Test
    void testCustomDelimiterThatConsistOfThreeCharacters() {
        int actual = calculator.add("//[***]\n5***3***6");
        Assertions.assertEquals(14, actual);
    }

    @Test
    void throwIllegalArgumentExceptionIfDelimiterFollowByAnotherDelimiterAndSomeOfThemIsCustom() {
        Exception e = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> calculator.add("//[++]\n4++18,5++4++,2"));
        Assertions.assertEquals(
                "There are two delimiters following one by another",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }

}
