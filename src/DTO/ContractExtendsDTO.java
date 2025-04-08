package DTO;

public class ContractExtendsDTO extends DecisionDTO {

    public double extensionTime;

    public ContractExtendsDTO() {
        super();
        this.extensionTime = 0;
    }

    public ContractExtendsDTO(double extensionTime, String decisionId, String decisionDate, String decisionMaker, String reason) {
        super(decisionId, decisionDate, decisionMaker, reason);
        this.extensionTime = extensionTime;
    }

    public void setExtensionTime(double extensionTime) {
        this.extensionTime = extensionTime;
    }

    public double getExtensionTime() {
        return extensionTime;
    }
}
