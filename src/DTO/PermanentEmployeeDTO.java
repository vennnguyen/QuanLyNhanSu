package DTO;

import java.time.LocalDate;

public class PermanentEmployeeDTO extends EmployeeDTO {

    public LaborContractDTO contract;

    public PermanentEmployeeDTO() {
        super();
        this.contract = null;
    }

    public PermanentEmployeeDTO(LaborContractDTO contractId, String employeeId, AccountDTO account, String fullName, String gender, LocalDate dateOfBirth, AddressDTO address, String phoneNumber, IdentityCardDTO idCard, String ethnicity, String maritalStatus, String religion, String email, String departmentId, QualificationDTO qualification, PositionDTO position) {
        super(employeeId, fullName, gender, dateOfBirth, address, phoneNumber, idCard, ethnicity, maritalStatus, religion, email, departmentId, qualification, position, account);
        this.contract = contractId;
    }

    public void setContract(LaborContractDTO contractId) {
        this.contract = contractId;
    }

    public LaborContractDTO getContract() {
        return contract;
    }
}
