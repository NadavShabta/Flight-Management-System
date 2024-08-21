public class Passenger extends Person implements Observer{

    private String passportNumber;


    public Passenger(String id, String name, String contactNumber, String passportNumber){
        super(id, name, contactNumber);
        this.passportNumber = passportNumber;
    }
    @Override
    public void update(String message) {
        System.out.println("Passenger " + getName() + " received notification: " + message);
    }
    @Override
    public String toString() {
        return displayDetails();
    }


    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }


    @Override
    public String displayDetails(){
        String str = ("Passenger ID: " + id + ", Name: " + name + ", Contact: " + contactNumber + ", Passport: " + passportNumber);
        return str;
    }
}
