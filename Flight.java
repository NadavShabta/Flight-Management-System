import java.time.LocalDate;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Flight  {
    private final String flightNumber;
    private final String origin;
    private final String destination;
    private ZonedDateTime departure;
    private ZonedDateTime landing;
    private Duration flightDuration;
    private double price;
    private String status;
    private List<Person> passAndEmpl = new ArrayList<>();
    private List<Observer> observers = new ArrayList<>();
    private int availableSeats;
    private boolean fullyBooked = false;
    private final int totalSeats ;
    private AirlineComponent airLine;


    // Constructor to initialize the flight details
    public Flight(String flightNumber, String origin, String destination,
                 ZonedDateTime departure, ZonedDateTime landing, double price, int numberOfSeats,
                  AirlineComponent airline) {
        this.flightNumber = flightNumber;
        this.origin = origin;
        this.destination = destination;
        setDeparture(departure);
        setLanding(landing);
        this.price = price;
        this.status = "On-time";
        this.availableSeats = numberOfSeats;
        this.totalSeats = numberOfSeats;
        this.airLine = airline;

    }


    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm z");
        return "Flight{" +
                "Flight Number='" + flightNumber + '\'' +
                ", Origin='" + origin + '\'' +
                ", Destination='" + destination + '\'' +
                ", Departure Time='" + (departure != null ? departure.format(formatter) : "N/A") + '\'' +
                ", Landing Time='" + (landing != null ? landing.format(formatter) : "N/A") + '\'' +
                ", Price=" + String.format("%.2f", price) +
                ", Status='" + status + '\'' +
                ", Available Seats=" + availableSeats +
                ", Total Seats=" + totalSeats +
                ", Airline='" + (airLine != null ? airLine.getName() : "No Airline") + '\'' +
                '}';
    }

    public void displayDetails() {
        System.out.println("Flight Number: " + flightNumber + "Airline: " + airLine.getName() +
                ", Origin: " + origin +
                ", Destination: " + destination +
                ", Departure: " + departure +
                ", Landing: " + landing +
                ", Duration: " + flightDuration +
                ", Price: " + price +
                ", Status:" + status+
                 ", Available Seats:" +availableSeats);
    }
    // Methods to manage observers
    public void attach(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public boolean detach(Observer observer) {
        observers.remove(observer);
        return false;
    }

    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public void add(Person person){
        if (!passAndEmpl.contains(person)){
            attach((Observer) person);
        }
        passAndEmpl.add(person);
    }
    public void remove(Person person){
        passAndEmpl.remove(person);
    }



    // Getters for flight properties
    public String getFlightNumber() {
        return flightNumber;
    }
    public double getPrice() {
        return price;
    }
    public Duration getFlightDuration() {
        return flightDuration;
    }
    public ZonedDateTime getLanding() {
        return landing;
    }
    public ZonedDateTime getDeparture() {
        return departure;
    }
    public String getStatus() {
        return status;
    }
    public String getOrigin(){return origin;}
    public String  getDestination(){return destination;}
    public int getAvailableSeats() {
        return availableSeats;
    }
    public boolean isFullyBooked() {
        return fullyBooked;
    }
    public List<Person> getPassAndEmpl() {
        return passAndEmpl;
    }
    public int getTotalSeats() {
        return totalSeats;
    }
    public AirlineComponent getAirLine() {
        return airLine;
    }


    // setters
    public void setAirLine(AirlineComponent airLine) {
        this.airLine = airLine;
    }
    public void setDeparture(ZonedDateTime departure) {
        this.departure = departure;
        calculateDuration();
    }
    public void setLanding(ZonedDateTime landing) {
        this.landing = landing;
        calculateDuration();
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public void setFullyBooked(boolean fullyBooked) {
        this.fullyBooked = fullyBooked;
    }
    public void setAvailableSeats(int numberOfSeatsYouWant) {
        if (this.availableSeats - numberOfSeatsYouWant < 0){
            System.out.println("their are not enough seats available");
        }else {
        this.availableSeats = availableSeats - numberOfSeatsYouWant;
        }
    }
    public void setPassAndEmpl(List<Person> passAndEmpl) {
        this.passAndEmpl = passAndEmpl;
    }
    public boolean landsOnSameDay() {
        if (departure == null || landing == null) {
            throw new IllegalStateException("Departure and landing dates must be set.");
        }
        LocalDate departureDate = departure.toLocalDate();
        LocalDate landingDate = landing.toLocalDate();
        return departureDate.equals(landingDate);
    }
    private void calculateDuration() {
        if (departure != null && landing != null) {
            this.flightDuration = Duration.between(departure, landing);
        }
    }

    public boolean updateFlightStatus(String flightNumber, String newStatus) {
        if (this.flightNumber.equals(flightNumber)) {
            setStatus(newStatus);
            return true;
        }
        return false;
    }
}
