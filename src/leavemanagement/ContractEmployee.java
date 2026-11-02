
package leavemanagement;

public class ContractEmployee extends Employee {

    public ContractEmployee(String employeeId,
                            String employeeName,
                            String department,
                            String email,
                            int availableLeaveBalance) {

        super(employeeId, employeeName, department,
              email, availableLeaveBalance);
    }

    @Override
    protected void displayEmployeeType() {
        System.out.println("Employee Type: Contract");
    }
}
