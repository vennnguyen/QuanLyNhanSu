package DTO;

import java.time.LocalDate;

public class ProbationEmployeeDTO extends EmployeeDTO {

    public LocalDate startProbationDate;
    public LocalDate endProbationDate;
    public double probationSalary;

    public ProbationEmployeeDTO() {
        super();
        this.startProbationDate = null;
        this.endProbationDate = null;
        this.probationSalary = 0;
    }

    public ProbationEmployeeDTO(String employeeId, String fullName, String gender, LocalDate dateOfBirth, AddressDTO address, String phoneNumber, IdentityCardDTO identification, String ethnicity, String maritalStatus, String religion, String email, String departmentId,QualificationDTO qualifcation, PositionDTO position, AccountDTO account, LocalDate startProbationDate, LocalDate endProbationDate, double probationSalary) {
        super(employeeId, fullName, gender, dateOfBirth, address, phoneNumber, identification, ethnicity, maritalStatus, religion, email, departmentId, qualifcation, position, account);
        this.startProbationDate = startProbationDate;
        this.endProbationDate = endProbationDate;
        this.probationSalary = probationSalary;
    }

    public LocalDate getStartProbationDate() {
        return startProbationDate;
    }

    public void setStartProbationDate(LocalDate startProbationDate) {
        this.startProbationDate = startProbationDate;
    }

    public LocalDate getEndProbationDate() {
        return endProbationDate;
    }

    public void setEndProbationDate(LocalDate endProbationDate) {
        this.endProbationDate = endProbationDate;
    }

    public double getProbationSalary() {
        return this.probationSalary;
    }

    public void setProbationSalary(double probationSalary) {
        this.probationSalary = probationSalary;
    }
}
