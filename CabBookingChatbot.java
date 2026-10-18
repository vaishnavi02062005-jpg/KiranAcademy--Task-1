import java.util.Scanner;
import java.util.Random;

public class CabBookingChatbot {

    static Scanner sc = new Scanner(System.in);

    static String[] sources = {
        "Pune Station",
        "Swargate",
        "Hinjawadi"
    };

    static String[] destinations = {
        "Shivajinagar",
        "Kothrud",
        "Wakad"
    };

    static int[] distances = {5, 8, 10};

    static String[] cabTypes = {"Auto", "Sedan", "SUV"};
    static int[] rates = {12, 18, 25};

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n===== CAB BOOKING CHATBOT =====");
            System.out.println("1. Book Ride");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");

            int choice = readInt();

            if (choice == 1) {
                bookRide();
            } else if (choice == 2) {
                System.out.println("Thank you for using Cab Booking Chatbot!");
                break;
            } else {
                System.out.println("Invalid choice! Please select 1 or 2.");
            }
        }

        sc.close();
    }

    static int readInt() {
        while (true) {
            String value = sc.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException ex) {
                System.out.print("Invalid input! Enter a number: ");
            }
        }
    }

    static void bookRide() {

        System.out.println("\nChoose Cab Type:");
        System.out.println("1. Auto - Rs.12/km");
        System.out.println("2. Sedan - Rs.18/km");
        System.out.println("3. SUV - Rs.25/km");
        System.out.print("Enter cab choice: ");

        int cabChoice = readInt();

        if (cabChoice < 1 || cabChoice > 3) {
            System.out.println("Invalid cab choice! Booking cancelled.");
            return;
        }

        int cabIndex = cabChoice - 1;

        System.out.println("\nAvailable Routes:");
        for (int i = 0; i < sources.length; i++) {
            System.out.println(
                (i + 1) + ". " + sources[i] + " to "
                + destinations[i] + " - "
                + distances[i] + " km"
            );
        }

        System.out.print("Choose route number (1-3): ");
        int routeChoice = readInt();

        if (routeChoice < 1 || routeChoice > 3) {
            System.out.println("Invalid route choice! Booking cancelled.");
            return;
        }

        int routeIndex = routeChoice - 1;
        int distance = distances[routeIndex];
        int rate = rates[cabIndex];
        int fare = distance * rate;

        System.out.println("\n===== BOOKING DETAILS =====");
        System.out.println("Cab Type: " + cabTypes[cabIndex]);
        System.out.println("Source: " + sources[routeIndex]);
        System.out.println("Destination: " + destinations[routeIndex]);
        System.out.println("Distance: " + distance + " km");
        System.out.println("Rate: Rs." + rate + "/km");
        System.out.println("Total Fare: Rs." + fare);

        System.out.println("\n1. Confirm & Share OTP");
        System.out.println("2. Cancel Ride");
        System.out.print("Enter your choice: ");

        int confirmChoice = readInt();

        if (confirmChoice == 2) {
            System.out.println("Ride cancelled before OTP sharing.");
            System.out.println("Returning to main menu...");
            return;
        }

        if (confirmChoice != 1) {
            System.out.println("Invalid choice! Booking not confirmed.");
            System.out.println("Returning to main menu...");
            return;
        }

        Random random = new Random();
        int otp = 1000 + random.nextInt(9000);

        System.out.println("\nBooking Confirmed!");
        System.out.println("Your 4-digit OTP is: " + otp);
        System.out.println("OTP has been shared with you.");
        System.out.println("Ride cannot be cancelled after OTP sharing.");
        System.out.println("Have a safe journey!");

        System.out.println("\nReturning to main menu...");
    }
}