package ua.mpumnia;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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
    void throwStringCalculatorIllegalArgumentExceptionIfDelimiterFollowByAnotherDelimiter() {
        Exception e = Assertions.assertThrows(
                StringCalculatorIllegalArgumentException.class,
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
    void throwStringCalculatorIllegalArgumentExceptionIfSomeNumberIsNegative() {
        Exception e = Assertions.assertThrows(
                StringCalculatorIllegalArgumentException.class,
                () -> calculator.add("-10,5,24,-3,6,-7"));
        Assertions.assertEquals(
                "Negative numbers are not supported. [-10, -3, -7] were passed",
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
    void throwStringCalculatorIllegalArgumentExceptionIfDelimiterFollowByAnotherDelimiterAndSomeOfThemIsCustom() {
        Exception e = Assertions.assertThrows(
                StringCalculatorIllegalArgumentException.class,
                () -> calculator.add("//[++]\n4++18,5++4++,2"));
        Assertions.assertEquals(
                "There are two delimiters following one by another",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }

    @Test
    void testThreeCustomDelimitersEachConsistsOfOneCharacter() {
        int actual = calculator.add("//[*][+][%]\n1+3%5*7");
        Assertions.assertEquals(16, actual);
    }

    @Test
    void testThreeCustomDelimitersWithDifferentLength() {
        int actual = calculator.add("//[**][***][*]\n1*1***1**2");
        Assertions.assertEquals(5, actual);
    }

    @Test
    void testTwoCustomDelimitersWithDifferentLengthWithDefaultDelimiters() {
        int actual = calculator.add("//[+][ab]\n2+2ab2,2\n2ab2");
        Assertions.assertEquals(12, actual);
    }

    @Test
    void throwStringCalculatorIllegalArgumentExceptionIfExpressionIsNull() {
        Exception e = Assertions.assertThrows(
                StringCalculatorIllegalArgumentException.class,
                () -> calculator.add(null));
        Assertions.assertEquals(
                "Null was passed",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }

    @ParameterizedTest
    @ValueSource(strings = {"/+\n1,2",
                            "//abc\n1,2",
                            "//[++][\n1,2",
                            "//][h]\n1,2",
                            "//[][%]\n1,2"})
    void throwStringCalculatorIllegalArgumentExceptionIfDelimiterDefinitionIsIncorrect(String expression) {
        Exception e = Assertions.assertThrows(
                StringCalculatorIllegalArgumentException.class,
                () -> calculator.add(expression));
        Assertions.assertEquals(
                "Delimiters definition is incorrect",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }

    @Test
    void throwStringCalculatorIllegalArgumentExceptionIfExpressionStartsWithDelimiter() {
        Exception e = Assertions.assertThrows(
                StringCalculatorIllegalArgumentException.class,
                () -> calculator.add("//+\n+1,2"));
        Assertions.assertEquals(
                "Expression starts with delimiter",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }

    @Test
    void throwStringCalculatorIllegalArgumentExceptionIfExpressionEndsWithDelimiter() {
        Exception e = Assertions.assertThrows(
                StringCalculatorIllegalArgumentException.class,
                () -> calculator.add("//+\n1,2+"));
        Assertions.assertEquals(
                "Expression ends with delimiter",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1+2",
                            "1,2,--3,5",
                            "1,2-,3,5",
                            "//+\n1,2+3a5",
                            "//[***][**]\n5****6**3***4"})
    void throwStringCalculatorIllegalArgumentExceptionIfExpressionContainsUndefinedDelimiter(String expression) {
        Exception e = Assertions.assertThrows(
                StringCalculatorIllegalArgumentException.class,
                () -> calculator.add(expression));
        Assertions.assertEquals(
                "Expression contains undefined delimiter",
                e.getMessage(),
                () -> "Exception message doesn't match expected one");
    }
}
