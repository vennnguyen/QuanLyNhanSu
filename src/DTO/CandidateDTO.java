package DTO;

import java.time.LocalDate;

public class CandidateDTO extends PersonDTO {

    private String recruitmentCode;
    private String candidateCode;
    private double desiredSalary;
    private QualificationDTO qualification;
    private String position;
    private String status;

    public CandidateDTO(IdentityCardDTO identityCard, String name, String gender, LocalDate dateOfBirth, AddressDTO address, String phoneNumber, String maritalStatus, String ethnicGroup, String religion, String email, String recruitmentCode, String candidateCode, double desiredSalary, QualificationDTO qualification, String position, String status) {
        super(identityCard, name, gender, dateOfBirth, address, phoneNumber, maritalStatus, ethnicGroup, religion, email);
        this.recruitmentCode = recruitmentCode;
        this.candidateCode = candidateCode;
        this.desiredSalary = desiredSalary;
        this.qualification = qualification;
        this.position = position;
        this.status = status;
    }
    
    public CandidateDTO() {
        super();
        this.recruitmentCode = null;
        this.candidateCode = null;
        this.desiredSalary = 0;
        this.qualification = new QualificationDTO();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setRecruitmentCode(String recruitmentCode) {
        this.recruitmentCode = recruitmentCode;
    }

    public void setCandidateCode(String candidateCode) {
        this.candidateCode = candidateCode;
    }

    public void setDesiredSalary(double desiredSalary) {
        this.desiredSalary = desiredSalary;
    }

    public void setEducationLevel(QualificationDTO educationLevel) {
        this.qualification = educationLevel;
    }

    public String getRecruitmentCode() {
        return recruitmentCode;
    }

    public String getCandidateCode() {
        return candidateCode;
    }

    public double getDesiredSalary() {
        return desiredSalary;
    }

    public QualificationDTO getEducationLevel() {
        return qualification;
    }

    public String[] getDataForRecruitment() {
        String data[] = {
            this.getName(),
            this.getGender(),
            SUPPORT.LocalDateToString(this.getDateOfBirth()),
            this.getPhoneNumber(),
            this.getEducationLevel().getAcademicLevel(),
            this.getEducationLevel().getProfessionalLevel(),
            this.getEducationLevel().getSpecialization(),
            this.getAddress().getHouseNumber(),
            this.getAddress().getStreet(),
            this.getAddress().getWard(),
            this.getAddress().getDistrict(),
            this.getAddress().getCity(),
            this.getEthnicGroup(),
            this.getReligion(),
            this.getIdentityCard().getId(),
            SUPPORT.LocalDateToString(this.getIdentityCard().getDateOfIssue()),
            this.getIdentityCard().getPlaceOfIssue(),
            this.getMaritalStatus(),
            this.getPosition(),
            SUPPORT.changeSalaryToFormatString(this.getDesiredSalary()),
            this.getEmail(),
            this.getCandidateCode()
        };
        return data;
    }
}
