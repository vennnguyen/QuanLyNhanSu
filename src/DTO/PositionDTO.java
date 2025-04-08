package DTO;

import java.time.LocalDate;

public class PositionDTO {
    private String positionId;
    private String positionName;
    private double positionAllowance;
    private LocalDate appointmentDate;

    public PositionDTO() {
        this.positionId = "";
        this.positionName = "";
        this.positionAllowance = 0;
        this.appointmentDate = null;
    }

    public PositionDTO(String positionId, String positionName, double positionAllowance, LocalDate appointmentDate) {
        this.positionId = positionId;
        this.positionName = positionName;
        this.positionAllowance = positionAllowance;
        this.appointmentDate = appointmentDate;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public double getPositionAllowance() {
        return positionAllowance;
    }

    public void setPositionAllowance(double positionAllowance) {
        this.positionAllowance = positionAllowance;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }
}