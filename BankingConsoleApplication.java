import java.util.Scanner;

public class BankingConsoleApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        double balance = 10000.0;

        System.out.println("Welcome to Kiran Bank");
        System.out.printf("Opening Balance: Rs.%.0f%n", balance);

        while (true) {
            System.out.println("\n1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid choice! Please enter 1 to 4.");
                sc.nextLine();
                continue;
            }

            int choice = sc.nextInt();

            if (choice == 1) {
                System.out.printf("Current Balance: Rs.%.0f%n", balance);

            } else if (choice == 2) {
                System.out.print("Enter deposit amount: ");

                if (!sc.hasNextDouble()) {
                    System.out.println("Invalid amount!");
                    sc.nextLine();
                    continue;
                }

                double amount = sc.nextDouble();

                if (amount <= 0) {
                    System.out.println(
                        "Invalid amount! Deposit must be greater than zero."
                    );
                } else {
                    balance = balance + amount;
                    System.out.println("Deposit successful.");
                    System.out.printf(
                        "Updated Balance: Rs.%.0f%n", balance
                    );
                }

            } else if (choice == 3) {
                System.out.print("Enter withdrawal amount: ");

                if (!sc.hasNextDouble()) {
                    System.out.println("Invalid amount!");
                    sc.nextLine();
                    continue;
                }

                double amount = sc.nextDouble();

                if (amount <= 0) {
                    System.out.println(
                        "Invalid amount! Withdrawal must be greater than zero."
                    );
                } else if (amount > balance) {
                    System.out.println(
                        "Transaction failed: Insufficient balance."
                    );
                } else {
                    balance = balance - amount;
                    System.out.println("Withdrawal successful.");
                    System.out.printf(
                        "Updated Balance: Rs.%.0f%n", balance
                    );
                }

            } else if (choice == 4) {
                System.out.println(
                    "Thank you for banking with us. Goodbye!"
                );
                break;

            } else {
                System.out.println(
                    "Invalid choice! Please select 1, 2, 3, or 4."
                );
            }
        }

        sc.close();
    }
}