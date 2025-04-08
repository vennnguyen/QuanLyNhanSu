package DTO;

import java.time.LocalDate;

public class ReviewDTO {
    
    private String reviewId;
    private String employeeId;
    private String employeeName;
    private String reviewerId;
    private String reviewerName;
    private LocalDate reviewDate;
    private int reviewScore;
    private String classification;
    private String reviewDetails;
    private String note;
    private String reviewType;
    
    public ReviewDTO() {
        this.reviewId = "";
        this.employeeId = "";
        this.employeeName = "";
        this.reviewerId = "";
        this.reviewerName = "";
        this.reviewDate = null;
        this.reviewScore = 0;
        this.classification = "";
        this.reviewDetails = "";
        this.note = "";
        this.reviewType = "";
    }
    
    public ReviewDTO(String reviewId, String employeeId, String employeeName, String reviewerId,
            String reviewerName, LocalDate reviewDate, int reviewScore, String classification, String reviewDetails,
            String note, String reviewType) {
        super();
        this.reviewId = reviewId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.reviewerId = reviewerId;
        this.reviewerName = reviewerName;
        this.reviewDate = reviewDate;
        this.reviewScore = reviewScore;
        this.classification = classification;
        this.reviewDetails = reviewDetails;
        this.note = note;
        this.reviewType = reviewType;
    }

    public String getReviewType() {
        return reviewType;
    }

    public void setReviewType(String reviewType) {
        this.reviewType = reviewType;
    }

    public String getEmployeeName() {
        return employeeName;
    }
    
    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public String getReviewerId() {
        return reviewerId;
    }
    
    public void setReviewerId(String reviewerId) {
        this.reviewerId = reviewerId;
    }
    
    public String getReviewerName() {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    
    public String getNote() {
        return note;
    }
    
    public void setNote(String note) {
        this.note = note;
    }
    
    public String getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }
    
    public String getEmployeeId() {
        return employeeId;
    }
    
    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }
    
    public LocalDate getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }
    
    public int getReviewScore() {
        return reviewScore;
    }
    
    public void setReviewScore(int reviewScore) {
        this.reviewScore = reviewScore;
    }
    
    public String getClassification() {
        return classification;
    }
    
    public void setClassification(String classification) {
        this.classification = classification;
    }
    
    public String getReviewDetails() {
        return reviewDetails;
    }
    
    public void setReviewDetails(String reviewDetails) {
        this.reviewDetails = reviewDetails;
    }
}