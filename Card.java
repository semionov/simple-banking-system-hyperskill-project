package banking;

public class Card {

    String cardNumber = "400000";
    String pinCode = "0000";
    int balance = 0;

    public Card() {
        this.cardNumber = chekSumGenerator(generateCardNumber());
        this.pinCode = generatePinCode();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public String getPinCode() {
        return pinCode;
    }

    public int getBalance() {
        return balance;
    }

    public String generateCardNumber() {
        long number = (long) Math.floor(Math.random() * 9_000_000_00L) + 1_000_000_00L;
        String cardNumber = "400000" + number;
        return cardNumber;
    }

    public String generatePinCode() {
        int number = (int) Math.floor(1000 + Math.random() * 9000);
        return Integer.toString(number);
    }

    public static String chekSumGenerator(String cardNumber) {
        char[] digits = cardNumber.toCharArray();
        int[] digitsInt = new int[digits.length];
        int sumOfDigits = 0;
        int chekSum = 0;

        for (int i = 0; i < digits.length; i++) {
            digitsInt[i] = Character.getNumericValue(digits[i]);

            if (i % 2 == 0) {
                digitsInt[i] *= 2;
            }

            if (digitsInt[i] > 9) {
                digitsInt[i] -= 9;
            }
            sumOfDigits += digitsInt[i];
        }

        if (sumOfDigits % 10 != 0) {
            chekSum = Math.abs(sumOfDigits % 10 - 10);
        }

        return cardNumber + chekSum;
    }


}
