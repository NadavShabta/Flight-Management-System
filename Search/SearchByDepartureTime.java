import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByDepartureTime implements FlightSearchStrategy {

    @Override
    public List<Flight> search(List<Flight> flights, SearchType criteria) {
        ZonedDateTime searchTime = criteria.getDepartureTime();
        return flights.stream().filter(flight -> flight.getDeparture().isAfter(searchTime))
                .collect(Collectors.toList());
    }
}
