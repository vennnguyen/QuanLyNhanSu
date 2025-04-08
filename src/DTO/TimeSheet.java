package DTO;

public class TimeSheet {

    private String timesheetId;
    private String employeeId;
    private int month;
    private int year;
    private int workingDays;
    private int absentDays;
    private int lateDays;
    private int overtimeHours;
    private String detail;
    private String status;
    
    public TimeSheet() {
        this.timesheetId = null;
        this.employeeId = "";
        this.month = 1;
        this.year = 2000;
        this.workingDays = 0;
        this.absentDays = 0;
        this.lateDays = 0;
        this.overtimeHours = 0;
        this.detail = null;
        this.status = null;
    }

    public TimeSheet(String timesheetId, String employeeId, int month, int year, int workingDays, int absentDays, int lateDays, int overtimeHours, String detail, String status) {
        this.timesheetId = timesheetId;
        this.employeeId = employeeId;
        this.month = month;
        this.year = year;
        this.workingDays = workingDays;
        this.absentDays = absentDays;
        this.lateDays = lateDays;
        this.overtimeHours = overtimeHours;
        this.detail = detail;
        this.status = status;
    }
    
    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
    
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTimesheetId(String timesheetId) {
        this.timesheetId = timesheetId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }

    public void setAbsentDays(int absentDays) {
        this.absentDays = absentDays;
    }

    public void setLateDays(int lateDays) {
        this.lateDays = lateDays;
    }

    public void setOvertimeHours(int overtimeHours) {
        this.overtimeHours = overtimeHours;
    }

    public String getTimesheetId() {
        return timesheetId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public int getWorkingDays() {
        return workingDays;
    }

    public int getAbsentDays() {
        return absentDays;
    }

    public int getLateDays() {
        return lateDays;
    }

    public int getOvertimeHours() {
        return overtimeHours;
    }

    public Object[][] getObjectToRender() {
        Object[][] data = new Object[][]{{
            this.getTimesheetId(),
            this.getEmployeeId(),
            this.getMonth() + "/" + this.getYear(),
            this.getWorkingDays(),
            this.getAbsentDays(),
            this.getLateDays(),
            this.getOvertimeHours(),}
        };
        return data;
    }

    public String[] getDataToRenderEdit() {
        return new String[]{
            this.getTimesheetId(),
            this.getEmployeeId(),
            this.getMonth() + "",
            this.getYear() + "",
            this.getWorkingDays() + "",
            this.getAbsentDays() + "",
            this.getLateDays() + "",
            this.getOvertimeHours() + ""
        };
    }
}