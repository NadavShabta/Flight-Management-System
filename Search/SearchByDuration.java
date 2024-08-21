
import java.util.List;
import java.util.stream.Collectors;

public class SearchByDuration implements FlightSearchStrategy {

    @Override
    public List<Flight> search(List<Flight> flights, SearchType criteria) {
        // Check for null inputs and invalid duration
        if (flights == null || criteria == null) {
            throw new IllegalArgumentException("Flights list and search criteria must not be null.");
        }
        if (criteria.getMaxDuration() <= 0) {
            throw new IllegalArgumentException("Maximum duration must be greater than zero.");
        }

        // Convert duration from hours to minutes for comparison
        int maxDurationInMinutes = criteria.getMaxDuration() * 60;

        System.out.println("Searching for flights with duration up to: " + maxDurationInMinutes + " minutes");

        return flights.stream()
                .filter(flight -> {
                    long flightDurationInMinutes = flight.getFlightDuration().toMinutes();
                    return flightDurationInMinutes <= maxDurationInMinutes;
                })
                .peek(flight -> System.out.println("Flight " + flight.getFlightNumber() + " with duration "
                        + flight.getFlightDuration().toMinutes() + " minutes passed the filter"))
                .collect(Collectors.toList());
    }
}


