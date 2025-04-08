package DTO;

public class DecisionDTO {

    private String decisionId;
    private String employeeId;
    private String decisionDate;
    private String decisionMaker;
    private String reason;
    
    public DecisionDTO() {
        this.decisionId = null;
        this.decisionDate = null;
        this.decisionMaker = null;
        this.reason = null;
    }

    public DecisionDTO(String decisionId, String decisionDate, String decisionMaker, String reason) {
        this.decisionId = decisionId;
        this.decisionDate = decisionDate;
        this.decisionMaker = decisionMaker;
        this.reason = reason;
    }

    public void setDecisionId(String decisionId) {
        this.decisionId = decisionId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setDecisionDate(String decisionDate) {
        this.decisionDate = decisionDate;
    }

    public void setDecisionMaker(String decisionMaker) {
        this.decisionMaker = decisionMaker;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDecisionId() {
        return decisionId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getDecisionDate() {
        return decisionDate;
    }

    public String getDecisionMaker() {
        return decisionMaker;
    }

    public String getReason() {
        return reason;
    }
}