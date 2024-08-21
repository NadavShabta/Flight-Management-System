import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FlightsManagement {
    private Scanner scanner;
    private static MainSystem mainSystem;
    public  FlightsManagement (MainSystem mainSystem){
        this.mainSystem = mainSystem;
        this.scanner = new Scanner(System.in);
    }
    public void flightsManagementMenu() {
        boolean back = false;
        while (!back) {
            System.out.println("\nFlights Management Menu:");
            System.out.println("1. Add Flight");
            System.out.println("2. Remove Flight");
            System.out.println("3. Search Flights");
            System.out.println("4. Update Flight Data");
            System.out.println("5. Add Person to Flight");
            System.out.println("6. Remove Person from Flight");
            System.out.println("7. Back to Main Menu");

            System.out.print("Choose an action: ");
            int action = Integer.parseInt(MainSystem.scanner.nextLine());
            switch (action) {
                case 1:
                    addFlight();
                    break;
                case 2:
                    removeFlight();
                    break;
                case 3:
                    searchFlights();
                    break;
                case 4:
                    updateFlightStatus();
                    break;
                case 5:
                    addPersonToFlight();
                    break;
                case 6:
                    removePersonFromFlight();
                    break;
                case 7:
                    back = true;
                    break;
                default:
                    System.out.println("Invalid action. Please choose again.");
            }
        }
    }

    public static void addFlight() {
        System.out.print("Enter flight number: ");
        Scanner scanner = new Scanner(System.in);
        String flightNumber = scanner.nextLine();
        String regex = "^[A-Z]{2,4}\\d{1,4}$";

        while (!flightNumber.matches(regex) || flightNumber.length() > 8) {
            System.out.println("Invalid flight number. Must be 2-4 capital letters followed by 1-4 numbers," +
                    " with a maximum length of 8 characters.");
            System.out.print("Enter flight number: ");
            flightNumber = scanner.nextLine();
        }
        String origin = MainSystem.getValidAirportCodeFromUser("Enter origin airport code (e.g., JFK)");
        String originRegex = "^[A-Z]{2,4}$"; // Regex for 2-4 capital letters
        String destinationRegex = "^[A-Z]{2,4}$";


        System.out.print("Enter destination (2-4 capital letters): ");
        String destination = scanner.nextLine();
        while (!destination.matches(destinationRegex)) {
            System.out.println("Invalid destination. Must be 2-4 capital letters.");
            System.out.print("Enter destination (2-4 capital letters): ");
            destination = scanner.nextLine();
        }

        scanner = new Scanner(System.in);
        LocalDate flightDate = null;
        LocalTime departureTime = null;
        LocalTime landingTime = null;
        ZoneId zoneId = ZoneId.systemDefault();
        while (flightDate == null) {
            System.out.print("Enter the flight date (YYYY-MM-DD): ");
            String dateInput = scanner.nextLine();
            try {
                LocalDate parsedDate = LocalDate.parse(dateInput);
                if (!parsedDate.isBefore(LocalDate.now())) { // Check if the date is not before today
                    flightDate = parsedDate;
                } else {
                    System.out.println("The flight date cannot be in the past. Please enter a future date.");
                }
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use the YYYY-MM-DD format.");
            }
        }

        // Input departure time
        while (departureTime == null) {
            System.out.print("Enter departure time (HH:MM): ");
            try {
                departureTime = LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:MM format.");
            }
        }

        // Input landing time
        while (landingTime == null) {
            System.out.print("Enter landing time (HH:MM): ");
            try {
                landingTime = LocalTime.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Please use HH:MM format.");
            }
        }

        // Combine the date and times into ZonedDateTime
        ZonedDateTime departure = ZonedDateTime.of(flightDate, departureTime, zoneId);
        ZonedDateTime landing = ZonedDateTime.of(flightDate, landingTime, zoneId);

        // Check if landing time is actually before the departure time, assume next day
        if (landing.isBefore(departure)) {
            landing = landing.plusDays(1);
        }
        System.out.print("Enter price in $: ");
        double price = 0;
        boolean validPrice = false;
        while (!validPrice) {
            try {
                price = Double.parseDouble(scanner.nextLine());
                validPrice = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format for price. Please enter a valid number.");
            }
        }
        int totalSeats = 0;
        boolean validSeats = false;
        while (!validSeats) {
            System.out.print("Enter total number of seats: ");
            try {
                totalSeats = Integer.parseInt(scanner.nextLine());
                validSeats = true;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format for total number of seats. Please enter a valid integer.");
            }
        }

        System.out.println("Enter AirLine name: ");
        String airLineName = scanner.nextLine();
        AirlineComponent airline = mainSystem.findOrCreateAirline(airLineName);

        Flight newFlight = new Flight(flightNumber, origin, destination, departure, landing, price, totalSeats, airline);
        MainSystem.categorizeAndAddFlight(newFlight);
        boolean landsSameDay = newFlight.landsOnSameDay();

        if (!landsSameDay) {
            System.out.println("The flight lands on: " + newFlight.getLanding().toLocalDate());
        }
        airline.addFlight(newFlight);

//  print all airlines to see if the new one appears
        System.out.println("Current airlines in the system:");
        for (AirlineComponent air : MainSystem.getInstance().airlines) {
            System.out.println(air.getName());
        }

    }
    private void removeFlight() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter flight number to remove: ");
        String flightNumber = scanner.nextLine();

        Flight flightToRemove = null;
        for (Flight flight : mainSystem.getAllFlights()) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                flightToRemove = flight;
                break;
            }
        }
        if (flightToRemove == null) {
            System.out.println("Flight not found.");
            return;
        }else {
            flightToRemove.getAirLine().removeFlight(flightToRemove);
        }
        boolean removed = mainSystem.getAllFlights().removeIf(flight -> flight.getFlightNumber().equals(flightNumber));
        if (removed) {
            System.out.println("Flight removed successfully.");
        } else {
            System.out.println("Flight not found.");
        }
    }


    private static void updateFlightStatus() {
        System.out.print("Enter flight number to update: ");
        String flightNumber = MainSystem.scanner.nextLine().trim();

        Flight flightToUpdate = null;
        for (Flight flight : mainSystem.getAllFlights()) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                flightToUpdate = flight;
                break;
            }
        }
        if (flightToUpdate == null) {
            System.out.println("Flight not found.");
            return;
        }
        System.out.print("Enter new status for the flight (On Time, Delayed, Cancelled, Boarding): ");
        String newStatus = MainSystem.scanner.nextLine().trim();
        List<String> allowedStatuses = Arrays.asList("On Time", "Delayed", "Cancelled", "Boarding");

        if (!allowedStatuses.contains(newStatus)) {
            System.out.println("Invalid status entered. Please enter one of the following statuses: On Time, Delayed, Cancelled, Boarding.");
            return;
        }
        try {
            flightToUpdate.updateFlightStatus(flightNumber,newStatus);
            System.out.println("Flight status updated successfully.");

            // Notify all observers about the status change
            String notificationMessage = "Flight " + flightNumber + " status updated to: " + newStatus;
            flightToUpdate.notifyObservers(notificationMessage);
            System.out.println("All observers have been notified about the status change.");
        } catch (Exception e) {
            System.out.println("An error occurred while updating the flight status. Please try again.");
            // Log the exception if logging is implemented
        }
    }

    public void searchFlights() {
        System.out.println("\nSearch Flights By:");
        System.out.println("1. Price");
        System.out.println("2. Duration");
        System.out.println("3. Destination");
        System.out.println("4. Origin and Destination");
        System.out.println("5. Closest Departure Time and Destination");
        System.out.println("6. Back to Main Menu");

        System.out.print("Select an option to search by: ");
        int searchOption = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        SearchType searchCriteria = new SearchType();
        FlightSearchStrategy strategy = null; // Placeholder for the strategy

        switch (searchOption) {
            case 1:
                System.out.print("Enter maximum price: ");
                double maxPrice = scanner.nextDouble();
                searchCriteria.setMaxPrice(maxPrice);
                strategy = new SearchByPrice();
                break;
            case 2:
                System.out.print("Enter maximum duration (in hours): ");
                int maxDuration = scanner.nextInt();
                scanner.nextLine();
                searchCriteria.setMaxDuration(maxDuration);
                strategy = new SearchByDuration();

                List<Flight> allFlights = mainSystem.getAllFlights(); // Ensure this method gets the updated list
                List<Flight> filteredFlights = strategy.search(allFlights, searchCriteria);
                if (filteredFlights.isEmpty()) {
                    System.out.println("No flights found matching the criteria.");
                } else {
                    System.out.println("Found " + filteredFlights.size() + " flights matching the criteria:");
                    filteredFlights.forEach(System.out::println);
                }
                break;

            case 3:
                System.out.print("Enter destination: ");
                String destination = scanner.next();
                searchCriteria.setDestination(destination);
                strategy = new SearchByDestination();
                break;
            case 4:
                System.out.print("Enter origin: ");
                String origin = scanner.next();
                System.out.print("Enter destination: ");
                destination = scanner.next();
                searchCriteria.setDestination(destination);
                searchCriteria.setOrigin(origin);
                strategy = new SearchByOriginAndDestination();
                break;
            case 5:
                System.out.print("Enter destination: ");
                String destinatio = scanner.nextLine(); // Get the destination input
                LocalDate departureDate = null;
                while (departureDate == null) {
                    System.out.print("Enter the departure date (yyyy-MM-dd): ");
                    String dateInput = scanner.nextLine();
                    try {
                        departureDate = LocalDate.parse(dateInput); // Parse the input into a LocalDate
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please use the format yyyy-MM-dd.");
                    }
                }
                // Assume the time is at midnight of the given date in the system's default timezone
                ZonedDateTime departureTime = departureDate.atStartOfDay(ZoneId.systemDefault());
                searchCriteria.setDestination(destinatio);
                searchCriteria.setDepartureTime(departureTime);
                strategy = new SearchByClosestDepartureAndDestination();
                break;


            case 6:
                return; // Go back to main menu
        }

        if (searchOption != 6) {
            scanner.nextLine(); // Consume any leftover newline
            FlightSearchContext context = new FlightSearchContext(strategy);
            List<Flight> results = context.searchFlights(mainSystem.getAllFlights(), searchCriteria);
            mainSystem.displaySearchResults(results);
        }
    }

    private void addPersonToFlight() {
        System.out.print("Enter flight number: ");
        String flightNumber = MainSystem.scanner.nextLine().trim();
        Flight flight = findFlightByNumber(flightNumber);

        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.print("Enter person ID: ");
        String personId = MainSystem.scanner.nextLine().trim();
        Observer observer = findPersonById(personId);

        if (observer == null) {
            System.out.println("Person not found.");
            return;
        }

        flight.attach(observer);
        System.out.println("Person added to flight and registered as observer.");
    }
    private void removePersonFromFlight() {
        System.out.print("Enter flight number: ");
        String flightNumber = MainSystem.scanner.nextLine().trim();
        Flight flight = findFlightByNumber(flightNumber);

        if (flight == null) {
            System.out.println("Flight not found.");
            return;
        }

        System.out.print("Enter person ID: ");
        String personId = MainSystem.scanner.nextLine().trim();
        Observer observer = findPersonById(personId);

        if (observer == null) {
            System.out.println("Person not found.");
            return;
        }

        if (flight.detach(observer)) {
            System.out.println("Person removed from flight and unregistered as observer.");
        } else {
            System.out.println("Failed to remove person from flight.");
        }
    }
    private Flight findFlightByNumber(String flightNumber) {
        for (Flight flight : mainSystem.getAllFlights()) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
                return flight;
            }
        }
        return null;  // Return null if no flight matches
    }
    private Observer findPersonById(String personId) {
        // Check among employees
        for (Employee employee : mainSystem.getEmployees()) {
            if (employee.getId().equals(personId)) {
                return employee;
            }
        }

        // Check among passengers
        for (Passenger passenger : mainSystem.getPassengers()) {
            if (passenger.getId().equals(personId)) {
                return passenger;
            }
        }

        return null;  // Return null if no person matches
    }















}
