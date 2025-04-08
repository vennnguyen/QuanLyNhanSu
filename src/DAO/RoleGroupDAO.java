package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DTO.RoleGroupDTO;
import Database.connection;

public class RoleGroupDAO{
    
    public static boolean[] getAccountFunctions(String username) {
        boolean[] functions = new boolean[38];
        for (int i = 0; i < 38; i++) {
            functions[i] = false;
        }
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(
                "select * from TAIKHOAN tk " +
                "join CHITIETNHOMQUYEN ct on tk.maNhomQuyen = ct.maNhomQuyen " +
                "where tk.username = ?"
            );
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int index = Integer.valueOf(rs.getString("maChucNang"));
                functions[index - 1] = true;
            }
            connection.closeConnection(con);
            return functions;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static ArrayList<RoleGroupDTO> getRoleGroupList() {
        ArrayList<RoleGroupDTO> list = new ArrayList<>();
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("select * from NHOMQUYEN");
            PreparedStatement pst2;
            ResultSet rs2;
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                RoleGroupDTO roleGroup = new RoleGroupDTO(
                        rs.getString("maNhomQuyen"),
                        rs.getString("tenNhomQuyen"),
                        null
                );
                boolean[] functions = new boolean[38];
                for (int i = 0; i < 38; i++) {
                    functions[i] = false;
                }
                pst2 = con.prepareStatement(
                        "select * from NHOMQUYEN nq join CHITIETNHOMQUYEN ct on nq.maNhomQuyen = ct.maNhomQuyen " +
                        "where nq.maNhomQuyen = ?"
                );
                pst2.setString(1, rs.getString("maNhomQuyen"));
                rs2 = pst2.executeQuery();
                while (rs2.next()) {
                    int index = Integer.valueOf(rs2.getString("maChucNang"));
                    functions[index - 1] = true;
                }
                roleGroup.setFunctionsArray(functions);
                list.add(roleGroup);
            }
            connection.closeConnection(con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean insertRoleGroup(String roleGroupId, String roleGroupName) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert NHOMQUYEN values(?,?)");
            pst.setString(1, roleGroupId);
            pst.setString(2, roleGroupName);
            int flag = pst.executeUpdate();
            connection.closeConnection(con);
            return flag != 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static boolean deleteRoleGroup(String roleGroupId) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("select count(*) from TAIKHOAN where maNhomQuyen = ?");
            pst.setString(1, roleGroupId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int value = rs.getInt(1);
                if (value > 0) {
                    return false;
                }
            }
            pst = con.prepareStatement("delete CHITIETNHOMQUYEN where maNhomQuyen = ?");
            pst.setString(1, roleGroupId);
            pst.executeUpdate();
            
            pst = con.prepareStatement("delete NHOMQUYEN where maNhomQuyen = ?");
            pst.setString(1, roleGroupId);
            int flag = pst.executeUpdate();
            connection.closeConnection(con);
            return flag != 0;
        } catch (SQLException e) {
            return false;
        }
    }
    
    public static void deleteRoleGroupDetails(String roleGroupId) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete CHITIETNHOMQUYEN where maNhomQuyen = ?");
            pst.setString(1, roleGroupId);
            pst.executeUpdate();
        } catch (SQLException e) {
            // Handle exception if necessary
        }
    }
    
    public static void insertRoleGroupDetails(String roleGroupId, String functionId) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert CHITIETNHOMQUYEN values(?,?)");
            pst.setString(1, roleGroupId);
            pst.setString(2, functionId);
            pst.executeUpdate();
        } catch (SQLException e) {
            // Handle exception if necessary
        }
    }
}