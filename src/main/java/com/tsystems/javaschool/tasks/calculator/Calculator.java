package com.tsystems.javaschool.tasks.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */


    public String evaluate(String statement) {
        // TODO: Implement the logic here
        CalculationPerformer calc = new CalculationPerformer();
        Double result =  calc.parse(statement);
        if (result == null || result.isInfinite()) return null;
        else return calc.customToString(result);
    }
}

class CalculationPerformer {
    int pos = -1;
    int currentChar;
    public String str;

    //custom toString method to remove frictional digits if 0, or remove all but four digits
    String customToString(Double input) {
        String inputString = BigDecimal.valueOf(input).
                setScale(4, RoundingMode.HALF_UP).toString();
        for (int i = inputString.length() - 1; inputString.charAt(i) != '.'; i--) {
            if (inputString.charAt(i) != '0' ) break;
            inputString = inputString.substring(0,i);
        }
        //in case no digits after '.' left, remove dot
        if (inputString.charAt(inputString.length() - 1) == '.') {
            inputString = inputString.substring(0,inputString.length() - 1);

        }

        return inputString;
    }

    void nextChar() {
        currentChar = (++pos < str.length()) ? str.charAt(pos) : -1;
    }

    boolean isRequiredChar(int charValue) {
        while (currentChar == ' ') nextChar();
        if (currentChar == charValue) {
            nextChar();
            return true;
        }
        return false;
    }


    public Double parse(String str) {
        if (str == null) return null;
        this.str = str;
        nextChar();
        Double x = parseExpression();
        if (pos < str.length()) return null;
        return x;
    }

    //Method for parsing addition and subtraction operations
    public Double parseExpression() {
        Double x = parseTerm();
        if (x == null || Double.isInfinite(x)) return null;
        while (true) {
            if (isRequiredChar('+')) {
                Double temp;
                if ((temp = parseTerm()) != null && !Double.isInfinite(temp)) {
                    x += temp;
                } else return null;
            }
            else if (isRequiredChar('-')) {
                Double temp;
                if ((temp = parseTerm()) != null && !Double.isInfinite(temp)) {
                    x -= temp;
                } else return null;
            }
            else return x;
        }
    }

    //Method for parsing multiply and divide operations
    public Double parseTerm() {
        Double x = parseFactor();
        while (true) {
            if (isRequiredChar('*')) {
                Double temp;
                if ((temp = parseFactor()) != null && !Double.isInfinite(temp)) {
                    x *= temp;
                } else return null;
            }
            else if (isRequiredChar('/')) {
                Double temp;
                if ((temp = parseFactor()) != null && !Double.isInfinite(temp)) {
                    x /= temp;
                } else return null;
            }
            else return x;
        }
    }

    //Method for parsing less prioritized symbols, such as numbers and parenthesis
    public Double parseFactor() {
        Double x;
        int startPos = this.pos;
        if (isRequiredChar('(')) {
            x = parseExpression();
            if (!isRequiredChar(')')) return null;
        } else if ((currentChar >= '0' && currentChar <= '9') || currentChar == '.') { // numbers
            while ((currentChar >= '0' && currentChar <= '9') || currentChar == '.') {
                nextChar();
            }
            try {
                x = Double.parseDouble(str.substring(startPos, this.pos));
            } catch (NumberFormatException e) {
                x = null;
            }
        } else {
            return null;
        }
        return x;
    }
}