package DTO;


public class QualificationDTO {
    
    private String educationLevelId;
    private String academicLevel;
    private String professionalLevel;
    private String specialization;
    
    public QualificationDTO(){
        
    }
    
    public QualificationDTO(String educationLevelId, String academicLevel, String professionalLevel, String specialization){
        this.educationLevelId = educationLevelId;
        this.academicLevel = academicLevel;
        this.professionalLevel = professionalLevel;
        this.specialization = specialization;
    }

    public String getEducationLevelId() {
        return educationLevelId;
    }

    public void setEducationLevelId(String educationLevelId) {
        this.educationLevelId = educationLevelId;
    }

    public String getAcademicLevel() {
        return academicLevel;
    }

    public void setAcademicLevel(String academicLevel) {
        this.academicLevel = academicLevel;
    }

    public String getProfessionalLevel() {
        return professionalLevel;
    }

    public void setProfessionalLevel(String professionalLevel) {
        this.professionalLevel = professionalLevel;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}