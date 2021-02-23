package banking;

import java.util.Scanner;

public class Main {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int answer;
        int id;
        String dataBaseName = args[1];
        String cardNumber = "0000000000000000";
        String pinCode = "0000";

        if (args[0].equals("-fileName")) {
            Database.connection(dataBaseName);
        }

        while (true) {
            System.out.println("1. Create an account\n" +
                "2. Log into account\n" +
                "0. Exit");
            answer = scanner.nextInt();

            switch (answer) {
                case 1:
                    Card card = new Card();
                    cardNumber = card.cardNumber;
                    pinCode = card.pinCode;
                    Database.save(cardNumber, pinCode, dataBaseName);
                    System.out.println("Your card has been created\n" +
                        "Your card number:\n" +
                        cardNumber);
                    System.out.println("Your card PIN:\n" +
                        pinCode);
                    break;
                case 2:
                    System.out.println("Enter your card number:");
                    cardNumber = scanner.next();
                    System.out.println("Enter your PIN:");
                    pinCode = scanner.next();
                    id = Database.logIn(cardNumber, pinCode, dataBaseName);

                    if (id != 0) {
                        System.out.println("You have successfully logged in!");
                        accountMenu(id, dataBaseName);
                    }
                    break;
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    public static void accountMenu(int id, String dataBaseName) {
        int income;

        System.out.println("1. Balance\n" +
            "2. Add income\n" +
            "3. Do transfer\n" +
            "4. Close account\n" +
            "5. Log out\n" +
            "0. Exit");

        while (scanner.hasNext()) {
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("Balance: \n" +
                        Database.getBalance(id, dataBaseName));
                    break;
                case 2:
                    System.out.println("Enter income:");
                    income = scanner.nextInt();
                    
                    if (Database.enterIncome(id, dataBaseName, income) == 1) {
                        System.out.println("Income was added!");
                    }
                    break;
                case 3:
                    System.out.println("Transfer\n" +
                        "Enter card number:");
                    String cardNumber = scanner.next();

                    if (!LuhChecking(cardNumber)) {
                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                        break;
                    } else if (Database.findCard(dataBaseName, cardNumber) == 0) {
                        System.out.println("Such a card does not exist.");
                        break;
                    }

                    System.out.println("Enter how much money you want to transfer:");
                    income = scanner.nextInt();

                    if (Database.getBalance(id, dataBaseName) < income) {
                        System.out.println("Not enough money!");
                        break;
                    } else if (Database.subtractIncome(id, dataBaseName, income) == 1
                        && Database.enterIncome(Database.findCard(dataBaseName, cardNumber), dataBaseName, income) == 1) {
                        System.out.println("Success!");
                        break;
                    }
                    break;
                case 4:
                    Database.delete(id, dataBaseName);
                    break;
                case 5:
                    System.out.println("You have successfully logged out!");
                    break;
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    public static boolean LuhChecking(String cardNumber) {

        String CutNumber = cardNumber.substring(0, cardNumber.length() - 1);
        int Luhchecker = Integer.parseInt(cardNumber.substring(15));

        char[] digits = CutNumber.toCharArray();
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

        return Luhchecker == chekSum;
    }
}