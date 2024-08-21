
import java.util.List;
import java.util.Scanner;

public class EmployeeManagement {
    private Scanner scanner;
    private static MainSystem mainSystem;
    public EmployeeManagement(MainSystem instance) {
        this.scanner = new Scanner(System.in);
        mainSystem = instance;
    }

    public void employeeManagementMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add New Employee");
            System.out.println("2. Remove Employee");
            System.out.println("3. Display All Employees");
            System.out.println("4. Notify All Employees");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline left-over

            switch (choice) {
                case 1:
                    addNewEmployee();
                    break;
                case 2:
                    removeEmployee();
                    break;
                case 3:
                    displayAllEmployees();
                    break;
                case 4:
                    notifyAllEmployees();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting Employee Management System.");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void addNewEmployee() {
        try {
            // Validate Employee ID: exactly 9 digits
            String id;
            while (true) {
                System.out.print("Enter Employee ID (9 digits): ");
                id = scanner.nextLine();
                if (id.matches("\\d{9}")) {
                    break;
                }
                System.out.println("Invalid ID. ID must be exactly 9 digits.");
            }
            String name;
            while (true) {
                System.out.print("Enter Name (only letters): ");
                name = scanner.nextLine();
                if (name.matches("[a-zA-Z ]+")) {
                    break;
                }
                System.out.println("Invalid Name. Name must contain only letters.");
            }
            String contactNumber;
            while (true) {
                System.out.print("Enter Contact Number ('+' followed by 12 digits): ");
                contactNumber = scanner.nextLine();
                if (contactNumber.matches("\\+[0-9]{12}")) {
                    break;
                }
                System.out.println("Invalid Contact Number. Format should be '+<12 digits>'.");
            }
            String employeeNumber;
            while (true) {
                System.out.print("Enter Employee Number ('E' followed by 3 or 4 digits): ");
                employeeNumber = scanner.nextLine();
                if (employeeNumber.matches("E\\d{3,4}")) {
                    break;
                }
                System.out.println("Invalid Employee Number. Format should be 'E' followed by 3 or 4 digits.");
            }

            System.out.print("Enter Department: ");
            String department = scanner.nextLine();

            Employee newEmployee = new Employee(id, name, contactNumber, employeeNumber, department);
            if (!mainSystem.getEmployees().contains(newEmployee)) {
                List<Employee> tmp = mainSystem.getEmployees();
                tmp.add(newEmployee);
                mainSystem.setEmployees(tmp);
                System.out.println("Employee added successfully.");
            } else {
                System.out.println("Failed to add employee. Employee already exists.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while adding an employee: " + e.getMessage());
        }
    }


    private void removeEmployee() {
        try {
            System.out.print("Enter Employee Number to Remove: ");
            String employeeNumber = scanner.nextLine();
            Employee toRemove = null;
            for (Employee emp : mainSystem.getEmployees()) {
                if (emp.getEmployeeNumber().equals(employeeNumber)) {
                    toRemove = emp;
                    break;
                }
            }
            if (toRemove != null && mainSystem.getEmployees().remove(toRemove)) {
                System.out.println("Employee removed successfully.");
            } else {
                System.out.println("No employee found with that number.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while trying to remove an employee: " + e.getMessage());
        }
    }

    private void displayAllEmployees() {
        try {
            if (mainSystem.getEmployees().isEmpty()) {
                System.out.println("No employees to display.");
            } else {
                for (Employee emp : mainSystem.getEmployees()) {
                    System.out.println(emp);
                }
            }
        } catch (Exception e) {
            System.out.println("An error occurred while displaying employees: " + e.getMessage());
        }
    }

    private void notifyAllEmployees() {
        try {
            System.out.print("Enter message to notify all employees: ");
            String message = scanner.nextLine();
            for (Employee emp : mainSystem.getEmployees()) {
                emp.update(message);
            }
            System.out.println("All employees have been notified.");
        } catch (Exception e) {
            System.out.println("An error occurred while notifying employees: " + e.getMessage());
        }
    }


}
