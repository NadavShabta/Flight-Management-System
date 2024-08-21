import java.time.LocalTime;
import java.time.ZonedDateTime;

public class SearchType {
    private Double maxPrice;
    private ZonedDateTime departureTime;
    private int maxDuration;
    private String destination;
    private String origin;


    // Constructor
    public SearchType(){

    }

    // Getters
    public Double getMaxPrice() {
        return maxPrice;
    }

    public ZonedDateTime getDepartureTime() {
        return departureTime;
    }

    public int getMaxDuration() {
        return maxDuration;
    }


    public String getDestination() {
        return destination;
    }


    public String getOrigin() {
        return origin;
    }

    // Setters
    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setDepartureTime(ZonedDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setMaxDuration(int maxDuration) {
        this.maxDuration = maxDuration;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
}
