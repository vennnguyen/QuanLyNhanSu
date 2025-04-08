package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import DTO.QualificationDTO;
import Database.connection;

public class QualificationDAO {
    public static void insertTRINHDO(QualificationDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert TRINHDO values(?,?,?,?)");
            pst.setString(1, x.getEducationLevelId());
            pst.setString(2, x.getAcademicLevel());
            pst.setString(3, x.getProfessionalLevel());
            pst.setString(4, x.getSpecialization());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateTRINHDO(QualificationDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("update TRINHDO set trinhDoHocVan = ?, trinhDoChuyenMon = ?, chuyenNganh = ? where maTrinhDo = ?");
            pst.setString(1, x.getAcademicLevel());
            pst.setString(2, x.getProfessionalLevel());
            pst.setString(3, x.getSpecialization());
            pst.setString(4, x.getEducationLevelId());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}