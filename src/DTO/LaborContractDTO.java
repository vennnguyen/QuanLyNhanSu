package DTO;

import java.time.LocalDate;
// import DAO.access_HOPDONGLAODONG;
// import DAO.access_PHONGBAN;

public class LaborContractDTO {
    private String contractId;
    private String employeeName;
    private String employeeId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String contractType;
    private String department;
    private double baseSalary;

    public LaborContractDTO() {
        this.contractId = "";
        this.startDate = null;
        this.endDate = null;
        this.contractType = "";
        this.baseSalary = 0;
        this.employeeId = "";
        this.employeeName = "";
        this.department = "";
    }

    public LaborContractDTO(String contractId, LocalDate startDate, LocalDate endDate, String contractType,
            double baseSalary) {
        this.contractId = contractId;
        this.employeeId = "null";
        this.employeeName = "null";
        this.department = "null";
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractType = contractType;
        this.baseSalary = baseSalary;
    }

    public LaborContractDTO(String contractId, String employeeId, String employeeName, String department, LocalDate startDate,
            LocalDate endDate, String contractType, double baseSalary) {
        this.contractId = contractId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.department = department;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractType = contractType;
        this.baseSalary = baseSalary;
    }

    public String getContractId() {
        return contractId;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDepartment() {
        return this.department;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(double baseSalary) {
        this.baseSalary = baseSalary;
    }

    // public Object[][] getObjectToRender() {
    //     String contractId = this.getContractId();
    //     String startDate = SUPPORT.LocalDateToString(this.getStartDate());
    //     String endDate = SUPPORT.LocalDateToString(this.getEndDate());
    //     String salary = SUPPORT.changeSalaryToFormatString(this.getBaseSalary());
    //     String contractType = this.getContractType();
    //     String employeeId = this.getEmployeeId();
    //     String employeeName = this.getEmployeeName();
    //     String departmentName = this.getDepartment();

    //     Object[] row = new Object[] { employeeId, employeeName, departmentName, contractId, startDate, endDate,
    //             contractType, salary, "", "" };
    //     Object[][] data = new Object[][] { row };

    //     return data;
    // }
}