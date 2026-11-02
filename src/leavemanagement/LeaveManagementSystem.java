
package leavemanagement;

import java.util.*;


public class LeaveManagementSystem {

    private List<Employee> employees = new ArrayList<>();

    private Set<String> employeeEmails = new HashSet<>();

    private Map<String, Employee> employeeMap = new HashMap<>();

    private List<LeaveRequest> leaveRequests = new ArrayList<>();

    private int requestCounter = 1001;

public void addEmployee(Employee employee) {

    if (employee.getAvailableLeaveBalance() < 0) {
        System.out.println("Error: Leave balance cannot be negative.");
        return;
    }

    if (employeeMap.containsKey(employee.getEmployeeId())) {
        System.out.println("Error: Duplicate employee ID.");
        return;
    }

    if (employeeEmails.contains(employee.getEmail())) {
        System.out.println("Error: Duplicate employee email.");
        return;
    }

    employees.add(employee);

    employeeMap.put(employee.getEmployeeId(), employee);

    employeeEmails.add(employee.getEmail());

    System.out.println("Employee added successfully.");
}

public Employee searchEmployee(String employeeId)
        throws EmployeeNotFoundException {

    Employee employee = employeeMap.get(employeeId);

    if (employee == null) {
        throw new EmployeeNotFoundException(
            "Employee " + employeeId + " does not exist."
        );
    }

    return employee;
}

public void displayAllEmployees() {

    if (employees.isEmpty()) {
        System.out.println("No employees found.");
        return;
    }

    Iterator<Employee> iterator = employees.iterator();

    while (iterator.hasNext()) {
        Employee employee = iterator.next();
        employee.displayDetails();
        System.out.println("--------------------");
    }
}

public void applyLeave(String employeeId, LeaveType leaveType,
                       int numberOfDays, String reason)
        throws EmployeeNotFoundException,
               InsufficientLeaveException {

    Employee employee = searchEmployee(employeeId);

    if (numberOfDays <= 0) {
        System.out.println("Error: Leave days must be greater than 0.");
        return;
    }

    if (numberOfDays > employee.getAvailableLeaveBalance()) {
        throw new InsufficientLeaveException(
            "Insufficient leave balance. Available: "
            + employee.getAvailableLeaveBalance()
            + ", Requested: " + numberOfDays
        );
    }

    String requestId = "LR" + requestCounter++;

    LeaveRequest request = new LeaveRequest(
        requestId, employeeId, leaveType, numberOfDays, reason
    );

    leaveRequests.add(request);

    System.out.println("Leave request created successfully.");
    System.out.println("Request ID: " + requestId);
    System.out.println("Status: " + request.getStatus());
}

public void approveLeave(String requestId) {

    LeaveRequest request = null;

    for (LeaveRequest r : leaveRequests) {
        if (r.getRequestId().equals(requestId)) {
            request = r;
            break;
        }
    }

    if (request == null) {
        System.out.println("Error: Leave request not found.");
        return;
    }

    if (request.getStatus() != LeaveStatus.PENDING) {
        System.out.println("Error: Request already processed.");
        return;
    }

    try {
        Employee employee = searchEmployee(request.getEmployeeId());

        int previousBalance = employee.getAvailableLeaveBalance();
        int days = request.getNumberOfDays();

        employee.setAvailableLeaveBalance(previousBalance - days);
        request.setStatus(LeaveStatus.APPROVED);

        System.out.println("Leave Request Approved.");
        System.out.println("Employee ID: " + employee.getEmployeeId());
        System.out.println("Previous Balance: " + previousBalance);
        System.out.println("Leave Used: " + days);
        System.out.println("Remaining Leave: "
                           + employee.getAvailableLeaveBalance());

    } catch (EmployeeNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public void rejectLeave(String requestId) {

    LeaveRequest request = null;

    for (LeaveRequest r : leaveRequests) {
        if (r.getRequestId().equals(requestId)) {
            request = r;
            break;
        }
    }

    if (request == null) {
        System.out.println("Error: Leave request not found.");
        return;
    }

    if (request.getStatus() != LeaveStatus.PENDING) {
        System.out.println("Error: Request already processed.");
        return;
    }

    request.setStatus(LeaveStatus.REJECTED);

    System.out.println("Leave Request Rejected.");
    System.out.println("Request ID: " + request.getRequestId());
    System.out.println("Employee ID: " + request.getEmployeeId());
    System.out.println("Leave Balance remains unchanged.");
}

public void displayAllLeaveRequests() {

    if (leaveRequests.isEmpty()) {
        System.out.println("No leave requests found.");
        return;
    }

    for (LeaveRequest request : leaveRequests) {
        request.displayDetails();
        System.out.println("--------------------");
    }
}

public void displayLeaveBalance(String employeeId) {

    try {
        Employee employee = searchEmployee(employeeId);

        System.out.println("Employee ID: "
                           + employee.getEmployeeId());

        System.out.println("Employee Name: "
                           + employee.getEmployeeName());

        System.out.println("Available Leave Balance: "
                           + employee.getAvailableLeaveBalance());

    } catch (EmployeeNotFoundException e) {
        System.out.println("Error: " + e.getMessage());
    }
}

public void displayLeaveBalance() {

    if (employees.isEmpty()) {
        System.out.println("No employees found.");
        return;
    }

    for (Employee employee : employees) {

        System.out.println(
            "Employee ID: " + employee.getEmployeeId()
        );

        System.out.println(
            "Employee Name: " + employee.getEmployeeName()
        );

        System.out.println(
            "Leave Balance: "
            + employee.getAvailableLeaveBalance()
        );

        System.out.println("--------------------");
    }
}


public static void main(String[] args) {

    Scanner sc = new Scanner(System.in);
    LeaveManagementSystem system = new LeaveManagementSystem();

    while (true) {

        System.out.println("\n===== Employee Leave Management =====");
        System.out.println("1. Add Employee");
        System.out.println("2. Display All Employees");
        System.out.println("3. Search Employee");
        System.out.println("4. Apply Leave");
        System.out.println("5. Approve Leave");
        System.out.println("6. Reject Leave");
        System.out.println("7. Display All Leave Requests");
        System.out.println("8. Display Employee Leave Balance");
        System.out.println("9. Exit");
        System.out.print("Enter your choice: ");

        int choice;

        try {
            choice = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            continue;
        }

        try {
            switch (choice) {

                case 1:
                    System.out.print("Employee ID: ");
                    String id = sc.nextLine();

                    System.out.print("Employee Name: ");
                    String name = sc.nextLine();

                    System.out.print("Department: ");
                    String department = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Leave Balance: ");
                    int balance = Integer.parseInt(sc.nextLine());

                    System.out.print("Type (1-Permanent, 2-Contract): ");
                    int type = Integer.parseInt(sc.nextLine());

                    Employee employee;

                    if (type == 1) {
                        employee = new PermanentEmployee(
                            id, name, department, email, balance
                        );
                    } else if (type == 2) {
                        employee = new ContractEmployee(
                            id, name, department, email, balance
                        );
                    } else {
                        System.out.println("Invalid employee type.");
                        break;
                    }

                    system.addEmployee(employee);
                    break;

                case 2:
                    system.displayAllEmployees();
                    break;

                case 3:
                    System.out.print("Enter Employee ID: ");
                    String searchId = sc.nextLine();

                    Employee found = system.searchEmployee(searchId);
                    found.displayDetails();
                    break;

                case 4:
                    System.out.print("Employee ID: ");
                    String empId = sc.nextLine();

                    System.out.println("Leave Type: 1-CASUAL, 2-SICK, 3-EARNED");
                    int leaveChoice = Integer.parseInt(sc.nextLine());

                    LeaveType leaveType;

                    if (leaveChoice == 1) {
                        leaveType = LeaveType.CASUAL;
                    } else if (leaveChoice == 2) {
                        leaveType = LeaveType.SICK;
                    } else if (leaveChoice == 3) {
                        leaveType = LeaveType.EARNED;
                    } else {
                        System.out.println("Invalid leave type.");
                        break;
                    }

                    System.out.print("Number of Days: ");
                    int days = Integer.parseInt(sc.nextLine());

                    System.out.print("Reason: ");
                    String reason = sc.nextLine();

                    system.applyLeave(empId, leaveType, days, reason);
                    break;

                case 5:
                    System.out.print("Enter Request ID: ");
                    system.approveLeave(sc.nextLine());
                    break;

                case 6:
                    System.out.print("Enter Request ID: ");
                    system.rejectLeave(sc.nextLine());
                    break;

                case 7:
                    system.displayAllLeaveRequests();
                    break;

                case 8:
                    System.out.print("Enter Employee ID (or ALL): ");
                    String balanceId = sc.nextLine();

                    if (balanceId.equalsIgnoreCase("ALL")) {
                        system.displayLeaveBalance();
                    } else {
                        system.displayLeaveBalance(balanceId);
                    }
                    break;

                case 9:
                    System.out.println("Exiting application...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

        } catch (EmployeeNotFoundException |
                 InsufficientLeaveException e) {
            System.out.println("Error: " + e.getMessage());

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}


}
