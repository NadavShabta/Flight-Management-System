import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByClosestDepartureAndDestination implements FlightSearchStrategy {

    @Override
    public List<Flight> search(List<Flight> flights, SearchType searchType) {
        ZonedDateTime departureTime = searchType.getDepartureTime();
        String destination = searchType.getDestination();

        return flights.stream()
                .filter(flight -> flight.getDestination().equalsIgnoreCase(destination)) // Match destination
                .filter(flight -> !flight.getDeparture().isBefore(departureTime))
                .sorted(Comparator.comparing(Flight::getDeparture)) // Sort by departure time, closest first
                .collect(Collectors.toList()); // Collect the sorted flights
    }
}
