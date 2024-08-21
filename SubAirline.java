
import java.util.ArrayList;
import java.util.List;
public class SubAirline implements AirlineComponent {
    private String name;
    private List<Flight> flights = new ArrayList<>();


    public SubAirline(String name) {
        this.name = name;
    }

    @Override
    public void displayDetails() {
        System.out.println("SubAirline: " + getName());
        for (Flight flight : flights) {
            flight.displayDetails();
        }
    }
    @Override
    public String getName() {

        return this.name;
    }
    @Override
    public String toString(){
        return "Airline Name: " + name;
    }

    @Override
    public void setName(String name){
        this.name = name;
    }


    @Override
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
        flights.remove(flight);
    }

    @Override
    public List<Flight> getFlights() {
        return new ArrayList<>(flights);
    }


}
