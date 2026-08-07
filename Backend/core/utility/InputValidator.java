package core.utility;

import java.util.Scanner;

public class InputValidator {


    public static double getValidBudget(Scanner scanner) {

        while (true) {

            System.out.print("Enter your budget: ₹");

            if (scanner.hasNextDouble()) {

                double budget = scanner.nextDouble();

                if (budget > 0) {
                    return budget;
                }
            }

            System.out.println("Invalid budget. Enter a positive number.");
            scanner.nextLine();
        }
    }


    public static int getValidScore(Scanner scanner, String feature) {

        while (true) {

            System.out.print(feature + " importance (0-100): ");

            if (scanner.hasNextInt()) {

                int score = scanner.nextInt();

                if (score >= 0 && score <= 100) {
                    return score;
                }
            }

            System.out.println("Enter value between 0 and 100.");
            scanner.nextLine();
        }
    }
}