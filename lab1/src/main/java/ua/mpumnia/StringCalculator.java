package ua.mpumnia;

import java.util.*;

public class StringCalculator {

    public int add(String numbers) throws IllegalArgumentException {

        if (numbers.length() == 0) {
            return 0;
        }

        // Replace each delimiter with comma
        // and cut off delimiters definition part
        numbers = ridOfDelimiters(numbers);

        if (numbers.contains(",,")) {
            throw new IllegalArgumentException("There are two delimiters following one by another");
        }

        // Here we have just comma separated numbers
        String[] nums = numbers.split(",");

        checkForNegativeNumbers(nums);

        return sumUpNumbersLessOrEqualToThreshold(nums, 1000);
    }
    private String ridOfDelimiters(String expression) {
        // Support custom delimiters
        if (expression.startsWith("//")) {
            // Custom delimiter that consists of 1 char
            // Syntax is "//d\n" where 'd' is our delimiter
            if (expression.charAt(3) == '\n') {
                char customDelimiter = expression.charAt(2);
                return expression.substring(4)
                        .replace('\n', ',')
                        .replace(customDelimiter, ',');
            }
            // Custom delimiters that consist of more than 1 char
            // Syntax is "//[del1][del2]...[deln]\n" where
            // "del1", "del2", ..., "deln" are our delimiter
            Set<String> customDelimiters = extractDelimitersSet(expression);
            customDelimiters.add("\n");

            int delimitersEndIndex = expression.indexOf("]\n");
            expression = expression.substring(delimitersEndIndex + 2);

            for (String delimiter : customDelimiters) {
                expression = expression.replace(delimiter, ",");
            }

            return expression;
        }

        return expression.replace('\n', ',');
    }

    private Set<String> extractDelimitersSet(String expression) {
        Set<String> delimiters = new TreeSet<>(Comparator.reverseOrder());
        int delimiterEndIndex = expression.indexOf("]\n");
        String[] customDelimiters = expression.substring(3, delimiterEndIndex).split("]\\[");
        for (String delimiter : customDelimiters) {
            if (delimiter.length() != 0) {
                delimiters.add(delimiter);
            }
        }
        return delimiters;
    }

    private void checkForNegativeNumbers(String[] numbers) throws IllegalArgumentException {
        List<Integer> negativeNumbers = new ArrayList<>();
        for (String number : numbers) {
            int num = Integer.parseInt(number);
            if (num < 0) {
                negativeNumbers.add(num);
            }
        }

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Unsupported negative numbers. %s were passed"
                            .formatted(negativeNumbers.toString()));
        }
    }

    private int sumUpNumbersLessOrEqualToThreshold(String[] numbers, int threshold) {
        int sum = 0;
        for (String number : numbers) {
            int num = Integer.parseInt(number);
            if (num <= threshold) {
                sum += num;
            }
        }
        return sum;
    }

}
