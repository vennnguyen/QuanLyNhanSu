package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import DTO.LaborContractDTO;
import Database.connection;

public class LaborContractDAO {

    // Keep old function name but now using LaborContractDTO
    public static void insertHOPDONGLAODONG(LaborContractDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("insert HOPDONGLAODONG values(?,?,?,?,?)");
            pst.setString(1, x.getContractId());
            pst.setDate(2, Date.valueOf(x.getStartDate()));
            pst.setDate(3, Date.valueOf(x.getEndDate()));
            pst.setString(4, x.getContractType());
            pst.setDouble(5, x.getBaseSalary());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
    }

    public static void updateHOPDONGLAODONG(LaborContractDTO x) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement(
                "update HOPDONGLAODONG set tuNgay = ? , denNgay = ? , loaiHopDong = ? , luongCoBan = ? where maHopDong = ?"
            );
            pst.setDate(1, Date.valueOf(x.getStartDate()));
            pst.setDate(2, Date.valueOf(x.getEndDate()));
            pst.setString(3, x.getContractType());
            pst.setDouble(4, x.getBaseSalary());
            pst.setString(5, x.getContractId());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
    }

    // Retrieves contracts that are about to expire
    public static ArrayList<LaborContractDTO> getHopDongSapHetHan() {
        Connection con = connection.getConnection();
        ArrayList<LaborContractDTO> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "select * from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong " +
                "join CONNGUOI cn on cn.CMND=nv.CMND join PHONGBAN pb on pb.maPhong=nv.maPhong " +
                "where nv.trangThai=1 and DATEDIFF(DAY, GETDATE(), denNgay) < 180 and DATEDIFF(DAY, GETDATE(), denNgay) > 0"
            );
            while (rs.next()) {
                LaborContractDTO x = new LaborContractDTO(
                    rs.getString("maHopDong"),                     // contractId
                    rs.getString("maNhanVien"),                    // employeeId
                    rs.getString("hoTen"),                         // employeeName
                    rs.getString("tenPhong"),                      // department
                    rs.getDate("tuNgay").toLocalDate().plusDays(2),  // startDate
                    rs.getDate("denNgay").toLocalDate().plusDays(2), // endDate
                    rs.getString("loaiHopDong"),                   // contractType
                    rs.getDouble("luongCoBan")                     // baseSalary
                );
                list.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return list;
    }

    // Retrieves expired contracts
    public static ArrayList<LaborContractDTO> getHopDongDaHetHan() {
        Connection con = connection.getConnection();
        ArrayList<LaborContractDTO> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "select * from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong " +
                "join CONNGUOI cn on cn.CMND=nv.CMND join PHONGBAN pb on pb.maPhong=nv.maPhong " +
                "where nv.trangThai=1 and DATEDIFF(DAY, GETDATE(), denNgay) <= 0"
            );
            while (rs.next()) {
                LaborContractDTO x = new LaborContractDTO(
                    rs.getString("maHopDong"),
                    rs.getString("maNhanVien"),
                    rs.getString("hoTen"),
                    rs.getString("tenPhong"),
                    rs.getDate("tuNgay").toLocalDate().plusDays(2),
                    rs.getDate("denNgay").toLocalDate().plusDays(2),
                    rs.getString("loaiHopDong"),
                    rs.getDouble("luongCoBan")
                );
                list.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return list;
    }

    // Retrieves all contracts
    public static ArrayList<LaborContractDTO> getList() {
        Connection con = connection.getConnection();
        ArrayList<LaborContractDTO> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "select * from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong = nv.maHopDong " +
                "join CONNGUOI cn on nv.CMND = cn.CMND join PHONGBAN pb on nv.maPhong = pb.maPhong " +
                "where nv.trangThai=1"
            );
            while (rs.next()) {
                LaborContractDTO x = new LaborContractDTO(
                    rs.getString("maHopDong"),
                    rs.getString("maNhanVien"),
                    rs.getString("hoTen"),
                    rs.getString("tenPhong"),
                    rs.getDate("tuNgay").toLocalDate().plusDays(2),
                    rs.getDate("denNgay").toLocalDate().plusDays(2),
                    rs.getString("loaiHopDong"),
                    rs.getDouble("luongCoBan")
                );
                list.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return list;
    }

    // Retrieves contracts by department name
    public static ArrayList<LaborContractDTO> getHopDongTheoTenPhong(String tenPhong) {
        Connection con = connection.getConnection();
        ArrayList<LaborContractDTO> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "select * from HOPDONGLAODONG HD join NHANVIEN NV on HD.maHopDong = NV.maHopDong " +
                "join PHONGBAN PB on NV.maPhong = PB.maPhong where NV.trangThai=1 and PB.tenPhong = N'" + tenPhong + "'"
            );
            while (rs.next()) {
                LaborContractDTO x = new LaborContractDTO(
                    rs.getString("maHopDong"),
                    rs.getString("maNhanVien"),
                    rs.getString("hoTen"),
                    rs.getString("tenPhong"),
                    rs.getDate("tuNgay").toLocalDate().plusDays(2),
                    rs.getDate("denNgay").toLocalDate().plusDays(2),
                    rs.getString("loaiHopDong"),
                    rs.getDouble("luongCoBan")
                );
                list.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return list;
    }

    // Retrieves recently issued contracts
    public static ArrayList<LaborContractDTO> getHopDongMoiKy() {
        Connection con = connection.getConnection();
        ArrayList<LaborContractDTO> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(
                "select * from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong " +
                "join CONNGUOI cn on cn.CMND= nv.CMND join PHONGBAN pb on pb.maPhong=nv.maPhong " +
                "where nv.trangThai=1 and DATEDIFF(DAY, tuNgay, GETDATE()) < 186"
            );
            while (rs.next()) {
                LaborContractDTO x = new LaborContractDTO(
                    rs.getString("maHopDong"),
                    rs.getString("maNhanVien"),
                    rs.getString("hoTen"),
                    rs.getString("tenPhong"),
                    rs.getDate("tuNgay").toLocalDate().plusDays(2),
                    rs.getDate("denNgay").toLocalDate().plusDays(2),
                    rs.getString("loaiHopDong"),
                    rs.getDouble("luongCoBan")
                );
                list.add(x);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return list;
    }

    public static int getSoLuongHopDong() {
        Connection con = connection.getConnection();
        int sum = 0;
        try {
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery(
                "select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong where nv.trangThai=1"
            );
            if (rs.next()) {
                sum = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return sum;
    }

    public static int getSoLuongHopDongMoiKy() {
        Connection con = connection.getConnection();
        int sum = 0;
        try {
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery(
                "select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong " +
                "where nv.trangThai=1 and DATEDIFF(DAY, tuNgay, GETDATE()) < 180"
            );
            if (rs.next()) {
                sum = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return sum;
    }

    public static void updateHopDong(LocalDate denNgay, String loaiHopDong, String maHopDong) {
        Connection con = connection.getConnection();
        try {
            PreparedStatement pst = con.prepareStatement("update HOPDONGLAODONG set denNgay = ?, loaiHopDong = ? where maHopDong = ?");
            pst.setDate(1, Date.valueOf(denNgay));
            pst.setString(2, loaiHopDong);
            pst.setString(3, maHopDong);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
    }

    public static String getLoaiHopDong(String maHopDong) {
        Connection con = connection.getConnection();
        String loaiHopDong = "";
        try {
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery("select * from HOPDONGLAODONG where maHopDong ='" + maHopDong + "'");
            if (rs.next()) {
                loaiHopDong = rs.getString(4);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return loaiHopDong;
    }

    public static int getSoLuongHopDongSapHetHan() {
        Connection con = connection.getConnection();
        int sum = 0;
        try {
            Statement ps = con.createStatement();
            ResultSet rs = ps.executeQuery(
                "select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong " +
                "where nv.trangThai=1 and DATEDIFF(DAY, GETDATE(), denNgay) < 180 and DATEDIFF(DAY, GETDATE(), denNgay) > 0"
            );
            if (rs.next()) {
                sum = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            connection.closeConnection(con);
        }
        return sum;
    }

    public static int getSoLuongHopDongDaHetHan(){
		Connection con = connection.getConnection();
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong where nv.trangThai=1 and DATEDIFF(DAY, GETDATE(), denNgay) <= 0");
			int sum = 0;
			while (rs.next()) {
				sum = rs.getInt(1);
			}
			connection.closeConnection(con);
			return sum;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static int[] getThongKeTiLeLoaiHopDong() {
		Connection con = connection.getConnection();
		int []data = new int[5];
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong where nv.trangThai=1 and DATEDIFF(YEAR, tuNgay, denNgay) <=2");
			while (rs.next()) {
				data[0] = rs.getInt(1);
			}
			rs = ps.executeQuery("select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong where nv.trangThai=1 and DATEDIFF(YEAR, tuNgay, denNgay) >2 and DATEDIFF(YEAR, tuNgay, denNgay) <=5");
			while (rs.next()) {
				data[1] = rs.getInt(1);
			}
			rs = ps.executeQuery("select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong where nv.trangThai=1 and DATEDIFF(YEAR, tuNgay, denNgay) >5 and DATEDIFF(YEAR, tuNgay, denNgay) <=8");
			while (rs.next()) {
				data[2] = rs.getInt(1);
			}
			rs = ps.executeQuery("select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong where nv.trangThai=1 and DATEDIFF(YEAR, tuNgay, denNgay) >8 and DATEDIFF(YEAR, tuNgay, denNgay) <=10");
			while (rs.next()) {
				data[3] = rs.getInt(1);
			}
			rs = ps.executeQuery("select COUNT(*) from HOPDONGLAODONG hd join NHANVIEN nv on hd.maHopDong=nv.maHopDong where nv.trangThai=1 and DATEDIFF(YEAR, tuNgay, denNgay) >10");
			while (rs.next()) {
				data[4] = rs.getInt(1);
			}
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
    public static int[] getSoLuongHopDongHetHanVaKiTrongNam(int nam) {
		Connection con = connection.getConnection();
		int []data = new int[2];
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select COUNT(*) from HOPDONGLAODONG where tuNgay like '%"+nam+"%'");
			while (rs.next()) {
				data[0] = rs.getInt(1);
			}
			rs = ps.executeQuery("select COUNT(*) from HOPDONGLAODONG where denNgay like '%"+nam+"%'");
			while (rs.next()) {
				data[1] = rs.getInt(1);
			}
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
	
}