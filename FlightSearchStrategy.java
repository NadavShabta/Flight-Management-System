import java.util.List;

public interface FlightSearchStrategy {
    List<Flight> search(List<Flight> flights, SearchType criteria);
}