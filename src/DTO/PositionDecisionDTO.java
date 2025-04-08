package DTO;

public class PositionDecisionDTO extends DecisionDTO {

    public String initialPosition;
    public String positionAfterDecision;
    
    public PositionDecisionDTO() {
        super();
        this.initialPosition = "";
        this.positionAfterDecision = "";
    }
    
    public PositionDecisionDTO(String decisionId, String decisionDate, String decisionMaker, String reason, 
                              String initialPosition, String positionAfterDecision) {
        super(decisionId, decisionDate, decisionMaker, reason);
        this.initialPosition = initialPosition;
        this.positionAfterDecision = positionAfterDecision;
    }

    public void setInitialPosition(String initialPosition) {
        this.initialPosition = initialPosition;
    }
    
    public void setPositionAfterDecision(String positionAfterDecision) {
        this.positionAfterDecision = positionAfterDecision;
    }

    public String getInitialPosition() {
        return initialPosition;
    }

    public String getPositionAfterDecision() {
        return positionAfterDecision;
    }
}