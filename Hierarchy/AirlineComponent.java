
import java.util.Collections;
import java.util.List;
public interface AirlineComponent {
    String getName();
    void setName(String name);
    void addFlight(Flight flight);
    void removeFlight(Flight flight);
    List<Flight> getFlights();
    public String toString();
    void displayDetails();
    default List<AirlineComponent> getSubsidiaries() {
        return Collections.emptyList();  // Default implementation for components without subsidiaries
    }

}


