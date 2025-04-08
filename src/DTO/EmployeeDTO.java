package DTO;

import java.time.LocalDate;

import DAO.DepartmentDAO;

// import DAO.access_PHONGBAN;

public class EmployeeDTO extends PersonDTO {

    private String employeeId;
    private String departmentId;
    private QualificationDTO qualification;
    private PositionDTO position;
    private AccountDTO account;

    public EmployeeDTO() {
        super();
        this.employeeId = null;
        this.departmentId = null;
        this.qualification = new QualificationDTO();
        this.position = null;
        this.account = new AccountDTO();
    }
    
    public EmployeeDTO(String employeeId, String fullName, String gender, LocalDate dob, AddressDTO address, String phone, IdentityCardDTO idCard, String ethnicity, String maritalStatus, String religion, String email, String departmentId, QualificationDTO qualification, PositionDTO position, AccountDTO account) {
        super(idCard, fullName, gender, dob, address, phone, ethnicity, maritalStatus, religion, email);
        this.employeeId = employeeId;
        this.departmentId = departmentId;
        this.qualification = qualification;
        this.position = position;
        this.account = account;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setQualification(QualificationDTO qualification) {
        this.qualification = qualification;
    }

    public void setPosition(PositionDTO position) {
        this.position = position;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public String getEmployeeId() {
        return this.employeeId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public QualificationDTO getQualification() {
        return this.qualification;
    }

    public PositionDTO getPosition() {
        return this.position;
    }

    public AccountDTO getAccount() {
        return this.account;
    }
    
    public double getBaseSalary() {
        if (this instanceof PermanentEmployeeDTO) {
            return ((PermanentEmployeeDTO)this).getContract().getBaseSalary();
        } else {
            return ((ProbationEmployeeDTO)this).getProbationSalary();
        }
    }
    
    public String[] getObjectToRender() {
        String employeeType = "Permanent";
        String startDate = "";
        String endDate = "";
        String contract = "None";
        String contractType = "Undefined";
        
        if(this instanceof ProbationEmployeeDTO) {
            employeeType = "Probation";
            startDate = SUPPORT.LocalDateToString(((ProbationEmployeeDTO)this).getStartProbationDate());
            endDate = SUPPORT.LocalDateToString(((ProbationEmployeeDTO)this).getEndProbationDate());
        } else {
            contract = ((PermanentEmployeeDTO)this).getContract().getContractId();
            contractType = ((PermanentEmployeeDTO)this).getContract().getContractType();
            startDate = SUPPORT.LocalDateToString(((PermanentEmployeeDTO)this).getContract().getStartDate());
            endDate = SUPPORT.LocalDateToString(((PermanentEmployeeDTO)this).getContract().getEndDate());
        }
        
        String[] data = new String[] {
            this.getEmployeeId(), 
            this.getName(), 
            DepartmentDAO.getTenTuMaSo(this.departmentId), 
            this.getPosition().getPositionName(), 
            SUPPORT.LocalDateToString(this.getPosition().getAppointmentDate()), 
            employeeType,
            contract, 
            startDate, 
            endDate, 
            contractType, 
            SUPPORT.changeSalaryToFormatString(this.getBaseSalary()),
            this.getGender(), 
            SUPPORT.LocalDateToString(this.getDateOfBirth()), 
            this.getAddress().toString(), 
            this.getPhoneNumber(), 
            this.getIdentityCard().getId() + " - " + this.getIdentityCard().getPlaceOfIssue() + " - " + SUPPORT.LocalDateToString(this.getIdentityCard().getDateOfIssue()), 
            this.getQualification().getProfessionalLevel() + " - " + this.getQualification().getSpecialization(), 
            this.getEthnicGroup(), 
            this.getReligion(), 
            this.getMaritalStatus(),
            this.getName(), 
            this.getAddress().getCity(), 
            this.getPhoneNumber(), 
            this.getEmail(),
        };
        
        return data;
    }
    
    public String[] getDataToRenderDepartmentDetailInfoEmployee() {
        String data[] = {
            this.getEmployeeId(), 
            this.getName(), 
            this.getGender(), 
            SUPPORT.LocalDateToString(this.getDateOfBirth()), 
            this.getPhoneNumber(), 
            this.getAddress().getCity().trim(), 
            DepartmentDAO.getTenTuMaSo(this.getDepartmentId()), 
            this.getPosition().getPositionName(), 
            "         " + this.getPosition().getAppointmentDate().toString()
        };
        return data;
    }
    
    public String[] getDataToFix() {
        String employeeType = "Permanent Employee";
        String startDate = "";
        String endDate = "";
        String salary = "";
        
        if(this instanceof PermanentEmployeeDTO) {
            startDate = SUPPORT.LocalDateToString(((PermanentEmployeeDTO)this).getContract().getStartDate());
            endDate = ((PermanentEmployeeDTO)this).getContract().getContractType();
            salary = SUPPORT.changeSalaryToFormatStringToFix(((PermanentEmployeeDTO)this).getContract().getBaseSalary());
            
        } else {
            employeeType = "Probation Employee";
            startDate = SUPPORT.LocalDateToString(((ProbationEmployeeDTO)this).getStartProbationDate());
            endDate = SUPPORT.LocalDateToString(((ProbationEmployeeDTO)this).getEndProbationDate());
            salary = SUPPORT.changeSalaryToFormatStringToFix(((ProbationEmployeeDTO)this).getProbationSalary());
        }
        
        String data[] = { 
            this.getEmployeeId(), 
            this.getName(), 
            this.getGender(), 
            SUPPORT.LocalDateToString(this.getDateOfBirth()), 
            this.getPhoneNumber(), 
            this.getEmail(),
            this.getAddress().getHouseNumber(), 
            this.getAddress().getStreet(), 
            this.getAddress().getWard(), 
            this.getAddress().getDistrict(), 
            this.getAddress().getCity(),
            this.getQualification().getAcademicLevel(), 
            this.getQualification().getProfessionalLevel(), 
            this.getQualification().getSpecialization(),
            this.getIdentityCard().getId(), 
            SUPPORT.LocalDateToString(this.getIdentityCard().getDateOfIssue()), 
            this.getIdentityCard().getPlaceOfIssue(),
            this.getEthnicGroup(), 
            this.getReligion(), 
            this.getMaritalStatus(),
            DepartmentDAO.getTenTuMaSo(this.getDepartmentId()),
            this.getPosition().getPositionName(), 
            SUPPORT.LocalDateToString(this.getPosition().getAppointmentDate()),
            employeeType,
            startDate, 
            endDate, 
            salary,
            this.getAccount().getAvatarImg(),
        };
        return data;
    }
}