package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import DTO.CandidateDTO;
import DTO.AddressDTO;
import Database.connection;

public class CandidateDAO {
    
    public static ArrayList<CandidateDTO> getList(){
        ArrayList<CandidateDTO> list = new ArrayList<>();
        Connection con = connection.getConnection();
        Statement st;
        try {
            st = con.createStatement();
            // Joining UNGVIEN, CONNGUOI, CMND and TRINHDO tables
            ResultSet rs = st.executeQuery(
                "select * from UNGVIEN " +
                "join CONNGUOI on UNGVIEN.CMND = CONNGUOI.CMND " +
                "join CMND on UNGVIEN.CMND = CMND.soCMND " +
                "join TRINHDO on TRINHDO.maTrinhDo = UNGVIEN.maTrinhDo " +
                "join CHUCVU on CHUCVU.maChucVu = UNGVIEN.maChucVu"
            );
            while(rs.next()) {
                CandidateDTO candidate = new CandidateDTO();
                
                // Set additional CandidateDTO fields
                candidate.setRecruitmentCode(rs.getString("maTuyenDung"));
                candidate.setCandidateCode(rs.getString("maUngVien"));
                candidate.setDesiredSalary(Double.parseDouble(rs.getString("mucLuongDeal")));
                candidate.setStatus(rs.getString("trangThai"));
                candidate.setPosition(rs.getString("tenChucVu"));
                
                // Set inherited PersonDTO fields
                candidate.setName(rs.getString("hoTen"));
                candidate.setGender(rs.getString("gioiTinh"));
                candidate.setDateOfBirth(rs.getDate("ngaySinh").toLocalDate().plusDays(2));
                candidate.setPhoneNumber(rs.getString("SDT"));
                candidate.setMaritalStatus(rs.getString("tinhTrangHonNhan"));
                candidate.setEthnicGroup(rs.getString("danToc"));
                candidate.setReligion(rs.getString("tonGiao"));
                candidate.setEmail(rs.getString("email"));
                
                // Parse address string (format: "houseNumber street, ward, district, city")
                String addrStr = rs.getString("diaChi");
                String[] parts = addrStr.split(",");
                String[] firstPart = parts[0].trim().split(" ");
                String houseNumber = firstPart[0];
                String street = "";
                for (int i = 1; i < firstPart.length; i++) {
                    street += firstPart[i] + " ";
                }
                street = street.trim();
                String ward = parts.length > 1 ? parts[1].trim() : "";
                String district = parts.length > 2 ? parts[2].trim() : "";
                String city = parts.length > 3 ? parts[3].trim() : "";
                candidate.setAddress(new AddressDTO(houseNumber, street, ward, district, city));
                
                // Set identity card fields
                candidate.getIdentityCard().setId(rs.getString("CMND"));
                candidate.getIdentityCard().setPlaceOfIssue(rs.getString("noiCap"));
                candidate.getIdentityCard().setDateOfIssue(rs.getDate("ngayCap").toLocalDate().plusDays(2));
                
                // Set qualification (education level) fields from TRINHDO table
                candidate.getEducationLevel().setEducationLevelId(rs.getString("maTrinhDo"));
                candidate.getEducationLevel().setAcademicLevel(rs.getString("trinhDoHocVan"));
                candidate.getEducationLevel().setProfessionalLevel(rs.getString("trinhdoChuyenMon"));
                candidate.getEducationLevel().setSpecialization(rs.getString("chuyenNganh"));
                
                list.add(candidate);
            }
            connection.closeConnection(con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void insertCandidate(CandidateDTO candidate) {
        Connection con = connection.getConnection();
        try {
            // Insert related qualification, identity card and person data
            QualificationDAO.insertTRINHDO(candidate.getEducationLevel());
            IdentityCardDAO.insertCMND(candidate.getIdentityCard());
            PersonDAO.insertCONNGUOI(candidate);
            
            PreparedStatement pst = con.prepareStatement("insert UNGVIEN values(?,?,?,?,?,?,?)");
            pst.setString(1, candidate.getRecruitmentCode());
            pst.setString(2, candidate.getCandidateCode());
            pst.setString(3, candidate.getIdentityCard().getId());
            pst.setDouble(4, candidate.getDesiredSalary());
            pst.setString(5, candidate.getEducationLevel().getEducationLevelId());
            pst.setString(6, candidate.getPosition());
            pst.setString(7, candidate.getStatus());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteCandidate(String maTrinhDo, String id, String candidateCode) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete UNGVIEN where maUngVien = ?");
            pst.setString(1, candidateCode);
            pst.executeUpdate();
            
            pst = con.prepareStatement("delete CONNGUOI where CMND = ?");
            pst.setString(1, id);
            pst.executeUpdate();
            
            pst = con.prepareStatement("delete CMND where soCMND = ?");
            pst.setString(1, id);
            pst.executeUpdate();
            
            pst = con.prepareStatement("delete TRINHDO where maTrinhDo = ?");
            pst.setString(1, maTrinhDo);
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void deleteCandidateApplied(String maTrinhDo, String candidateCode) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete UNGVIEN where maUngVien = ?");
            pst.setString(1, candidateCode);
            pst.executeUpdate();
            
            pst = con.prepareStatement("delete TRINHDO where maTrinhDo = ?");
            pst.setString(1, maTrinhDo);
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static void updateStatus(String candidateCode, String status) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("update UNGVIEN set trangThai=? where maUngVien = ?");
            pst.setString(1, status);
            pst.setString(2, candidateCode);
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static ArrayList<String> getCandidateCodeList() {
        Connection con = connection.getConnection();
        try {
            ArrayList<String> list = new ArrayList<>();
            PreparedStatement pst = con.prepareStatement("select * from UNGVIEN");
            ResultSet rs = pst.executeQuery();
            while(rs.next()) {
                  list.add(rs.getString("maUngVien")); 
            }
            connection.closeConnection(con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }    
}