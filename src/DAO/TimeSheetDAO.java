package DAO;

import java.util.ArrayList;
import DTO.TimeSheet;
import Database.connection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TimeSheetDAO {

    // Get list of timesheets
    public static ArrayList<TimeSheet> getList() {
        Connection conn = connection.getConnection();
        try {
            ArrayList<TimeSheet> list = new ArrayList<>();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from BANGCHAMCONG");
            while (rs.next()) {
                TimeSheet temp = new TimeSheet();
                temp.setTimesheetId(rs.getString("maBangChamCong"));
                temp.setEmployeeId(rs.getString("maNhanVien"));
                temp.setMonth(Integer.parseInt(rs.getString("thangChamCong")));
                temp.setYear(Integer.parseInt(rs.getString("namChamCong")));
                temp.setWorkingDays(Integer.parseInt(rs.getString("soNgayLamViec")));
                temp.setAbsentDays(Integer.parseInt(rs.getString("soNgayNghi")));
                temp.setLateDays(Integer.parseInt(rs.getString("soNgayTre")));
                temp.setOvertimeHours(Integer.parseInt(rs.getString("soGioLamThem")));
                temp.setDetail(rs.getString("chiTiet"));
                // status field remains null unless stored in DB
                list.add(temp);
            }
            connection.closeConnection(conn);
            return list;
        } catch (SQLException e) {
        }
        return null;
    }

    // Delete timesheet by timesheetId
    public static void xoaBangChamCongTheoMa(String timesheetId) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete BANGCHAMCONG where maBangChamCong = ?");
            pst.setString(1, timesheetId);
            pst.executeUpdate();
            System.out.println(1);
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get a timesheet by timesheetId
    public static TimeSheet getBangChamCongTuMa(String timesheetId) {
        Connection con = connection.getConnection();
        try {
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("select * from BANGCHAMCONG where maBangChamCong = N'" + timesheetId + "'");
            while (rs.next()) {
                TimeSheet ts = new TimeSheet();
                ts.setTimesheetId(rs.getString("maBangChamCong"));
                ts.setEmployeeId(rs.getString("maNhanVien"));
                ts.setMonth(rs.getInt("thangChamCong"));
                ts.setYear(rs.getInt("namChamCong"));
                ts.setWorkingDays(rs.getInt("soNgayLamViec"));
                ts.setAbsentDays(rs.getInt("soNgayNghi"));
                ts.setLateDays(rs.getInt("soNgayTre"));
                ts.setOvertimeHours(rs.getInt("soGioLamThem"));
                ts.setDetail(rs.getString("chiTiet"));
                connection.closeConnection(con);
                return ts;
            }
        } catch (SQLException e) {
        }
        return null;
    }

    // Get employee name to render (joining with NHANVIEN and CONNGUOI)
    public static String getTen(String timesheetId) {
        Connection con = connection.getConnection();
        try {
            String ten = "";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from BANGCHAMCONG join NHANVIEN on NHANVIEN.maNhanVien = BANGCHAMCONG.maNhanVien join CONNGUOI on CONNGUOI.CMND = NHANVIEN.CMND where BANGCHAMCONG.maBangChamCong = '" + timesheetId + "' ");
            while (rs.next()) {
                ten = rs.getString("maNhanVien") + " - " + rs.getString("hoTen");
            }
            connection.closeConnection(con);
            return ten;
        } catch (SQLException e) {
        }
        return null;
    }

    // Get department id from department name
    public static String getMaPBTuTen(String ten) {
        Connection con = connection.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select maPhong from PHONGBAN where tenPhong = N'" + ten + "'");
            while (rs.next()) {
                return rs.getString("maPhong");
            }
            connection.closeConnection(con);
        } catch (SQLException e) {
        }
        connection.closeConnection(con);
        return null;
    }

    // Get timesheets by department id
    public static ArrayList<TimeSheet> getBangChamCongTheoMaPhong(String maPhong) {
        Connection con = connection.getConnection();
        try {
            ArrayList<TimeSheet> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from BANGCHAMCONG BCC join NHANVIEN NV on BCC.maNhanVien = NV.maNhanVien join PHONGBAN PB on NV.maPhong = PB.maPhong where PB.maPhong = '" + maPhong + "'");
            while (rs.next()) {
                TimeSheet ts = new TimeSheet();
                ts.setTimesheetId(rs.getString("maBangChamCong"));
                ts.setEmployeeId(rs.getString("maNhanVien"));
                ts.setMonth(rs.getInt("thangChamCong"));
                ts.setYear(rs.getInt("namChamCong"));
                ts.setWorkingDays(rs.getInt("soNgayLamViec"));
                ts.setAbsentDays(rs.getInt("soNgayNghi"));
                ts.setLateDays(rs.getInt("soNgayTre"));
                ts.setOvertimeHours(rs.getInt("soGioLamThem"));
                ts.setDetail(rs.getString("chiTiet"));
                list.add(ts);
            }
            connection.closeConnection(con);
            return list;
        } catch (SQLException e) {
        }
        return null;
    }

    // Get timesheets by employee id
    public static ArrayList<TimeSheet> getBangChamCongTheoMaNhanVien(String maNhanVien) {
        Connection con = connection.getConnection();
        try {
            ArrayList<TimeSheet> list = new ArrayList<>();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from BANGCHAMCONG BCC join NHANVIEN NV on BCC.maNhanVien = NV.maNhanVien join PHONGBAN PB on NV.maPhong = PB.maPhong where NV.maNhanVien = '" + maNhanVien + "'");
            while (rs.next()) {
                TimeSheet ts = new TimeSheet();
                ts.setTimesheetId(rs.getString("maBangChamCong"));
                ts.setEmployeeId(rs.getString("maNhanVien"));
                ts.setMonth(rs.getInt("thangChamCong"));
                ts.setYear(rs.getInt("namChamCong"));
                ts.setWorkingDays(rs.getInt("soNgayLamViec"));
                ts.setAbsentDays(rs.getInt("soNgayNghi"));
                ts.setLateDays(rs.getInt("soNgayTre"));
                ts.setOvertimeHours(rs.getInt("soGioLamThem"));
                ts.setDetail(rs.getString("chiTiet"));
                list.add(ts);
            }
            connection.closeConnection(con);
            return list;
        } catch (SQLException e) {
        }
        return null;
    }

    // Insert timesheet into database (using individual values)
    public static void themBangChamCongDatabase(String timesheetId, String employeeId, int month, int year, int workingDays, int absentDays, int lateDays, int overtimeHours, String detail) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert BANGCHAMCONG values(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, timesheetId);
            pst.setString(2, employeeId);
            pst.setInt(3, month);
            pst.setInt(4, year);
            pst.setInt(5, workingDays);
            pst.setInt(6, absentDays);
            pst.setInt(7, lateDays);
            pst.setInt(8, overtimeHours);
            pst.setString(9, detail);
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
        }
    }

    // Insert a TimeSheet object into database
    public static void insertBCC(TimeSheet ts) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert BANGCHAMCONG values(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, ts.getTimesheetId());
            pst.setString(2, ts.getEmployeeId());
            pst.setInt(3, ts.getMonth());
            pst.setInt(4, ts.getYear());
            pst.setInt(5, ts.getWorkingDays());
            pst.setInt(6, ts.getAbsentDays());
            pst.setInt(7, ts.getLateDays());
            pst.setInt(8, ts.getOvertimeHours());
            pst.setString(9, ts.getDetail());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
        }
    }

    // Update timesheet in database
    public static void updateBANGCHAMCONG(TimeSheet ts) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("update BANGCHAMCONG set soNgayLamViec = ?, soNgayNghi = ?, soNgayTre = ?, soGioLamThem = ?, chiTiet = ? where maBangChamCong = ?");
            pst.setInt(1, ts.getWorkingDays());
            pst.setInt(2, ts.getAbsentDays());
            pst.setInt(3, ts.getLateDays());
            pst.setInt(4, ts.getOvertimeHours());
            pst.setString(5, ts.getDetail());
            pst.setString(6, ts.getTimesheetId());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}