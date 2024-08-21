
import java.util.Scanner;

public class PassengerManagement {
    private Scanner scanner;
    private static MainSystem mainSystem;

    public PassengerManagement(MainSystem instance) {
        this.scanner = new Scanner(System.in);
        mainSystem = instance;
    }

    public void passengerManagementMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nPassenger Management System");
            System.out.println("1. Add New Passenger");
            System.out.println("2. Remove Passenger");
            System.out.println("3. Display All Passengers");
            System.out.println("4. Notify All Passengers");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addNewPassenger();
                    break;
                case 2:
                    removePassenger();
                    break;
                case 3:
                    displayAllPassengers();
                    break;
                case 4:
                    notifyAllPassengers();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting Passenger Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewPassenger() {
        try {
            // Validate passenger ID: exactly 9 digits
            String id;
            while (true) {
                System.out.print("Enter passenger ID (9 digits): ");
                id = scanner.nextLine();
                if (id.matches("\\d{9}")) {
                    break;
                }
                System.out.println("Invalid ID. ID must be exactly 9 digits.");
            }

            // Validate Name: only letters
            String name;
            while (true) {
                System.out.print("Enter Name (only letters): ");
                name = scanner.nextLine();
                if (name.matches("[a-zA-Z ]+")) {
                    break;
                }
                System.out.println("Invalid Name. Name must contain only letters.");
            }

            // Validate Contact Number: '+' followed by 12 digits
            String contactNumber;
            while (true) {
                System.out.print("Enter Contact Number ('+' followed by 12 digits): ");
                contactNumber = scanner.nextLine();
                if (contactNumber.matches("\\+[0-9]{12}")) {
                    break;
                }
                System.out.println("Invalid Contact Number. Format should be '+<12 digits>'.");
            }
            // Validate passenger PASS: exactly 9 digits
            String PASS;
            while (true) {
                System.out.print("Enter passenger Passport (8 digits): ");
                PASS = scanner.nextLine();
                if (PASS.matches("\\d{8}")) {
                    break;
                }
                System.out.println("Invalid PASSPORT. PASSPORT must be exactly 8 digits.");
            }

            Passenger newPassenger = new Passenger(id, name, contactNumber, PASS);
            if (!mainSystem.getPassengers().contains(newPassenger)) {
                mainSystem.getPassengers().add(newPassenger);
                System.out.println("Passenger added successfully.");
            } else {
                System.out.println("Failed to add passenger. Passenger already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding an passenger: " + e.getMessage());
        }
    }

    private void removePassenger() {
        try {
            System.out.print("Enter Passport Number to Remove: ");
            String passportNumber = scanner.nextLine();
            Passenger toRemove = null;
            for (Passenger pass : mainSystem.getPassengers()) {
                if (pass.getPassportNumber().equals(passportNumber)) {
                    toRemove = pass;
                    break;
                }
            }
            if (toRemove != null && mainSystem.getPassengers().remove(toRemove)) {
                System.out.println("Passenger removed successfully.");
            } else {
                System.out.println("No passenger found with that passport number.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to remove an passengers: " + e.getMessage());
        }
    }

    private void displayAllPassengers() {
        try {
            if (mainSystem.getPassengers().isEmpty()) {
                System.out.println("No passengers to display.");
            } else {
                for (Passenger pass : mainSystem.getPassengers()) {
                    System.out.println(pass);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while displaying passengers: " + e.getMessage());
        }
    }

    private void notifyAllPassengers() {
        try {
            System.out.print("Enter message to notify all passengers: ");
            String message = scanner.nextLine();
            for (Passenger pass : mainSystem.getPassengers()) {
                pass.update(message);
            }
            System.out.println("All passengers have been notified.");
        } catch (Exception e) {
            System.out.println("An error occurred while notifying passengers: " + e.getMessage());
        }
    }
}
