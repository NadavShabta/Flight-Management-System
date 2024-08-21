import java.time.ZonedDateTime;


public class Main {
    public static void main(String[] args) {
        System.out.println("Initializing the Airline Management System...");
        String STR = "TLV";
        MainSystem system = new MainSystem(STR) ;
        preloadData(system);
        runSimulation(system);
           system.start();
    }

    private static void preloadData(MainSystem system) {
        System.out.println("\nPreloading Data...");
        // Employees
        Employee emp1 = new Employee("001", "John Doe", "+11234567890", "E1001", "Booking");
        Employee emp2 = new Employee("002", "Jane Smith", "+10987654321", "E1002", "Security");
        system.getEmployees().add(emp1);
        system.getEmployees().add(emp2);
        // Authorize employee numbers for demonstration
        MainSystem.authorizedEmployeeNumbers.add("E1001");
        MainSystem.authorizedEmployeeNumbers.add("E1002");

        // Airlines
        AirlineComponent delta = new Airline("Delta Airlines");
        AirlineComponent united = new Airline("United Airlines");
        system.addAirline(delta);
        system.addAirline(united);

        // Flights
        Flight flight1 = new Flight("DL123", "JFK", "LAX", ZonedDateTime.now(), ZonedDateTime.now().plusHours(6), 300.00, 150, delta);
        Flight flight2 = new Flight("UA456", "JFK", "SFO", ZonedDateTime.now().plusHours(2), ZonedDateTime.now().plusHours(8), 400.00, 150, united);
        system.getAllFlights().add(flight1);
        system.getAllFlights().add(flight2);

        // Passengers
        Passenger pass1 = new Passenger("201", "Alice Johnson", "+11987654321", "P7890");
        Passenger pass2 = new Passenger("202", "Bob Lee", "+11876543210", "P6789");
        system.getPassengers().add(pass1);
        system.getPassengers().add(pass2);

        // Observers to flights
        flight1.attach(emp1);
        flight1.attach(pass1);
        flight2.attach(emp2);
        flight2.attach(pass2);



        System.out.println("Data preloaded. Starting system demo...\n");
    }

    private static void runSimulation(MainSystem system) {
        // Scenario 1: Update Flight Status
        System.out.println("Scenario 1: Updating Flight Status...");
        Flight flight = system.getAllFlights().get(0);
        flight.updateFlightStatus(flight.getFlightNumber(), "Delayed");
        flight.notifyObservers("Flight " + flight.getFlightNumber() + " is now delayed.");

        // Scenario 2: Add a new flight and attempt to book
        System.out.println("\nScenario 2: Adding a New Flight and Attempting Booking...");
        Flight newFlight = new Flight("DL789", "JFK", "MIA", ZonedDateTime.now().plusHours(1), ZonedDateTime.now().plusHours(5), 200.00, 150, system.getAirlineByName("Delta Airlines"));
        system.getAllFlights().add(newFlight);
        // Assume a booking system method that handles flight booking
        System.out.println("New flight " + newFlight.getFlightNumber() + " added to the schedule.");

        // Scenario 3: Employee and Passenger Notifications
        System.out.println("\nScenario 3: Notifying Employees and Passengers on a Flight...");
        newFlight.attach(system.getEmployees().get(0));  // Attach John Doe to the new flight
        newFlight.attach(system.getPassengers().get(0));  // Attach Alice Johnson to the new flight
        newFlight.notifyObservers("Reminder: Flight " + newFlight.getFlightNumber() + " will depart in 1 hour.");

        // Interaction loop: User can then proceed to interact with the system
        System.out.println("\nProceeding to main interactive demo...");
    }
}
