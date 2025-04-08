package DTO;

public class SalaryDecisionDTO extends DecisionDTO {
    private double initialSalary;
    private double salaryAfterDecision;
    public SalaryDecisionDTO () {
        super();
        this.initialSalary = 0;
        this.salaryAfterDecision = 0;
	}
	public SalaryDecisionDTO(double initialSalary, double salaryAfterDecision) {
		super();
		this.initialSalary = initialSalary;
		this.salaryAfterDecision = salaryAfterDecision;
	}
	public double getInitialSalary() {
		return initialSalary;
	}
	public void setInitialSalary(double initialSalary) {
		this.initialSalary = initialSalary;
	}
	public double getSalaryAfterDecision() {
		return salaryAfterDecision;
	}
	public void setSalaryAfterDecision(double salaryAfterDecision) {
		this.salaryAfterDecision = salaryAfterDecision;
	}
}
