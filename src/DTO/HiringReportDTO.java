package DTO;

import java.time.LocalDate;

public class HiringReportDTO {
    
    private String recruitmentId;
    private String position;
    private String qualification;
    private String gender;
    private String ageRange;
    private int requiredPositions;
    private LocalDate applicationDeadline;
    private double minimumSalary;
    private double maximumSalary;
    private int applicationsReceived;
    private int positionsFilled;
    
    public HiringReportDTO() {
    }
    
    public HiringReportDTO(String recruitmentId, String position, String qualification, String gender, String ageRange, 
                          int requiredPositions, LocalDate applicationDeadline, double minimumSalary, 
                          double maximumSalary, int applicationsReceived, int positionsFilled) {
        this.recruitmentId = recruitmentId;
        this.position = position;
        this.qualification = qualification;
        this.gender = gender;
        this.ageRange = ageRange;
        this.requiredPositions = requiredPositions;
        this.applicationDeadline = applicationDeadline;
        this.minimumSalary = minimumSalary;
        this.maximumSalary = maximumSalary;
        this.applicationsReceived = applicationsReceived;
        this.positionsFilled = positionsFilled;
    }

    public int getApplicationsReceived() {
        return applicationsReceived;
    }
    
    public void setApplicationsReceived(int applicationsReceived) {
        this.applicationsReceived = applicationsReceived;
    }
    
    public int getPositionsFilled() {
        return positionsFilled;
    }
    
    public void setPositionsFilled(int positionsFilled) {
        this.positionsFilled = positionsFilled;
    }
    
    public String getRecruitmentId() {
        return recruitmentId;
    }

    public void setRecruitmentId(String recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public int getRequiredPositions() {
        return requiredPositions;
    }

    public void setRequiredPositions(int requiredPositions) {
        this.requiredPositions = requiredPositions;
    }

    public LocalDate getApplicationDeadline() {
        return applicationDeadline;
    }

    public void setApplicationDeadline(LocalDate localDate) {
        this.applicationDeadline = localDate;
    }

    public double getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(double minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public double getMaximumSalary() {
        return maximumSalary;
    }

    public void setMaximumSalary(double maximumSalary) {
        this.maximumSalary = maximumSalary;
    }
}