package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.SalaryDTO;
import DTO.TimeSheet;
import DTO.SUPPORT;
import Database.connection;

public class SalaryDAO {

    public static ArrayList<SalaryDTO> getList() {
        Connection con = connection.getConnection();
        ArrayList<SalaryDTO> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String sql = "select * from LUONG";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                SalaryDTO s = new SalaryDTO(
                    rs.getString("maLuong"),
                    rs.getString("maBangChamCong"),
                    rs.getDouble("luongThucTe"),
                    rs.getDouble("luongThuong"),
                    rs.getDouble("phuCapChucVu"),
                    rs.getDouble("phuCapKhac"),
                    rs.getDouble("khoanTruBaoHiem"),
                    rs.getDouble("khoanTruKhac"),
                    rs.getDouble("thue"),
                    rs.getDouble("thucLanh")
                );
                list.add(s);
            }
            con.close();
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void insertLUONG(SalaryDTO s) {
        Connection con = connection.getConnection();
        try {
            String sql = "insert LUONG values(?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, s.getSalaryId());
            st.setString(2, s.getTimeSheetId());
            st.setDouble(3, s.getPositionAllowance());
            st.setDouble(4, s.getOtherAllowances());
            st.setDouble(5, s.getActualSalary());
            st.setDouble(6, s.getBonus());
            st.setDouble(7, s.getInsuranceDeduction());
            st.setDouble(8, s.getOtherDeductions());
            st.setDouble(9, s.getTax());
            st.setDouble(10, s.getNetSalary());
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteLUONG(String salaryId) {
        Connection con = connection.getConnection();
        try {
            String sql = "delete LUONG where maLuong=?";
            PreparedStatement st = con.prepareStatement(sql);
            st.setString(1, salaryId);
            st.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Object[][] getObjectToRender() {
        Connection con = connection.getConnection();
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String sql = "select * from LUONG l \r\n"
                    + "join BANGCHAMCONG bcc on bcc.maBangChamCong = l.maBangChamCong\r\n"
                    + "join NHANVIEN nv on nv.maNhanVien = bcc.maNhanVien\r\n"
                    + "join CONNGUOI cn on cn.CMND = nv.CMND left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong";
            ResultSet rs = st.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                String baseSalary = SUPPORT.changeSalaryToFormatString(rs.getDouble("luongCoBan"));
                if (rs.getString("maHopDong") == null) {
                    baseSalary = SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThuViec"));
                }
                Object[] obj = new Object[]{
                    (count + 1) + "",
                    rs.getString("maNhanVien") + " - " + rs.getString("hoTen"),
                    rs.getInt("thangChamCong") + "/" + rs.getInt("namChamCong"),
                    baseSalary,
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThucTe")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("phuCapChucVu") + rs.getDouble("phuCapKhac")),
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThuong")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("khoanTruBaoHiem") + rs.getDouble("khoanTruKhac")),
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("thue")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("thucLanh"))
                };
                list.add(obj);
                count++;
            }
            Object[][] data = new Object[count][];
            for (int i = 0; i < count; i++) {
                data[i] = list.get(i);
            }
            con.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[][] getObjectToRender(String tenPhong, String thang, String nam, int sortby, int type_sort, String maNhanVien) {
        Connection con = connection.getConnection();
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            String sql = "select * from LUONG l \r\n"
                    + "join BANGCHAMCONG bcc on bcc.maBangChamCong = l.maBangChamCong\r\n"
                    + "join NHANVIEN nv on nv.maNhanVien = bcc.maNhanVien\r\n"
                    + "join CONNGUOI cn on cn.CMND = nv.CMND left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong\r\n"
                    + "join PHONGBAN p on p.maPhong = nv.maPhong where 1=1 ";
            // filter
            if (!tenPhong.equalsIgnoreCase("Phòng ban")) {
                sql += "and p.tenPhong = N'" + tenPhong + "' ";
            }
            if (!thang.equalsIgnoreCase("Tháng")) {
                sql += "and bcc.thangChamCong = " + thang.split(" ")[1] + " ";
            }
            if (!nam.equalsIgnoreCase("Năm")) {
                sql += "and bcc.namChamCong = " + nam.split(" ")[1] + " ";
            }
            if (maNhanVien != null && !maNhanVien.equals("")) {
                sql += "and nv.maNhanVien = N'" + maNhanVien + "' ";
            }
            // sort
            if (type_sort == 0) {
                if (sortby == 0) {
                    sql += " order by bcc.namChamCong asc, bcc.thangChamCong asc";
                } else {
                    sql += " order by l.thucLanh asc";
                }
            } else {
                if (sortby == 0) {
                    sql += " order by bcc.namChamCong desc, bcc.thangChamCong desc";
                } else {
                    sql += " order by l.thucLanh desc";
                }
            }
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            int count2 = 0;
            while (rs.next()) {
                String baseSalary = SUPPORT.changeSalaryToFormatString(rs.getDouble("luongCoBan"));
                if (rs.getString("maHopDong") == null) {
                    baseSalary = SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThuViec"));
                }
                Object[] obj = new Object[]{
                    (count2 + 1) + "",
                    rs.getString("maNhanVien") + " - " + rs.getString("hoTen"),
                    rs.getInt("thangChamCong") + "/" + rs.getInt("namChamCong"),
                    baseSalary,
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("phuCapChucVu") + rs.getDouble("phuCapKhac")),
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThucTe")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThuong")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("khoanTruBaoHiem") + rs.getDouble("khoanTruKhac")),
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("thue")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("thucLanh"))
                };
                list.add(obj);
                count2++;
            }
            Object[][] data = new Object[count2][];
            for (int i = 0; i < count2; i++) {
                data[i] = list.get(i);
            }
            con.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[][] getObjectToRender(String maNhanVien) {
        Connection con = connection.getConnection();
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            String sql = "select * from LUONG l \r\n"
                    + "join BANGCHAMCONG bcc on bcc.maBangChamCong = l.maBangChamCong\r\n"
                    + "join NHANVIEN nv on nv.maNhanVien = bcc.maNhanVien\r\n"
                    + "join CONNGUOI cn on cn.CMND = nv.CMND left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong\r\n"
                    + "join PHONGBAN p on p.maPhong = nv.maPhong where nv.maNhanVien=N'" + maNhanVien + "'";
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            int count2 = 0;
            while (rs.next()) {
                String baseSalary = SUPPORT.changeSalaryToFormatString(rs.getDouble("luongCoBan"));
                if (rs.getString("maHopDong") == null) {
                    baseSalary = SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThuViec"));
                }
                Object[] obj = new Object[]{
                    (count2 + 1) + "",
                    rs.getString("maNhanVien") + " - " + rs.getString("hoTen"),
                    rs.getInt("thangChamCong") + "/" + rs.getInt("namChamCong"),
                    baseSalary,
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("phuCapChucVu") + rs.getDouble("phuCapKhac")),
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThucTe")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThuong")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("khoanTruBaoHiem") + rs.getDouble("khoanTruKhac")),
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("thue")) + "",
                    SUPPORT.changeSalaryToFormatString(rs.getDouble("thucLanh"))
                };
                list.add(obj);
                count2++;
            }
            Object[][] data = new Object[count2][];
            for (int i = 0; i < count2; i++) {
                data[i] = list.get(i);
            }
            con.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[][] getDanhSachLuongThuongToRender() {
        Connection con = connection.getConnection();
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            String sql = "select * from LUONG l \r\n"
                    + "join BANGCHAMCONG bcc on bcc.maBangChamCong = l.maBangChamCong\r\n"
                    + "join NHANVIEN nv on nv.maNhanVien = bcc.maNhanVien\r\n"
                    + "join CONNGUOI cn on cn.CMND = nv.CMND left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong";
            ResultSet rs = st.executeQuery(sql);
            int count = 0;
            while (rs.next()) {
                double base = rs.getDouble("luongCoBan");
                double bonus = rs.getDouble("luongThuong");
                if (rs.getString("maHopDong") == null) {
                    base = rs.getDouble("luongThuViec");
                }
                double percent = Math.round(bonus / base * 100);
                Object[] obj = new Object[]{
                    (count + 1) + "",
                    rs.getString("maNhanVien") + " - " + rs.getString("hoTen"),
                    rs.getInt("thangChamCong") + "/" + rs.getInt("namChamCong"),
                    SUPPORT.changeSalaryToFormatString(base),
                    percent + "%",
                    SUPPORT.changeSalaryToFormatString(bonus)
                };
                list.add(obj);
                count++;
            }
            Object[][] data = new Object[count][];
            for (int i = 0; i < count; i++) {
                data[i] = list.get(i);
            }
            con.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[][] getDanhSachLuongThuongToRender(String tenPhong, String thang, String nam, int sortby, int type_sort) {
        Connection con = connection.getConnection();
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            String sql = "select * from LUONG l \r\n"
                    + "join BANGCHAMCONG bcc on bcc.maBangChamCong = l.maBangChamCong\r\n"
                    + "join NHANVIEN nv on nv.maNhanVien = bcc.maNhanVien\r\n"
                    + "join CONNGUOI cn on cn.CMND = nv.CMND left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong\r\n"
                    + "join PHONGBAN p on p.maPhong = nv.maPhong where 1=1 ";
            // filter
            if (!tenPhong.equalsIgnoreCase("Phòng ban")) {
                sql += "and p.tenPhong = N'" + tenPhong + "' ";
            }
            if (!thang.equalsIgnoreCase("Tháng")) {
                sql += "and bcc.thangChamCong = " + thang.split(" ")[1] + " ";
            }
            if (!nam.equalsIgnoreCase("Năm")) {
                sql += "and bcc.namChamCong = " + nam.split(" ")[1] + " ";
            }
            // sort
            if (type_sort == 0) {
                if (sortby == 0) {
                    sql += " order by bcc.namChamCong asc, bcc.thangChamCong asc";
                } else if (sortby == 1) {
                    sql += " order by l.luongThuong asc";
                } else if (sortby == 2) {
                    sql += " order by l.phuCapChucVu + l.phuCapKhac asc";
                } else if (sortby == 3) {
                    sql += " order by l.thue asc";
                } else {
                    sql += " order by l.thucLanh asc";
                }
            } else {
                if (sortby == 0) {
                    sql += " order by bcc.namChamCong desc, bcc.thangChamCong desc";
                } else if (sortby == 1) {
                    sql += " order by l.luongThuong desc";
                } else if (sortby == 2) {
                    sql += " order by l.phuCapChucVu + l.phuCapKhac desc";
                } else if (sortby == 3) {
                    sql += " order by l.thue desc";
                } else {
                    sql += " order by l.thucLanh desc";
                }
            }
            PreparedStatement st = con.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            int count2 = 0;
            while (rs.next()) {
                double base = rs.getDouble("luongCoBan");
                double bonus = rs.getDouble("luongThuong");
                if (rs.getString("maHopDong") == null) {
                    base = rs.getDouble("luongThuViec");
                }
                double percent = Math.round(bonus / base * 100);
                Object[] obj = new Object[]{
                    (count2 + 1) + "",
                    rs.getString("maNhanVien") + " - " + rs.getString("hoTen"),
                    rs.getInt("thangChamCong") + "/" + rs.getInt("namChamCong"),
                    SUPPORT.changeSalaryToFormatString(base),
                    percent + "%",
                    SUPPORT.changeSalaryToFormatString(bonus)
                };
                list.add(obj);
                count2++;
            }
            Object[][] data = new Object[count2][];
            for (int i = 0; i < count2; i++) {
                data[i] = list.get(i);
            }
            con.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void tangLuongHangNam(String maHopDong, double mucTang) {
        Connection con = connection.getConnection();
        String sql = "update HOPDONGLAODONG set luongCoBan = luongCoBan*? where maHopDong=?";
        try {
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDouble(1, mucTang);
            pst.setString(2, maHopDong);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void thuongTatCaNhanVien(int nam,int thang, double phanTramThuong) {
		Connection con = connection.getConnection();
		String sql = "select * from NHANVIEN nv\r\n"
				+ "join BANGCHAMCONG bcc on bcc.maNhanVien = nv.maNhanVien \r\n"
				+ "join LUONG l on bcc.maBangChamCong = l.maBangChamCong\r\n"
				+ "left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong \r\n"
				+ "where bcc.namChamCong = ? and bcc.thangChamCong = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, nam);
			pst.setInt(2, thang);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				double luongCoBan = rs.getDouble("luongCoBan");
				if(rs.getString("maHopDong")==null) {
					luongCoBan = rs.getDouble("luongThuViec");
				}
				double luongThuong = Math.round(phanTramThuong*luongCoBan/100);
				TimeSheet bcc = new TimeSheet(rs.getString("maBangChamCong"), rs.getString("maNhanVien"), rs.getInt("thangChamCong"), rs.getInt("thangChamCong"), rs.getInt("soNgayLamViec"), rs.getInt("soNgayNghi"), rs.getInt("soNgayTre"), rs.getInt("soGioLamThem"), rs.getString("chiTiet"), "");
				SalaryDTO l = SUPPORT.chuyenBangChamCongSangLuong(bcc, luongThuong, rs.getDouble("phuCapKhac"), rs.getDouble("khoanTruKhac"));
				updateLUONG(l);
			}
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public static void thuongNhanVienTheoPhongBan(int nam,int thang, double phanTramThuong,String maPhong) {
		Connection con = connection.getConnection();
		String sql = "select * from NHANVIEN nv\r\n"
				+ "join BANGCHAMCONG bcc on bcc.maNhanVien = nv.maNhanVien \r\n"
				+ "join LUONG l on bcc.maBangChamCong = l.maBangChamCong\r\n"
				+ "left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong \r\n"
				+ "where bcc.namChamCong = ? and bcc.thangChamCong = ? and nv.maPhong = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, nam);
			pst.setInt(2, thang);
			pst.setString(3, maPhong);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				double luongCoBan = rs.getDouble("luongCoBan");
				if(rs.getString("maHopDong")==null) {
					luongCoBan = rs.getDouble("luongThuViec");
				}
				double luongThuong = Math.round(phanTramThuong*luongCoBan/100);
				TimeSheet bcc = new TimeSheet(rs.getString("maBangChamCong"), rs.getString("maNhanVien"), rs.getInt("thangChamCong"), rs.getInt("thangChamCong"), rs.getInt("soNgayLamViec"), rs.getInt("soNgayNghi"), rs.getInt("soNgayTre"), rs.getInt("soGioLamThem"), rs.getString("chiTiet"), "");
				SalaryDTO l = SUPPORT.chuyenBangChamCongSangLuong(bcc, luongThuong, rs.getDouble("phuCapKhac"), rs.getDouble("khoanTruKhac"));
				updateLUONG(l);
			}
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public static void thuongNhanVien(int nam,int thang, double phanTramThuong,String maNhanVien) {
		Connection con = connection.getConnection();
		String sql = "select * from NHANVIEN nv\r\n"
				+ "join BANGCHAMCONG bcc on bcc.maNhanVien = nv.maNhanVien \r\n"
				+ "join LUONG l on bcc.maBangChamCong = l.maBangChamCong\r\n"
				+ "left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong \r\n"
				+ "where bcc.namChamCong = ? and bcc.thangChamCong = ? and nv.maNhanVien = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, nam);
			pst.setInt(2, thang);
			pst.setString(3, maNhanVien);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				double luongCoBan = rs.getDouble("luongCoBan");
				if(rs.getString("maHopDong")==null) {
					luongCoBan = rs.getDouble("luongThuViec");
				}
				double luongThuong = Math.round(phanTramThuong*luongCoBan/100);
				TimeSheet bcc = new TimeSheet(rs.getString("maBangChamCong"), rs.getString("maNhanVien"), rs.getInt("thangChamCong"), rs.getInt("thangChamCong"), rs.getInt("soNgayLamViec"), rs.getInt("soNgayNghi"), rs.getInt("soNgayTre"), rs.getInt("soGioLamThem"), rs.getString("chiTiet"), "");
				SalaryDTO l = SUPPORT.chuyenBangChamCongSangLuong(bcc, luongThuong, rs.getDouble("phuCapKhac"), rs.getDouble("khoanTruKhac"));
				updateLUONG(l);
			}
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public static void phuCapNhanVien(int nam,int thang,String maNhanVien,double phuCapKhac) {
		Connection con = connection.getConnection();
		String sql = "select * from NHANVIEN nv\r\n"
				+ "join BANGCHAMCONG bcc on bcc.maNhanVien = nv.maNhanVien \r\n"
				+ "join LUONG l on bcc.maBangChamCong = l.maBangChamCong\r\n"
				+ "where bcc.namChamCong = ? and bcc.thangChamCong = ? and nv.maNhanVien = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, nam);
			pst.setInt(2, thang);
			pst.setString(3, maNhanVien);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				TimeSheet bcc = new TimeSheet(rs.getString("maBangChamCong"), rs.getString("maNhanVien"), rs.getInt("thangChamCong"), rs.getInt("thangChamCong"), rs.getInt("soNgayLamViec"), rs.getInt("soNgayNghi"), rs.getInt("soNgayTre"), rs.getInt("soGioLamThem"), rs.getString("chiTiet"), "");
				SalaryDTO l = SUPPORT.chuyenBangChamCongSangLuong(bcc, rs.getDouble("luongThuong"), phuCapKhac, rs.getDouble("khoanTruKhac"));
				updateLUONG(l);
			}
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public static void khoanTruNhanVien(int nam,int thang,String maNhanVien,double khoanTru) {
		Connection con = connection.getConnection();
		String sql = "select * from NHANVIEN nv\r\n"
				+ "join BANGCHAMCONG bcc on bcc.maNhanVien = nv.maNhanVien \r\n"
				+ "join LUONG l on bcc.maBangChamCong = l.maBangChamCong\r\n"
				+ "where bcc.namChamCong = ? and bcc.thangChamCong = ? and nv.maNhanVien = ?";
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, nam);
			pst.setInt(2, thang);
			pst.setString(3, maNhanVien);
			ResultSet rs = pst.executeQuery();
			while(rs.next()) {
				TimeSheet bcc = new TimeSheet(rs.getString("maBangChamCong"), rs.getString("maNhanVien"), rs.getInt("thangChamCong"), rs.getInt("thangChamCong"), rs.getInt("soNgayLamViec"), rs.getInt("soNgayNghi"), rs.getInt("soNgayTre"), rs.getInt("soGioLamThem"), rs.getString("chiTiet"), "");
				SalaryDTO l = SUPPORT.chuyenBangChamCongSangLuong(bcc, rs.getDouble("luongThuong"), rs.getDouble("phuCapKhac"), khoanTru);
				updateLUONG(l);
			}
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    // Update salary record based on SalaryDTO
    public static void updateLUONG(SalaryDTO s) {
        Connection con = connection.getConnection();
        try {
            String sql = "update LUONG set maBangChamCong = ?, luongThuong = ?, phuCapChucVu = ?, phuCapKhac = ?, khoanTruBaoHiem = ?, khoanTruKhac = ?, thue = ?, thucLanh = ? where maLuong = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, s.getTimeSheetId());
            pst.setDouble(2, s.getBonus());
            pst.setDouble(3, s.getPositionAllowance());
            pst.setDouble(4, s.getOtherAllowances());
            pst.setDouble(5, s.getInsuranceDeduction());
            pst.setDouble(6, s.getOtherDeductions());
            pst.setDouble(7, s.getTax());
            pst.setDouble(8, s.getNetSalary());
            pst.setString(9, s.getSalaryId());
            pst.executeUpdate();
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static Object[][] getDanhSachPhuCapKhoanTruToRender() {
		Connection con = connection.getConnection();
		try {
			
			Statement st = con.createStatement();
			String sql = "select count(maLuong) from LUONG l \r\n"
					+ "join BANGCHAMCONG bcc on bcc.maBangChamCong = l.maBangChamCong\r\n"
					+ "join NHANVIEN nv on nv.maNhanVien = bcc.maNhanVien\r\n"
					+ "join CONNGUOI cn on cn.CMND = nv.CMND ";
			ResultSet rs = st.executeQuery(sql);
			Object[][] data = null;
			while(rs.next()) {
				 data = new Object[rs.getInt(1)][];
			}
			
			sql = "select * from LUONG l \r\n"
					+ "join BANGCHAMCONG bcc on bcc.maBangChamCong = l.maBangChamCong\r\n"
					+ "join NHANVIEN nv on nv.maNhanVien = bcc.maNhanVien\r\n"
					+ "join CONNGUOI cn on cn.CMND = nv.CMND order by bcc.namChamCong desc , bcc.thangChamCong desc";
			rs = st.executeQuery(sql);
			int count = 0;
			while(rs.next()) {
				Object[] obj = new Object[] {
						count+1+"",rs.getString("maNhanVien")+" - "+rs.getString("hoTen"),rs.getInt("thangChamCong")+"/"+rs.getInt("namChamCong"),
						SUPPORT.changeSalaryToFormatString(rs.getDouble("phuCapChucVu")),
						SUPPORT.changeSalaryToFormatString(rs.getDouble("phuCapKhac")),
						SUPPORT.changeSalaryToFormatString(rs.getDouble("khoanTruBaoHiem")),
						SUPPORT.changeSalaryToFormatString(rs.getDouble("khoanTruKhac"))
				};
				data[count] = obj;
				count++;
			}
			
			
			
			con.close();
			return data;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    public static Object[][]  getDanhSachPhuCapKhoanTruToRender(String tenPhong,String thang,String nam,int sortby,int type_sort) {
        //		"2","001 - Le Quan Phat","09/2022","26","0","0","12","25,000,000","0","2,500,000","1,200,000","30,000,000"
        Connection con = connection.getConnection();
        ArrayList<Object[]> list = new ArrayList<>();
        try {
            String sql = "select * from  LUONG l \r\n"
                    + "join BANGCHAMCONG bcc on bcc.maBangChamCong = l.maBangChamCong\r\n"
                    + "join NHANVIEN nv on nv.maNhanVien = bcc.maNhanVien\r\n"
                    + "join CONNGUOI cn on cn.CMND = nv.CMND left join HOPDONGLAODONG hd on hd.maHopDong = nv.maHopDong\r\n"
                    + "join PHONGBAN p on p.maPhong = nv.maPhong where 1=1 ";
            // filter
            if(!tenPhong.equalsIgnoreCase("Phòng ban")) {
                sql+="and p.tenPhong = N'"+tenPhong+"' ";
            }
            if(!thang.equalsIgnoreCase("Tháng")) {
                
                sql+="and bcc.thangChamCong = "+thang.split(" ")[1]+" ";
            }
            if(!nam.equalsIgnoreCase("Năm")) {
                sql+="and bcc.namChamCong =  "+nam.split(" ")[1]+" ";
            }
            // sort
            if(type_sort==0) {
                if(sortby==0) {
                    sql+=" order by bcc.namChamCong asc , bcc.thangChamCong asc";
                }else if(sortby==1) {
                    sql+=" order by l.phuCapChucVu + l.phuCapKhac asc";
                }else {
                    sql+=" order by l.khoanTruBaoHiem + l.khoanTruKhac asc";
                }
            }else {
                if(sortby==0) {
                    sql+=" order by bcc.namChamCong desc , bcc.thangChamCong desc";
                }else if(sortby==1) {
                    sql+=" order by l.phuCapChucVu + l.phuCapKhac desc";
                }else {
                    sql+=" order by l.khoanTruBaoHiem + l.khoanTruKhac desc";
                }
            }
            PreparedStatement st = con.prepareStatement(sql);
            
            ResultSet rs = st.executeQuery();
            int count2 = 0;
            while(rs.next()) {
                Object[] obj = new Object[] {
                        count2+1+"",rs.getString("maNhanVien")+" - "+rs.getString("hoTen"),rs.getInt("thangChamCong")+"/"+rs.getInt("namChamCong"),
                        SUPPORT.changeSalaryToFormatString(rs.getDouble("phuCapChucVu")),
                        SUPPORT.changeSalaryToFormatString(rs.getDouble("phuCapKhac")),
                        SUPPORT.changeSalaryToFormatString(rs.getDouble("khoanTruBaoHiem")),
                        SUPPORT.changeSalaryToFormatString(rs.getDouble("khoanTruKhac"))
                };
                
                list.add(obj);
                count2++;
            }
            Object[][] data = new Object[count2][];
            for(int i=0;i<count2;i++) {
                data[i] = list.get(i);
            }
            
            con.close();
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}