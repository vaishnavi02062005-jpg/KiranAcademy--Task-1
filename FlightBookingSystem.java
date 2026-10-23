import java.util.Scanner;

class Flight {
    private String code;
    private String airline;
    private String source;
    private String destination;
    private int availableSeats;
    private double baseFare;

    public Flight(String code, String airline, String source,
                  String destination, int availableSeats,
                  double baseFare) {
        this.code = code;
        this.airline = airline;
        this.source = source;
        this.destination = destination;
        this.availableSeats = availableSeats;
        this.baseFare = baseFare;
    }

    public String getCode() {
        return code;
    }

    public String getAirline() {
        return airline;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void reserveSeats(int count) {
        availableSeats -= count;
    }

    public void display() {
        System.out.println(
            code + " | " + airline + " | " + source + " to "
            + destination + " | Seats: " + availableSeats
            + " | Base fare: Rs." + String.format("%.2f", baseFare)
        );
    }
}

abstract class Passenger {
    private String name;
    private int age;

    public Passenger(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public abstract double getDiscountPercentage();

    public String getType() {
        return "Passenger";
    }
}

class RegularPassenger extends Passenger {
    public RegularPassenger(String name, int age) {
        super(name, age);
    }

    @Override
    public double getDiscountPercentage() {
        return 0.0;
    }

    @Override
    public String getType() {
        return "Regular";
    }
}

class StudentPassenger extends Passenger {
    public StudentPassenger(String name, int age) {
        super(name, age);
    }

    @Override
    public double getDiscountPercentage() {
        return 0.10;
    }

    @Override
    public String getType() {
        return "Student";
    }
}

class Booking {
    private String bookingId;
    private Flight flight;
    private Passenger passenger;
    private int tickets;
    private double subtotal;
    private double discount;
    private double finalAmount;

    public Booking(String bookingId, Flight flight,
                   Passenger passenger, int tickets) {
        this.bookingId = bookingId;
        this.flight = flight;
        this.passenger = passenger;
        this.tickets = tickets;

        subtotal = flight.getBaseFare() * tickets;
        discount = subtotal * passenger.getDiscountPercentage();
        finalAmount = subtotal - discount;
    }

    public void displaySummary() {
        System.out.println("\nBooking confirmed!");
        System.out.println("Booking ID: " + bookingId);
        System.out.println("Flight: " + flight.getCode()
            + " | " + flight.getSource() + " to "
            + flight.getDestination());
        System.out.println("Passenger: " + passenger.getName());
        System.out.println("Age: " + passenger.getAge());
        System.out.println("Passenger type: " + passenger.getType());
        System.out.println("Tickets: " + tickets);
        System.out.printf("Base fare per ticket: Rs.%.2f%n",
            flight.getBaseFare());
        System.out.printf("Subtotal: Rs.%.2f%n", subtotal);
        System.out.printf("Discount (%.0f%%): Rs.%.2f%n",
            passenger.getDiscountPercentage() * 100, discount);
        System.out.printf("Final amount: Rs.%.2f%n", finalAmount);
    }
}

abstract class FarePolicy {
    public abstract double calculateFare(
        Flight flight, Passenger passenger, int tickets
    );
}

class StandardFarePolicy extends FarePolicy {
    @Override
    public double calculateFare(
        Flight flight, Passenger passenger, int tickets
    ) {
        double subtotal = flight.getBaseFare() * tickets;
        return subtotal - subtotal * passenger.getDiscountPercentage();
    }
}

class FlightBookingSystem {
    private Scanner sc = new Scanner(System.in);
    private Flight[] flights = {
        new Flight("PN101", "Kiran Air", "Pune", "Delhi", 20, 4000),
        new Flight("MB202", "Kiran Air", "Mumbai", "Bengaluru", 30, 5000),
        new Flight("PN303", "Kiran Air", "Pune", "Hyderabad", 25, 3500)
    };

    private Booking lastBooking = null;
    private FarePolicy farePolicy = new StandardFarePolicy();
    private int bookingCounter = 1001;

    public void showFlights() {
        System.out.println("\nAvailable Flights:");
        for (Flight flight : flights) {
            flight.display();
        }
    }

    private Flight findFlight(String code) {
        for (Flight flight : flights) {
            if (flight.getCode().equalsIgnoreCase(code)) {
                return flight;
            }
        }
        return null;
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String value = sc.nextLine().trim();

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Enter a whole number.");
            }
        }
    }

    public void bookFlight() {
        showFlights();

        System.out.print("\nEnter flight code: ");
        String code = sc.nextLine().trim();

        Flight flight = findFlight(code);

        if (flight == null) {
            System.out.println("Booking failed: Invalid flight code.");
            return;
        }

        System.out.print("Enter passenger name: ");
        String name = sc.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Booking failed: Name cannot be blank.");
            return;
        }

        int age = readInt("Enter age: ");

        if (age <= 0) {
            System.out.println("Booking failed: Age must be greater than zero.");
            return;
        }

        System.out.print("Passenger type (Regular/Student): ");
        String type = sc.nextLine().trim();

        Passenger passenger;

        if (type.equalsIgnoreCase("Regular")) {
            passenger = new RegularPassenger(name, age);
        } else if (type.equalsIgnoreCase("Student")) {
            passenger = new StudentPassenger(name, age);
        } else {
            System.out.println("Booking failed: Invalid passenger type.");
            return;
        }

        int tickets = readInt("Enter number of tickets: ");

        if (tickets <= 0) {
            System.out.println("Booking failed: Tickets must be greater than zero.");
            return;
        }

        if (tickets > flight.getAvailableSeats()) {
            System.out.println("Booking failed: Not enough seats available.");
            System.out.println("Available seats remain unchanged.");
            return;
        }

        double fare = farePolicy.calculateFare(flight, passenger, tickets);

        // Update seats only after all validation succeeds.
        flight.reserveSeats(tickets);

        lastBooking = new Booking(
            "BK" + bookingCounter++, flight, passenger, tickets
        );

        lastBooking.displaySummary();
    }

    public void viewBooking() {
        if (lastBooking == null) {
            System.out.println("No confirmed booking found.");
        } else {
            lastBooking.displaySummary();
        }
    }

    public void run() {
        System.out.println("Welcome to Kiran Air Booking!");

        while (true) {
            System.out.println("\n1. View Flights");
            System.out.println("2. Book Flight");
            System.out.println("3. View Booking");
            System.out.println("4. Exit");

            int choice = readInt("Enter choice: ");

            switch (choice) {
                case 1:
                    showFlights();
                    break;
                case 2:
                    bookFlight();
                    break;
                case 3:
                    viewBooking();
                    break;
                case 4:
                    System.out.println(
                        "Thank you for using Kiran Air Booking!"
                    );
                    sc.close();
                    return;
                default:
                    System.out.println(
                        "Invalid choice! Please select 1 to 4."
                    );
            }
        }
    }

    public static void main(String[] args) {
        FlightBookingSystem system = new FlightBookingSystem();
        system.run();
    }
}