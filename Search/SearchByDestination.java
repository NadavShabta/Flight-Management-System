import java.util.List;
import java.util.stream.Collectors;

public class SearchByDestination implements FlightSearchStrategy {
    @Override
    public List<Flight> search(List<Flight> flights, SearchType searchType) {
        return flights.stream()
                .filter(flight -> flight.getDestination().equalsIgnoreCase(searchType.getDestination()))
                .collect(Collectors.toList());
    }
}

