public class Employee extends Person implements Observer {
    private String employeeNumber;
    private String department;

    public Employee(String id, String name, String contactNumber, String employeeNumber, String department) {
        super(id, name, contactNumber);
        this.employeeNumber = employeeNumber;
        this.department = department;
    }
    @Override
    public void update(String message) {
        System.out.println("Employee " + getName() + " received notification: " + message);
    }
    @Override
    public String toString() {
        return displayDetails();
    }

    public String getDepartment() {
        return department;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }



    @Override
    public String displayDetails() {
        String str =("Employee ID: " + id + ", Name: " + name + ", Contact: " + contactNumber + ", Employee Number: " + employeeNumber + ", Department: " + department);
        return str;
    }


}

