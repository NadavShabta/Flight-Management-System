import java.util.List;

public class FlightSearchContext {
    private static FlightSearchStrategy strategy;

    public FlightSearchContext(FlightSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(FlightSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public static List<Flight> searchFlights(List<Flight> allFlights, SearchType searchType) {
        return strategy.search(allFlights, searchType);
    }
}

