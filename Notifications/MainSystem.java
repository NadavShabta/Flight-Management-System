
import java.util.*;
import java.util.Scanner;


    public class MainSystem {
        private static String ORIGIN = null;
        private static volatile MainSystem instance;

        static Scanner scanner = new Scanner(System.in);
        private List<Employee> employees = new ArrayList<>();
        private List<Passenger> passengers = new ArrayList<>();
        private static List<Flight> inComingFlights = new ArrayList<>();
        private static List<Flight> departureFlights = new ArrayList<>();
        private static List<Flight> connectionFlights = new ArrayList<>();
        private static List<Flight> allFlights = new ArrayList<>();

        private static List<AirlineComponent> airLines = new ArrayList<>();

        static Set<String> authorizedEmployeeNumbers = new HashSet<>();

        public MainSystem(String origin) {
            ORIGIN = origin;
            MainSystem.instance = this;
        }

        public void start() {
            // Entry point for the system's operational logic
            showMainMenu();
        }
            public List<AirlineComponent> airlines = new ArrayList<>();

            public AirlineComponent getAirlineByName(String name) {
                for (AirlineComponent airline : airlines) {
                    if (airline.getName().equalsIgnoreCase(name) && airline instanceof Airline) {
                        return airline;
                    }
                }
                throw new NoSuchElementException("Airline with name '" + name + "' not found.");
            }
        private static boolean authenticateEmployee() {
            int attempts = 0;
            while (attempts < 3) {
                System.out.print("Enter your employee number to access the system: ");
                try {
                    String enteredNumber = scanner.nextLine();
                    if (authorizedEmployeeNumbers.contains(enteredNumber)) {
                        return true; // Successful authentication
                    } else {
                        System.out.println("Invalid employee number. Please try again.");
                        attempts++;
                    }
                } catch (Exception e) {
                    System.out.println("An error occurred while trying to authenticate. Please try again.");
                    attempts++;
                }
            }
            System.out.println("Authentication failed after 3 attempts.");
            return false; // Return false after 3 failed attempts
        }


        private static void showMainMenu() {
            if (!authenticateEmployee()) {
                System.out.println("You are not authorized to access the system.");
                return; // Exit the application if authentication fails
            }
            boolean exit = false;
            while (!exit) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Airline Management");
                System.out.println("2. Flights Management");
                System.out.println("3. Employee Management");
                System.out.println("4. Passenger Management");
                System.out.println("5. Exit");

                System.out.print("Choose an area to manage: ");
                try {
                    int area = Integer.parseInt(scanner.nextLine()); // Use nextLine and parse to avoid issues

                    switch (area) {
                        case 1:
                            AirlineManagement airlineManagement = new AirlineManagement(MainSystem.getInstance());
                            airlineManagement.manageAirline();
                            break;
                        case 2:
                            FlightsManagement flightsManagement = new FlightsManagement(MainSystem.getInstance());
                            flightsManagement.flightsManagementMenu();
                            break;
                        case 3:
                            EmployeeManagement employeeManagement = new EmployeeManagement(MainSystem.getInstance());
                            employeeManagement.employeeManagementMenu();
                            break;
                        case 4:
                            PassengerManagement passengerManagement = new PassengerManagement(MainSystem.getInstance());
                            passengerManagement.passengerManagementMenu();
                            break;
                        case 5:
                            exit = true;
                            System.out.println("Exiting... Thank you for using the system.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please choose again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number for your selection.");
                } catch (Exception e) {
                    System.out.println("An error occurred. Please try again.");
                }
            }
        }

        public List<Employee> getEmployees() {
            return employees;
        }

        public void setEmployees(List<Employee> employees) {
            this.employees = employees;
        }

        public List<Passenger> getPassengers() {
            return passengers;
        }

        public List<Flight> getAllFlights() {
            return allFlights;
        }

        static String getValidAirportCodeFromUser(String prompt) {
            String airportCode = "";
            boolean isValidCode = false;

            while (!isValidCode) {
                System.out.print(prompt + " (2-4 capital letters, 'list' to see codes, 'add' to add new): ");
                airportCode = scanner.nextLine().trim().toUpperCase();

                if ("LIST".equals(airportCode)) {
                    printAvailableAirportCodes();
                } else if ("ADD".equals(airportCode)) {
                    addNewAirportCode();
                } else if (airportCode.matches("^[A-Z]{2,4}$")) {
                    if (TimezoneMapper.doesAirportCodeExist(airportCode)) {
                        isValidCode = true; // Valid airport code that exists in the mapper
                    } else {
                        System.out.println("Unknown airport code. You can add it by choosing 'add'.");
                    }
                } else {
                    System.out.println("Invalid format. Must be 2-4 capital letters, 'list' for codes, 'add' to add new.");
                }
            }

            return airportCode; // Return the valid airport code
        }
        private static void printAvailableAirportCodes() {
            System.out.println("Available Airport Codes:");
            TimezoneMapper.getAvailableAirportCodes().forEach(code -> System.out.print(code + " "));
            System.out.println("\n");
        }
        private static void addNewAirportCode() {
            System.out.print("Enter new airport code (2-4 capital letters): ");
            String newCode = scanner.nextLine().trim().toUpperCase();

            if (!newCode.matches("^[A-Z]{2,4}$")) {
                System.out.println("Invalid code format.");
                return;
            }

            System.out.print("Enter timezone for " + newCode + ": ");
            String timeZone = scanner.nextLine().trim();

            if (!TimezoneMapper.doesAirportCodeExist(newCode)) {
                TimezoneMapper.addAirportCode(newCode, timeZone);
                System.out.println("New airport added: " + newCode + " in timezone " + timeZone);
            } else {
                System.out.println("Airport code already exists.");
            }
        }


        public static MainSystem getInstance() {
            if (instance == null) {
                synchronized (MainSystem.class) {
                    if (instance == null) {
                        instance = new MainSystem(ORIGIN);
                    }
                }
            }
            return instance;
        }
        static void categorizeAndAddFlight(Flight flight) {
            if (flight.getDestination().equalsIgnoreCase(ORIGIN)) {
                inComingFlights.add(flight);
            } else if (flight.getOrigin().equalsIgnoreCase(ORIGIN)) {
                departureFlights.add(flight);
            } else {
                connectionFlights.add(flight); // Consider your criteria for connection flights
            }
            allFlights.add(flight);
        }

        public List<AirlineComponent> getAirLines() {
            return airLines;
        }
        public boolean addAirline(AirlineComponent airline) {
            // Check for an existing airline with the same name
            for (AirlineComponent existingAirline : airlines) {
                if (existingAirline.getName().equalsIgnoreCase(airline.getName())) {
                    return false; // Airline already exists
                }
            }
            airlines.add(airline);
            return true; // Airline added successfully
        }
        public  AirlineComponent findOrCreateAirline(String airlineName){
            AirlineComponent tmpAirLine = new Airline(airlineName);
            if (addAirline(tmpAirLine)){
                return tmpAirLine;
            }else {
                for (AirlineComponent newAirLine : airlines){
                    if (newAirLine.getName().equalsIgnoreCase(airlineName)){
                        return newAirLine;
                    }
                }
            }
            return tmpAirLine;
        }

        public boolean removeAirline(String airlineName) {
            // Check for an existing airline with the same name
            for (AirlineComponent existingAirline : airlines) {
                if (existingAirline.getName().equalsIgnoreCase(airlineName)) {
                    airlines.remove(existingAirline); // Airline already exists
                    return true;
                }
            }
            return false;
        }

        static void displaySearchResults(List<Flight> results) {
            if (results.isEmpty()) {
                System.out.println("No flights found matching the criteria.");
            } else {
                System.out.println("Search Results:");
                results.forEach(Flight::displayDetails);
            }
        }
    }

