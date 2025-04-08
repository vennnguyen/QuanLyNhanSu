package BUS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import DAO.TimeSheetDAO;
import DTO.TimeSheet;
import DTO.SUPPORT;

public class TimeSheetBUS {

    private ArrayList<TimeSheet> list;
    private ArrayList<TimeSheet> list_one;
    
    public TimeSheetBUS() {
        this.list = new ArrayList<>();
    }

    public TimeSheetBUS(ArrayList<TimeSheet> list) {
        this.list = list;
    }

    public ArrayList<TimeSheet> getList() {
        return list;
    }
    public void getDataFromDatabase() {
        this.list = TimeSheetDAO.getList();
    }
    
    public TimeSheet getBangChamCong(String tsId) {
        tsId = tsId.trim();
        for (TimeSheet ts : list) {
            if (ts.getTimesheetId().equalsIgnoreCase(tsId)) {
                return ts;
            }
        }
        return null;
    }

    public Object[][] getObjectseToRender() {
        int n = this.list.size();
        Object[][] obj = new Object[n][];
        for (int i = 0; i < n; i++) {
            TimeSheet temp = list.get(i);
            obj[i] = new Object[]{
                (i + 1) + "",
                temp.getTimesheetId(),
                TimeSheetDAO.getTen(temp.getTimesheetId()),
                temp.getMonth() + "/" + temp.getYear(),
                temp.getWorkingDays() + "",
                temp.getAbsentDays() + "",
                temp.getLateDays() + "",
                temp.getOvertimeHours() + ""
            };
        }
        return obj;
    }
    
    public Object[][] getObjectseToRender_Them() {
        // Build unique list based on employeeId
        HashMap<String, TimeSheet> uniqueElements = new HashMap<>();
        for (TimeSheet ts : list) {
            if (!uniqueElements.containsKey(ts.getEmployeeId())) {
                uniqueElements.put(ts.getEmployeeId(), ts);
            }
        }
        list_one = new ArrayList<>(uniqueElements.values());
        int n = list_one.size();
        Object[][] obj = new Object[n][];
        for (int i = 0; i < n; i++) {
            TimeSheet temp = list_one.get(i);
            obj[i] = new Object[]{
                (i + 1) + "",
                TimeSheetDAO.getTen(temp.getTimesheetId()),
                "Chưa chấm công"
            };
        }
        return obj;
    }
    
    /* Them timesheet vao list */
    public void themVaoList(TimeSheet ts) {
        this.list.add(ts);
    }
    
    /* Tim kiem */
    @SuppressWarnings({"UnusedAssignment", "static-access"})
    public ArrayList<TimeSheet> timBangChamCong(String find) {
        ArrayList<TimeSheet> temp = new ArrayList<>();
        find = find.trim();
        for (TimeSheet ts : list) {
            if (ts.getTimesheetId().trim().contains(find) ||
                TimeSheetDAO.getTen(ts.getTimesheetId()).toLowerCase().contains(find.toLowerCase())) {
                temp.add(ts);
            }
        }
        return temp;
    }
    
    /* Xoa theo ma */
    public void xoaBangMa(String ma) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTimesheetId().equalsIgnoreCase(ma)) {
                list.remove(i);
                return;
            }
        }
    }
    
    /* Sort theo ma bang cham cong */
    public void sortMaBCC(int type) {
        list.sort((o1, o2) -> o1.getTimesheetId().compareTo(o2.getTimesheetId()));
        if (type != 0) {
            Collections.reverse(list);
        }
    }
    
    /* Sort theo ma nhan vien */
    public void sortMaNV(int type) {
        list.sort((o1, o2) -> o1.getEmployeeId().compareTo(o2.getEmployeeId()));
        if (type != 0) {
            Collections.reverse(list);
        }
    }
    
    /* Sort theo thoi gian cham cong (nam & thang) */
    public void sortThoiGianChamCong(int type) {
        list.sort((o1, o2) -> SUPPORT.compareDouble(o1.getYear(), o2.getYear()));
        // For timesheets in the same year, sort by month
        for (int i = 0; i < list.size(); i++) {
            for (int j = i + 1; j < list.size(); j++) {
                if (list.get(i).getYear() == list.get(j).getYear() &&
                    list.get(i).getMonth() > list.get(j).getMonth()) {
                    Collections.swap(list, i, j);
                }
            }
        }
        if (type != 0) {
            Collections.reverse(list);
        }
    }
    
    // Filter by thang (month)
    public ArrayList<TimeSheet> getBangChamCongTheoThang(String thang) {
        ArrayList<TimeSheet> arr = new ArrayList<>();
        for (TimeSheet ts : list) {
            if ((ts.getMonth() + "").equals(thang)) {
                arr.add(ts);
            }
        }
        return arr;
    }
    
    // Filter by ma nhan vien (employee id)
    public ArrayList<TimeSheet> getBangChamCongTheoMaNV(String maNhanVien) {
        ArrayList<TimeSheet> arr = new ArrayList<>();
        for (TimeSheet ts : list) {
            if (ts.getEmployeeId().equals(maNhanVien)) {
                arr.add(ts);
            }
        }
        return arr;
    }
    
    // Filter by nam (year)
    public ArrayList<TimeSheet> getBangChamCongTheoNam(String nam) {
        ArrayList<TimeSheet> arr = new ArrayList<>();
        for (TimeSheet ts : list) {
            if ((ts.getYear() + "").equals(nam)) {
                arr.add(ts);
            }
        }
        return arr;
    }
}