package DTO;

import java.time.LocalDate;

public class HiringBatchDTO {
    
    private String hiringId;
    private String employeeId;
    private LocalDate hiringDate;
    
    public HiringBatchDTO(){
        this.hiringId = "";
        this.employeeId = "";
        this.hiringDate = null;
    }
    
    public HiringBatchDTO(String hiringId, String employeeId, LocalDate hiringDate){
        this.hiringId = hiringId;
        this.employeeId = employeeId;
        this.hiringDate = hiringDate;
    }

    public String getHiringId() {
        return hiringId;
    }

    public void setHiringId(String hiringId) {
        this.hiringId = hiringId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getHiringDate() {
        return hiringDate;
    }

    public void setHiringDate(LocalDate hiringDate) {
        this.hiringDate = hiringDate;
    }
}
