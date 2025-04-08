package DTO;

public class SalaryDTO {

    public String salaryId;
    public String timeSheetId;
    public double bonus;
    public double actualSalary;
    public double positionAllowance;
    public double otherAllowances;
    public double insuranceDeduction;
    public double otherDeductions;
    public double tax;
    public double netSalary;

    public SalaryDTO() {
        this.salaryId = "";
        this.timeSheetId = "";
        this.bonus = 0;
        this.actualSalary = 0;
        this.positionAllowance = 0;
        this.otherAllowances = 0;
        this.insuranceDeduction = 0;
        this.otherDeductions = 0;
        this.tax = 0;
        this.netSalary = 0;
    }
    
    public SalaryDTO(String salaryId, String timeSheetId, double actualSalary, double bonus, double positionAllowance, double otherAllowances, double insuranceDeduction, double otherDeductions, double tax, double netSalary) {
        this.salaryId = salaryId;
        this.actualSalary = actualSalary;
        this.timeSheetId = timeSheetId;
        this.bonus = bonus;
        this.positionAllowance = positionAllowance;
        this.otherAllowances = otherAllowances;
        this.insuranceDeduction = insuranceDeduction;
        this.otherDeductions = otherDeductions;
        this.tax = tax;
        this.netSalary = netSalary;
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId;
    }
    
    public double getActualSalary() {
        return this.actualSalary;
    }
    
    public void setTimeSheetId(String timeSheetId) {
        this.timeSheetId = timeSheetId;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public void setPositionAllowance(double allowance) {
        this.positionAllowance = allowance;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public String getSalaryId() {
        return salaryId;
    }

    public String getTimeSheetId() {
        return timeSheetId;
    }

    public double getBonus() {
        return bonus;
    }

    public double getPositionAllowance() {
        return positionAllowance;
    }

    public double getTax() {
        return tax;
    }

    public double getNetSalary() {
        return netSalary;
    }
    
    public void setInsuranceDeduction(double deduction) {
        this.insuranceDeduction = deduction;
    }
    
    public double getInsuranceDeduction() {
        return this.insuranceDeduction;
    }

    public double getOtherAllowances() {
        return otherAllowances;
    }

    public void setOtherAllowances(double otherAllowances) {
        this.otherAllowances = otherAllowances;
    }

    public double getOtherDeductions() {
        return otherDeductions;
    }

    public void setOtherDeductions(double otherDeductions) {
        this.otherDeductions = otherDeductions;
    }
    
    public void setActualSalary(double actualSalary) {
        this.actualSalary = actualSalary;
    }
}
