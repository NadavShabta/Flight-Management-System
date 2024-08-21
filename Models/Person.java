public abstract class Person {

    protected String id;
    protected String name;
    protected String contactNumber;



    public Person(String id, String name, String contactNumber){

        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public String getName() {
        return name;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public String getId() {
        return id;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public abstract String displayDetails();

}
