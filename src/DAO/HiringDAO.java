package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.HiringReportDTO;
import Database.connection;

public class HiringDAO {
    
    public static ArrayList<HiringReportDTO> getList(){
        ArrayList<HiringReportDTO> list = new ArrayList<>();
        Connection con = connection.getConnection();
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from BAOCAOTUYENDUNG order by maTuyenDung");
            while(rs.next()) {
                HiringReportDTO report = new HiringReportDTO();
                report.setRecruitmentId(rs.getString("maTuyenDung"));
                report.setPosition(rs.getString("chucVu"));
                report.setQualification(rs.getString("hocVan"));
                report.setGender(rs.getString("yeuCauGioiTinh"));
                report.setAgeRange(rs.getString("yeuCauDoTuoi"));
                report.setRequiredPositions(rs.getInt("soLuongCanTuyen"));
                report.setApplicationDeadline(rs.getDate("hanNopHoSo").toLocalDate().plusDays(2));
                report.setMinimumSalary(rs.getDouble("mucLuongToiThieu"));
                report.setMaximumSalary(rs.getDouble("mucLuongToiDa"));
                
                // Get number of applications received
                Statement st1 = con.createStatement();
                ResultSet rs1 = st1.executeQuery("select COUNT(CMND) from UNGVIEN where maTuyenDung = '" + report.getRecruitmentId() + "'");
                if(rs1.next()) {
                    report.setApplicationsReceived(rs1.getInt(1));
                }
                rs1.close();
                st1.close();
                
                // Get number of filled positions
                Statement st2 = con.createStatement();
                ResultSet rs2 = st2.executeQuery("select count(maUngVien) from UNGVIEN uv join NHANVIEN nv on nv.CMND = uv.CMND where uv.maTuyenDung = '" + report.getRecruitmentId() + "'");
                if(rs2.next()) {
                    report.setPositionsFilled(rs2.getInt(1));
                }
                rs2.close();
                st2.close();
                
                list.add(report);
            }
            connection.closeConnection(con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static String getChucVuTuyenDung(String maTuyenDung){
        Connection con = connection.getConnection();
        PreparedStatement pst;
        try {
            String sql = "select chucVu from BAOCAOTUYENDUNG where maTuyenDung = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, maTuyenDung);
            ResultSet rs = pst.executeQuery();
            String position = "";
            if(rs.next()) {
                position = rs.getString(1);
            }
            connection.closeConnection(con);
            return position;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void insertBaoCaoTuyenDung(HiringReportDTO report) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert BAOCAOTUYENDUNG values(?,?,?,?,?,?,?,?,?)");
            pst.setString(1, report.getRecruitmentId());
            pst.setString(2, report.getPosition());
            pst.setString(3, report.getQualification());
            pst.setString(4, report.getGender());
            pst.setString(5, report.getAgeRange());
            pst.setInt(6, report.getRequiredPositions());
            pst.setDate(7, Date.valueOf(report.getApplicationDeadline()));
            pst.setDouble(8, report.getMinimumSalary());
            pst.setDouble(9, report.getMaximumSalary());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteBaoCaoTuyenDung(String maTuyenDung) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete BAOCAOTUYENDUNG where maTuyenDung = ?");
            pst.setString(1, maTuyenDung);
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static String[] getMaTuyenDung() {
        String data[] = null;
        Connection con = connection.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs  = st.executeQuery("select COUNT(DISTINCT maTuyenDung) from BAOCAOTUYENDUNG");
            if(rs.next()) {
                data = new String[rs.getInt(1)];
            }
            rs = st.executeQuery("select * from BAOCAOTUYENDUNG order by maTuyenDung");
            int count = 0;
            while(rs.next()) {
                data[count] = rs.getString("maTuyenDung");
                count++;
            }
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
    
    public static String[] getMaTuyenDungToFilter() {
        String data[] = null;
        Connection con = connection.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs  = st.executeQuery("select COUNT(DISTINCT maTuyenDung) from BAOCAOTUYENDUNG");
            if(rs.next()) {
                data = new String[rs.getInt(1) + 1];
            }
            rs = st.executeQuery("select * from BAOCAOTUYENDUNG order by maTuyenDung");
            data[0] = "Mã tuyển dụng";
            int count = 1;
            while(rs.next()) {
                data[count] = rs.getString("maTuyenDung");
                count++;
            }
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }
}