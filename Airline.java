
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Airline implements AirlineComponent {
    private String name;
    private List<Flight> flights = new ArrayList<>();
    private List<AirlineComponent> subsidiaries = new ArrayList<>();

    public Airline(String name) {
        this.name = name;
    }
    @Override
    public void displayDetails() {
        // Display the current airline's details
        System.out.println("Airline: " + getName());
        for (Flight flight : flights) {
            flight.displayDetails();
        }
        for (AirlineComponent subsidiary : subsidiaries) {
            subsidiary.displayDetails();
        }
    }
    @Override
    public String getName() { return name; }
    @Override
    public void setName(String name){
        this.name = name;
    }
    @Override
    public String toString(){
        return "Airline Name: " + name;
    }


    public void addSubsidiary(AirlineComponent subsidiary) {
        if (!subsidiaries.contains(subsidiary)) {
            subsidiaries.add(subsidiary);
        }
    }
    public boolean removeSubsidiary(String subsidiaryName) {
        return subsidiaries.removeIf(subsidiary -> subsidiary.getName().equalsIgnoreCase(subsidiaryName));
    }
    public void addFlight(Flight flight) {
        // Check for an existing flight with the same flight number
        for (Flight existingFlight : flights) {
            if (existingFlight.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                System.out.println("A flight with the number " + flight.getFlightNumber() + " already exists.");
                return;
            }
        }
        flights.add(flight);
        System.out.println("Flight " + flight.getFlightNumber() + " has been added successfully.");
    }


    @Override
    public void removeFlight(Flight flight) {
        boolean found = false;
        Iterator<Flight> iterator = flights.iterator();
        while (iterator.hasNext()) {
            Flight existingFlight = iterator.next();
            if (existingFlight.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                iterator.remove(); // Remove the flight using iterator to avoid ConcurrentModificationException
                System.out.println("Flight " + flight.getFlightNumber() + " has been removed successfully.");
                found = true;
                break; // Assuming unique flight numbers, so break after finding
            }
        }
        if (!found) {
            System.out.println("Flight " + flight.getFlightNumber() + " could not be found for removal.");
        }
    }
    @Override
    public List<AirlineComponent> getSubsidiaries() {
        return subsidiaries;
    }

    @Override
    public List<Flight> getFlights() {
        return new ArrayList<>(flights);
    }

    public void addSub(AirlineComponent airline) {
        // Check for duplication
        for (AirlineComponent existingAirline : subsidiaries) {
            if (existingAirline.getName().equalsIgnoreCase(airline.getName())) {
                System.out.println("The subsidiary " + airline.getName() + " already exists.");
                return;
            }
        }
        subsidiaries.add(airline);
        System.out.println("Subsidiary " + airline.getName() + " has been added successfully.");
    }
    public void removeSub(String airlineName) {
        boolean found = false;
        Iterator<AirlineComponent> iterator = subsidiaries.iterator();
        while (iterator.hasNext()) {
            AirlineComponent airlineComponent = iterator.next();
            if (airlineComponent.getName().equalsIgnoreCase(airlineName)) {
                iterator.remove(); // Use iterator's remove method to avoid ConcurrentModificationException
                System.out.println("Subsidiary " + airlineName + " has been deleted successfully.");
                found = true;
                break; // Assuming only one subsidiary can have this name, so break after finding it
            }
        }
        if (!found) {
            System.out.println("Subsidiary " + airlineName + " could not be found.");
        }
    }


}
