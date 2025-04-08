package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DTO.PositionDTO;
import Database.connection;

public class PositionDAO {
    public static void deleteChucVu(String positionId) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("delete CHUCVU where maChucVu = ?");
            st.setString(1, positionId);
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void insertChucVu(PositionDTO position) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("insert CHUCVU values(?,?,?,?)");
            st.setString(1, position.getPositionId());
            st.setString(2, position.getPositionName());
            st.setDouble(3, position.getPositionAllowance());
            st.setDate(4, Date.valueOf(position.getAppointmentDate()));
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateChucVu(PositionDTO position) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement st = con.prepareStatement("update CHUCVU set tenChucVu = ?, phuCapChucVu = ?, ngayNhanChuc = ? where maChucVu = ?");
            st.setString(1, position.getPositionName());
            st.setDouble(2, position.getPositionAllowance());
            st.setDate(3, Date.valueOf(position.getAppointmentDate()));
            st.setString(4, position.getPositionId());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}