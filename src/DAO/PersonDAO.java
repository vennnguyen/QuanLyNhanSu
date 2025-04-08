package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DTO.PersonDTO;
import Database.connection;

public class PersonDAO {
    
    public static void insertCONNGUOI(PersonDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert into CONNGUOI values(?,?,?,?,?,?,?,?,?,?)");
            pst.setString(1, x.getIdentityCard().getId());
            pst.setString(2, x.getName());
            pst.setString(3, x.getGender());
            pst.setDate(4, Date.valueOf(x.getDateOfBirth()));
            pst.setString(5, x.getAddress().toString());
            pst.setString(6, x.getPhoneNumber());
            pst.setString(7, x.getMaritalStatus());
            pst.setString(8, x.getEthnicGroup());
            pst.setString(9, x.getReligion());
            pst.setString(10, x.getEmail());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateCONNGUOI(PersonDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(
                "update CONNGUOI set hoTen = ?, gioiTinh = ?, ngaySinh = ?, diaChi = ?, SDT = ?, tinhTrangHonNhan = ?, danToc = ?, tonGiao = ?, email = ? where CMND = ?"
            );
            pst.setString(1, x.getName());
            pst.setString(2, x.getGender());
            pst.setDate(3, Date.valueOf(x.getDateOfBirth()));
            pst.setString(4, x.getAddress().toString());
            pst.setString(5, x.getPhoneNumber());
            pst.setString(6, x.getMaritalStatus());
            pst.setString(7, x.getEthnicGroup());
            pst.setString(8, x.getReligion());
            pst.setString(9, x.getEmail());
            pst.setString(10, x.getIdentityCard().getId());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}