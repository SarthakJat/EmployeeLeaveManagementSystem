
package leavemanagement;

public class Employee {

    private String employeeId;
    private String employeeName;
    private String department;
    private String email;
    private int availableLeaveBalance;

    public Employee(String employeeId, String employeeName,
                    String department, String email,
                    int availableLeaveBalance) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.email = email;
        this.availableLeaveBalance = availableLeaveBalance;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public String getDepartment() {
        return department;
    }

    public String getEmail() {
        return email;
    }

    public int getAvailableLeaveBalance() {
        return availableLeaveBalance;
    }

    public void setAvailableLeaveBalance(int balance) {
        this.availableLeaveBalance = balance;
    }

    protected void displayEmployeeType() {
        System.out.println("Employee Type: General");
    }

    public void displayDetails() {
        System.out.println("Employee ID : " + employeeId);
        System.out.println("Employee Name : " + employeeName);
        System.out.println("Department : " + department);
        System.out.println("Email : " + email);
        System.out.println("Leave Balance : "
                           + availableLeaveBalance);

        displayEmployeeType();
    }
}

