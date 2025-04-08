package DTO;

import java.time.LocalDate;
import java.util.Random;

import DAO.DepartmentDAO;

// import DAO.access_PHONGBAN;

public class DepartmentDTO {
    
    private String departmentId;
    private String departmentName;
    private LocalDate establishmentDate;
    private String managerId;
    
    public DepartmentDTO(){
        departmentId = "";
        departmentName = "";
        establishmentDate = null;
        managerId = "";
    }
    
    public DepartmentDTO(String departmentId, String departmentName, LocalDate establishmentDate, String managerId){
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.establishmentDate = establishmentDate;
        this.managerId = managerId;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public LocalDate getEstablishmentDate() {
        return establishmentDate;
    }

    public void setEstablishmentDate(LocalDate establishmentDate) {
        this.establishmentDate = establishmentDate;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
    
    public int[] getSalaryData() {
        Random rd = new Random();
        return new int[] {rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50};
    }
    
    public int[] getEmployeeCount() {
        Random rd = new Random();
        return new int[] {rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50};
    }
    
    public int[] getAgeCount() {
        Random rd = new Random();
        return new int[] {rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50};
    }
    
    public int[] getPositionCount() {
        Random rd = new Random();
        return new int[] {rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50, rd.nextInt(155)+50};
    }
    
    public int[] getGenderCount() {
        Random rd = new Random();
        return new int[] {rd.nextInt(155)+50, rd.nextInt(155)+50};
    }
    
    public double getAverageSalaryDepartment() {
        double data[] = DepartmentDAO.getAverageSalaryData(this.departmentId);
        double value = (data[1]+data[2])/data[0];
        return value;
    }
}