import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SearchByPrice implements FlightSearchStrategy {

    @Override
    public List<Flight> search(List<Flight> flights, SearchType searchCriteria) {
        double maxPrice = searchCriteria.getMaxPrice();
        return flights.stream()
                .filter(flight -> flight.getPrice() <= maxPrice)
                .sorted(Comparator.comparingDouble(Flight::getPrice)) // Sort by price, lowest to highest
                .collect(Collectors.toList());
    }
}
