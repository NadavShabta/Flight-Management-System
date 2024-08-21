import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;



public class AirlineManagement {
    private Scanner scanner;
    private MainSystem mainSystem;

    public AirlineManagement(MainSystem mainSystem) {
        this.mainSystem = mainSystem;
        this.scanner = new Scanner(System.in);
    }
    public void manageAirline(){
        boolean back = false;
        while (!back) {
            System.out.println("\nAirline Management Menu:");
            System.out.println("1. List Airlines");
            System.out.println("2. Add Airline");
            System.out.println("3. Remove Airline");
            System.out.println("4. Update Airline Details");
            System.out.println("5. View Airline Flights");
            System.out.println("6. Add SubAirline");
            System.out.println("7. remove SubAirline");
            System.out.println("8. Back to Main Menu");

            System.out.print("Select an option: ");
            try {
                int choice = Integer.parseInt(scanner.nextLine());
                switch (choice) {
                    case 1:
                        listAirlines();
                        break;
                    case 2:
                        addAirline();
                        break;
                    case 3:
                        removeAirline();
                        break;
                    case 4:
                        updateAirlineDetails();
                        break;
                    case 5:
                        viewAirlineFlights();
                        break;
                    case 6:
                        addSubsidiary();
                        break;
                    case 7:
                        removeSubsidiary();
                        break;
                    case 8:
                        back = true;
                        break;
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }


    public void listAirlines() {
        System.out.println("List of All Airlines:");
        List<AirlineComponent> airlines = MainSystem.getInstance().airlines;
        for (AirlineComponent airline : airlines) {
            System.out.println(airline);
        }
    }
    private void addAirline() {
        System.out.print("Enter Airline Name: ");
        String name = scanner.nextLine();
        Airline newAirline = new Airline(name);

        boolean added = mainSystem.addAirline(newAirline);
        if (added) {
            System.out.println(name + " added successfully.");
        } else {
            System.out.println(name + " is already exist.");
        }
    }
    private void removeAirline() {
        System.out.print("Enter the name of the Airline to remove: ");
        String name = scanner.nextLine();

        boolean removed = MainSystem.getInstance().removeAirline(name);
        if (removed) {
            System.out.println(name + " has been successfully removed.");
        } else {
            System.out.println("Could not find an airline named " + name + ".");
        }
    }
    private void addSubsidiary() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Parent Airline Name: ");
        String parentName = scanner.nextLine();
        AirlineComponent parentAirline = MainSystem.getInstance().getAirlineByName(parentName);

        if (parentAirline instanceof Airline) {
            System.out.print("Enter Subsidiary Airline Name: ");
            String subsidiaryName = scanner.nextLine();
            AirlineComponent newSubsidiary = new SubAirline(subsidiaryName);
            ((Airline) parentAirline).addSubsidiary(newSubsidiary);
            MainSystem.getInstance().airlines.add(newSubsidiary);
            System.out.println(subsidiaryName + " added as a subsidiary to " + parentName + " successfully.");
        } else {
            if (parentAirline == null) {
                System.out.println("Parent airline " + parentName + " does not exist.");
            } else {
                System.out.println("Cannot add a subsidiary to a subsidiary.");
            }
        }
    }
    private void removeSubsidiary() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Parent Airline Name: ");
        String parentName = scanner.nextLine();
        AirlineComponent parentAirline = MainSystem.getInstance().getAirlineByName(parentName);

        if (parentAirline instanceof Airline) {
            System.out.print("Enter Subsidiary Airline Name to Remove: ");
            String subsidiaryName = scanner.nextLine();

            boolean removed = ((Airline) parentAirline).removeSubsidiary(subsidiaryName);
            boolean removed2 = MainSystem.getInstance().removeAirline(subsidiaryName);
            if (removed && removed2) {
                System.out.println(subsidiaryName + " removed from " + parentName + " successfully.");
            } else {
                System.out.println("Subsidiary " + subsidiaryName + " not found under " + parentName + ".");
            }
        } else {
            if (parentAirline == null) {
                System.out.println("Parent airline " + parentName + " does not exist.");
            } else {
                System.out.println("Subsidiaries cannot be removed from a non-Airline type or SubAirline.");
            }
        }
    }

    private void viewAirlineFlights() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Airline/Subairline Name: ");
        String airlineName = scanner.nextLine();
        AirlineComponent airline = MainSystem.getInstance().getAirlineByName(airlineName);

        if (airline != null) {
            System.out.println("Flights for " + airlineName + " and its subsidiaries:");
            printFlightsRecursive(airline, "");
        } else {
            System.out.println("Airline named " + airlineName + " does not exist.");
        }
    }

    private void printFlightsRecursive(AirlineComponent airline, String prefix) {
        // Print flights directly managed by this airline
        for (Flight flight : airline.getFlights()) {
            System.out.println(prefix + airline.getName() + " Flight: " + flight.getFlightNumber() +
                    " from " + flight.getOrigin() + " to " + flight.getDestination());
        }
        for (AirlineComponent subsidiary : airline.getSubsidiaries()) {
            printFlightsRecursive(subsidiary, prefix + "  ");
        }
    }

    public void updateAirlineDetails() {
        try {
            System.out.print("Enter the name of the Airline to update: ");
            String name = scanner.nextLine();
            AirlineComponent airline = MainSystem.getInstance().getAirlineByName(name);

            System.out.println("Select the detail to update:");
            System.out.println("1. Change the name of the airline");
            System.out.println("2. Update the flights list");
            System.out.print("Choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    updateAirlineName(airline);
                    break;
                case "2":
                    updateFlightsList(airline);
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } catch (NoSuchElementException | IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    private void updateAirlineName(AirlineComponent airline) {
        System.out.print("Enter the new name: ");
        String newName = scanner.nextLine();
        try {
            airline.setName(newName);
            System.out.println("Airline name updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update name: " + e.getMessage());
        }
    }

    private void updateFlightsList(AirlineComponent airline) {
        System.out.println("Do you want to (1) Add or (2) Remove a flight? Enter 1 or 2:");
        String choice = scanner.nextLine();
        try {
            switch (choice) {
                case "1":
                    FlightsManagement.addFlight();
                case "2":
                    System.out.print("Enter the flight number to remove: ");
                    String flightNumber = scanner.nextLine();
                    List<Flight> flights = airline.getFlights(); // Get the list of flights
                    Flight flightToRemove = null;
                    for (Flight flight : flights) {
                        if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                            flightToRemove = flight;
                            break;
                        }
                    }
                    if (flightToRemove != null) {
                        airline.removeFlight(flightToRemove); // Remove the flight
                        System.out.println("Flight " + flightNumber + " removed successfully.");
                    } else {
                        System.out.println("Flight number " + flightNumber + " not found.");
                    }
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Failed to update flights list: " + e.getMessage());
        }
    }




}


