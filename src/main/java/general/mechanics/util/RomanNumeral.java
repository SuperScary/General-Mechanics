package general.mechanics.util;

public class RomanNumeral {

    public static String toRoman(int num) {
        if (num <= 0 || num > 3999) {
            throw new IllegalArgumentException("Number must be between 1 and 3999 for Roman numeral conversion.");
        }

        StringBuilder roman = new StringBuilder();
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
        String[] numerals = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};

        for (int i = 0; i < values.length; i++) {
            while (num >= values[i]) {
                num -= values[i];
                roman.append(numerals[i]);
            }
        }
        return roman.toString();
    }

}
