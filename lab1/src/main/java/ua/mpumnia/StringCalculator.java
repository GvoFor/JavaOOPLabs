package ua.mpumnia;

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

        int sum = 0;
        for (String num : nums) {
            sum += Integer.parseInt(num);
        }

        return sum;
    }
    private String ridOfDelimiters(String expression) {
        // Support one custom delimiter
        if (expression.startsWith("//")) {
            char customDelimiter = expression.charAt(2);
            return expression.replace('\n', ',')
                    .replace(customDelimiter, ',')
                    .substring(4);
        }

        return expression.replace('\n', ',');
    }

}
