package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.ReviewDTO;
import DTO.SUPPORT;
import Database.connection;

public class ReviewDAO {
    
    public static ArrayList<ReviewDTO> getList(){
        ArrayList<ReviewDTO> list = new ArrayList<>();
        Connection con = connection.getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select dg.maDanhGia, nv.maNhanVien, cn.hoTen as tenNhanVien, "
                    + "dg.nguoiDanhGia, cn2.hoTen as tenNguoiDanhGia, dg.ngayDanhGia, "
                    + "dg.diemDanhGia, dg.xepLoaiDanhGia, dg.chiTietDanhGia, dg.loaiDanhGia, dg.ghiChu "
                    + "from CONNGUOI cn join NHANVIEN nv on cn.CMND = nv.CMND "
                    + "join BANGDANHGIA dg on dg.maNhanVien = nv.maNhanVien "
                    + "join NHANVIEN nv2 on dg.nguoiDanhGia = nv2.maNhanVien "
                    + "join CONNGUOI cn2 on cn2.CMND = nv2.CMND "
                    + "order by dg.ngayDanhGia desc";
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                ReviewDTO review = new ReviewDTO();
                review.setReviewId(rs.getString("maDanhGia"));
                review.setEmployeeId(rs.getString("maNhanVien"));
                review.setEmployeeName(rs.getString("tenNhanVien"));
                review.setReviewerId(rs.getString("nguoiDanhGia"));
                review.setReviewerName(rs.getString("tenNguoiDanhGia"));
                review.setReviewDate(rs.getDate("ngayDanhGia").toLocalDate().plusDays(2));
                review.setReviewScore(rs.getInt("diemDanhGia"));
                review.setClassification(rs.getString("xepLoaiDanhGia"));
                review.setReviewDetails(rs.getString("chiTietDanhGia"));
                review.setReviewType(rs.getString("loaiDanhGia"));
                review.setNote(rs.getString("ghiChu"));
                list.add(review);
            }
            connection.closeConnection(con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static Object[][] getListNhanVien() {
        Connection con = connection.getConnection();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("select count(*) from CONNGUOI cn join NHANVIEN nv on cn.CMND=nv.CMND where nv.trangThai=1");
            Object[][] obj = null;
            if(rs.next()) {
                obj = new Object[rs.getInt(1)][];
            }
            rs = st.executeQuery("select * from CONNGUOI cn join NHANVIEN nv on cn.CMND=nv.CMND where nv.trangThai=1");
            int count = 0;
            while(rs.next()) {
                obj[count] = new Object[] {
                    count+1+"", rs.getString("maNhanVien")+" - "+rs.getString("hoTen")
                };
                count++;
            }
            connection.closeConnection(con);
            return obj;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static boolean insertBangDanhGia(ReviewDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert BANGDANHGIA values (?,?,?,?,?,?,?,?,?)");
            pst.setString(1, x.getReviewId());
            pst.setString(2, x.getEmployeeId());
            pst.setString(3, x.getReviewerId());
            pst.setDate(4, Date.valueOf(x.getReviewDate()));
            pst.setInt(5, x.getReviewScore());
            pst.setString(6, x.getClassification());
            pst.setString(7, x.getReviewDetails());
            pst.setString(8, x.getReviewType());
            pst.setString(9, x.getNote());
            pst.executeUpdate();
            connection.closeConnection(con);
            return true;
        } catch (SQLException e) {
            System.out.println("Không thể lưu bảng đánh giá");
            return false;
        }
    }
    
    public static boolean deleteBangDanhGia(String reviewId) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("delete BANGDANHGIA where maDanhGia=?");
            pst.setString(1, reviewId);
            pst.executeUpdate();
            connection.closeConnection(con);
            return true;
        } catch (SQLException e) {
            System.out.println("Không thể xóa bảng đánh giá");
            return false;
        }
    }
    
    public static Object[][] getDanhSachDanhGiaTangLuong() {
        Connection con = connection.getConnection();
        try {
            String sql = "select nv.maNhanVien, cn.hoTen, pb.tenPhong, hd.luongCoBan, avg(dg.diemDanhGia) as diem "
                    + "from BANGDANHGIA dg join NHANVIEN nv on dg.maNhanVien = nv.maNhanVien "
                    + "join CONNGUOI cn on cn.CMND = nv.CMND "
                    + "join PHONGBAN pb on nv.maPhong = pb.maPhong "
                    + "join HOPDONGLAODONG hd on nv.maHopDong = hd.maHopDong "
                    + "where YEAR(dg.ngayDanhGia) = YEAR(GETDATE()) "
                    + "group by nv.maNhanVien, cn.hoTen, pb.tenPhong, hd.luongCoBan "
                    + "order by diem desc";
                    
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            int dem = 0;
            while(rs.next()) {
                dem++;
            }
            Object[][] obj = new Object[dem][];
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();
            int count = 0;
            while(rs.next()) {
                String loai = "";
                int diem = rs.getInt("diem");
                if(diem >= 110) {
                    loai = "Xuất sắc";
                } else if(diem >= 90) {
                    loai = "Giỏi";
                } else if(diem >= 70) {
                    loai = "Khá";
                } else if(diem >= 50) {
                    loai = "Trung bình";
                } else {
                    loai = "Yếu";
                }
                obj[count] = new Object[] {
                    count + 1 + "",
                    rs.getString("maNhanVien") + " - " + rs.getString("hoTen"),
                    rs.getString("tenPhong"),
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("luongCoBan")),
                    diem + "",
                    loai	
                };
                count++;
            }
            connection.closeConnection(con);
            return obj;
        } catch (SQLException e) {
            System.out.println("Không thể lấy danh sách đánh giá tăng lương");
            e.printStackTrace();
            return null;
        }
    }
}