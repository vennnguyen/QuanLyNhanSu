package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.IdentityCardDTO;
import Database.connection;

public class IdentityCardDAO {

    public static ArrayList<String> getDanhSachMaSo(){
        ArrayList<String> dsMaso = new ArrayList<>();
        Connection con = connection.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select * from CMND"); // Adjust table name if needed
            while(rs.next()) {
                // Assuming the identity card id column is renamed to "id"
                dsMaso.add(rs.getString("id"));
            }
            connection.closeConnection(con);
            return dsMaso;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public static void insertCMND(IdentityCardDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert into CMND values(?,?,?)"); // Adjust table and column names if needed
            pst.setString(1, x.getId());
            pst.setString(2, x.getPlaceOfIssue());
            pst.setDate(3, Date.valueOf(x.getDateOfIssue()));
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateCMND(IdentityCardDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("update CMND set placeOfIssue = ? , dateOfIssue = ? where id = ?");
            pst.setString(1, x.getPlaceOfIssue());
            pst.setDate(2, Date.valueOf(x.getDateOfIssue()));
            pst.setString(3, x.getId());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}