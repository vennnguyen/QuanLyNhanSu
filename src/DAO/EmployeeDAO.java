package DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import DTO.AddressDTO;
import DTO.EmployeeDTO;
import DTO.PermanentEmployeeDTO;
import DTO.ProbationEmployeeDTO;
import DTO.IdentityCardDTO;
import DTO.LaborContractDTO;
import DTO.PersonDTO;
import DTO.QualificationDTO;
import DTO.SUPPORT;
import DTO.PositionDTO;
import DTO.AccountDTO;
import Database.connection;

public class EmployeeDAO {

    public static ArrayList<EmployeeDTO> getList() {
        Connection con = connection.getConnection();
        ArrayList<EmployeeDTO> list = new ArrayList<>();
        try {
            Statement st = con.createStatement();
            // Note: ensure that the column names for address, id card, etc. match your DB schema
            String sql = "select cn.hoTen, cn.gioiTinh, cn.ngaySinh, cn.diaChi, cn.SDT, cn.email, " +
                         "cn.danToc, cn.tinhTrangHonNhan, cn.tonGiao, " +
                         "cmnd.soCMND, cmnd.noiCap, cmnd.ngayCap, " +
                         "nv.maNhanVien, nv.maPhong, " +
                         "td.maTrinhDo, td.trinhDoHocVan, td.trinhDoChuyenMon, td.chuyenNganh, " +
                         "cv.maChucVu, cv.tenChucVu, cv.phuCapChucVu, cv.ngayNhanChuc, " +
                         "tk.username, tk.pass, tk.maNhomQuyen, tk.avatar " +
                         "from CONNGUOI cn " +
                         "join NHANVIEN nv on cn.CMND = nv.CMND " +
                         "join CMND on CMND.soCMND = cn.CMND " +
                         "join TRINHDO td on nv.maTrinhDo = td.maTrinhDo " +
                         "join CHUCVU cv on nv.maChucVu = cv.maChucVu " +
                         "join TAIKHOAN tk on tk.username = nv.maNhanVien " +
                         "where nv.trangThai = 1";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                // Parse address (assumed format: "houseNumber street, ward, district, city")
                String diaChi = rs.getString("diaChi");
                String[] parts = diaChi.split(",");
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
                AddressDTO address = new AddressDTO(houseNumber, street, ward, district, city);

                // Build identity card
                IdentityCardDTO idCard = new IdentityCardDTO(
                    rs.getString("soCMND"),
                    rs.getString("noiCap"),
                    rs.getDate("ngayCap").toLocalDate().plusDays(2)
                );

                // Build qualification
                QualificationDTO qualification = new QualificationDTO(
                    rs.getString("maTrinhDo"),
                    rs.getString("trinhDoHocVan"),
                    rs.getString("trinhDoChuyenMon"),
                    rs.getString("chuyenNganh")
                );

                // Build position
                PositionDTO position = new PositionDTO(
                    rs.getString("maChucVu"),
                    rs.getString("tenChucVu"),
                    rs.getDouble("phuCapChucVu"),
                    rs.getDate("ngayNhanChuc").toLocalDate().plusDays(2)
                );

                // Build account
                AccountDTO account = new AccountDTO(
                    rs.getString("username"),
                    rs.getString("pass"),
                    rs.getString("maNhomQuyen"),
                    rs.getString("avatar")
                );

                // Build EmployeeDTO using full constructor
                EmployeeDTO emp = new EmployeeDTO(
                    rs.getString("maNhanVien"),                  // employeeId
                    rs.getString("hoTen"),                         // fullName
                    rs.getString("gioiTinh"),                      // gender
                    rs.getDate("ngaySinh").toLocalDate().plusDays(2),// dob
                    address,                                       // address
                    rs.getString("SDT"),                           // phone
                    idCard,                                        // identity card
                    rs.getString("danToc"),                        // ethnicity
                    rs.getString("tinhTrangHonNhan"),              // marital status
                    rs.getString("tonGiao"),                       // religion
                    rs.getString("email"),                         // email
                    rs.getString("maPhong"),                       // departmentId
                    qualification,                                 // qualification
                    position,                                      // position
                    account                                        // account
                );
                list.add(emp);
            }
            connection.closeConnection(con);
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static EmployeeDTO getNhanVien(String employeeId) {
        Connection con = connection.getConnection();
        try {
            Statement st = con.createStatement();
            String sql = "select cn.hoTen, cn.gioiTinh, cn.ngaySinh, cn.diaChi, cn.SDT, cn.email, " +
                         "cn.danToc, cn.tinhTrangHonNhan, cn.tonGiao, " +
                         "cmnd.soCMND, cmnd.noiCap, cmnd.ngayCap, " +
                         "nv.maNhanVien, nv.maPhong, " +
                         "td.maTrinhDo, td.trinhDoHocVan, td.trinhDoChuyenMon, td.chuyenNganh, " +
                         "cv.maChucVu, cv.tenChucVu, cv.phuCapChucVu, cv.ngayNhanChuc, " +
                         "tk.username, tk.pass, tk.maNhomQuyen, tk.avatar " +
                         "from CONNGUOI cn " +
                         "join NHANVIEN nv on cn.CMND = nv.CMND " +
                         "join CMND on CMND.soCMND = cn.CMND " +
                         "join TRINHDO td on nv.maTrinhDo = td.maTrinhDo " +
                         "join CHUCVU cv on nv.maChucVu = cv.maChucVu " +
                         "join TAIKHOAN tk on tk.username = nv.maNhanVien " +
                         "where nv.maNhanVien = N'" + employeeId + "'";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String diaChi = rs.getString("diaChi");
                String[] parts = diaChi.split(",");
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
                AddressDTO address = new AddressDTO(houseNumber, street, ward, district, city);

                IdentityCardDTO idCard = new IdentityCardDTO(
                    rs.getString("soCMND"),
                    rs.getString("noiCap"),
                    rs.getDate("ngayCap").toLocalDate().plusDays(2)
                );

                QualificationDTO qualification = new QualificationDTO(
                    rs.getString("maTrinhDo"),
                    rs.getString("trinhDoHocVan"),
                    rs.getString("trinhDoChuyenMon"),
                    rs.getString("chuyenNganh")
                );

                PositionDTO position = new PositionDTO(
                    rs.getString("maChucVu"),
                    rs.getString("tenChucVu"),
                    rs.getDouble("phuCapChucVu"),
                    rs.getDate("ngayNhanChuc").toLocalDate().plusDays(2)
                );

                AccountDTO account = new AccountDTO(
                    rs.getString("username"),
                    rs.getString("pass"),
                    rs.getString("maNhomQuyen"),
                    rs.getString("avatar")
                );

                EmployeeDTO emp = new EmployeeDTO(
                    rs.getString("maNhanVien"),
                    rs.getString("hoTen"),
                    rs.getString("gioiTinh"),
                    rs.getDate("ngaySinh").toLocalDate().plusDays(2),
                    address,
                    rs.getString("SDT"),
                    idCard,
                    rs.getString("danToc"),
                    rs.getString("tinhTrangHonNhan"),
                    rs.getString("tonGiao"),
                    rs.getString("email"),
                    rs.getString("maPhong"),
                    qualification,
                    position,
                    account
                );
                connection.closeConnection(con);
                return emp;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ArrayList<String> getDanhSachMaSo() {
		Connection con = connection.getConnection();
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select maNhanVien from NHANVIEN");
			ArrayList<String> list = new ArrayList<>();
			while(rs.next()) {
				list.add(rs.getString("maNhanVien"));
			}
			connection.closeConnection(con);
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    public static String getTenNhanVien(String maSo) {
		Connection con = connection.getConnection();
		try {
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery("select hoTen from CONNGUOI join NHANVIEN on NHANVIEN.CMND = CONNGUOI.CMND where maNhanVien = N'"+maSo+"'");
			while(rs.next()) {
				return rs.getString("hoTen");
			}
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
    public static void thayDoiMaPhongBanAll(String oldId,String newId) {
		Connection con = connection.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("update NHANVIEN set maPhong = ? where maPhong = ?");
			pst.setString(1, newId);
			pst.setString(2, oldId);
			pst.executeUpdate();
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public static void thayDoiMaPhongBan(String emplopyeeId, String departmentId) {
		Connection con = connection.getConnection();
		try {
			PreparedStatement pst = con.prepareStatement("update NHANVIEN set maPhong = ? where maNhanVien = ?");
			pst.setString(1, departmentId);
			pst.setString(2, emplopyeeId);
			pst.executeUpdate();
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public static void insertNHANVIEN(EmployeeDTO x) {
		Connection con = connection.getConnection();
		try {
			// tạo cmnd
			IdentityCardDAO.insertCMND(x.getIdentityCard());
			// con người
			PersonDAO.insertCONNGUOI((PersonDTO)x);
			// trinh do
			QualificationDAO.insertTRINHDO(x.getQualification());
			// chuc vu
			PositionDAO.insertChucVu(x.getPosition());
			// nhanvien
			PreparedStatement pst;
			if(x instanceof PermanentEmployeeDTO) {
				LaborContractDAO.insertHOPDONGLAODONG(((PermanentEmployeeDTO)x).getContract());
				// nhan vien
				pst = con.prepareStatement("insert NHANVIEN values(?,?,?,?,?,?,?,?,?,?)");
				pst.setString(1, x.getEmployeeId());
				pst.setString(2, x.getIdentityCard().getId());
				pst.setString(3, x.getDepartmentId());
				pst.setString(4, x.getQualification().getEducationLevelId());
				pst.setString(5, x.getPosition().getPositionId());
				pst.setString(6, ((PermanentEmployeeDTO)x).getContract().getContractId());
				pst.setDate(7,null);
				pst.setDate(8,null);
				pst.setDouble(9,0);
				pst.setInt(10, 1);
				pst.executeUpdate();
			}else {
				// nhan vien
                pst = con.prepareStatement("insert NHANVIEN values(?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, x.getEmployeeId());
                pst.setString(2, x.getIdentityCard().getId());
                pst.setString(3, x.getDepartmentId());
                pst.setString(4, x.getQualification().getEducationLevelId());
                pst.setString(5, x.getPosition().getPositionId());
                pst.setString(6, null);
                pst.setDate(7,Date.valueOf(((ProbationEmployeeDTO)x).getStartProbationDate()));
                pst.setDate(8,Date.valueOf(((ProbationEmployeeDTO)x).getEndProbationDate()));
                pst.setDouble(9,((ProbationEmployeeDTO)x).getProbationSalary());
                pst.setInt(10, 1);
                pst.executeUpdate();
            }
            // tài khoản
            AccountDAO.insertTAIKHOAN(x.getAccount());
			
			connection.closeConnection(con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
    public static void insertNHANVIENfromUNGVIEN(EmployeeDTO x) {
        Connection con = connection.getConnection();
        try {
            // trinh do
            QualificationDAO.insertTRINHDO(x.getQualification());
            // chuc vu
            PositionDAO.insertChucVu(x.getPosition());
            // nhanvien
            PreparedStatement pst;
            if(x instanceof PermanentEmployeeDTO) {
                LaborContractDAO.insertHOPDONGLAODONG(((PermanentEmployeeDTO)x).getContract());
                // nhan vien
                
                pst = con.prepareStatement("insert NHANVIEN values(?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, x.getEmployeeId());
                pst.setString(2, x.getIdentityCard().getId());
                pst.setString(3, x.getDepartmentId());
                pst.setString(4, x.getQualification().getEducationLevelId());
                pst.setString(5, x.getPosition().getPositionId());
                pst.setString(6, ((PermanentEmployeeDTO)x).getContract().getContractId());
                pst.setDate(7,null);
                pst.setDate(8,null);
                pst.setDouble(9,0);
                pst.setInt(10, 1);
                pst.executeUpdate();
            }else {
                // nhan vien
                pst = con.prepareStatement("insert NHANVIEN values(?,?,?,?,?,?,?,?,?,?)");
                pst.setString(1, x.getEmployeeId());
                pst.setString(2, x.getIdentityCard().getId());
                pst.setString(3, x.getDepartmentId());
                pst.setString(4, x.getQualification().getEducationLevelId());
                pst.setString(5, x.getPosition().getPositionId());
                pst.setString(6, null);
                pst.setDate(7,Date.valueOf(((ProbationEmployeeDTO)x).getStartProbationDate()));
                pst.setDate(8,Date.valueOf(((ProbationEmployeeDTO)x).getEndProbationDate()));
                pst.setDouble(9,((ProbationEmployeeDTO)x).getProbationSalary());
                pst.setInt(10, 1);
                pst.executeUpdate();
            }
            // tài khoản
            AccountDAO.insertTAIKHOAN(x.getAccount());
            
            connection.closeConnection(con);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public static void updateNHANVIEN(EmployeeDTO x) {
            Connection con = connection.getConnection();
            try {
                // tạo cmnd
                IdentityCardDAO.updateCMND(x.getIdentityCard());
                // con người
                PersonDAO.updateCONNGUOI((PersonDTO)x);
                // trinh do
                QualificationDAO.updateTRINHDO(x.getQualification());
                // chuc vu
                PositionDAO.updateChucVu(x.getPosition());
                // nhanvien
                PreparedStatement pst;
                if(x instanceof PermanentEmployeeDTO) {
                    LaborContractDAO.updateHOPDONGLAODONG(((PermanentEmployeeDTO)x).getContract());
                    // nhan vien
                    
                    pst = con.prepareStatement("update NHANVIEN set CMND = ?, maPhong = ?, maTrinhDo = ? , maChucVu = ? , maHopDong = ? where maNhanVien = ? ");
                    
                    pst.setString(1, x.getIdentityCard().getId());
                    pst.setString(2, x.getDepartmentId());
                    pst.setString(3, x.getQualification().getEducationLevelId());
                    pst.setString(4, x.getPosition().getPositionId());
                    pst.setString(5, ((PermanentEmployeeDTO)x).getContract().getContractId());
                    pst.setString(6, x.getEmployeeId());
                    pst.executeUpdate();
                } else {
                    // nhan vien
                    pst = con.prepareStatement("update NHANVIEN set ngayBatDauThuViec = ? , ngayKetThucThuViec = ? , luongThuViec = ? where maNhanVien = ? ");
                    pst.setDate(1, Date.valueOf(((ProbationEmployeeDTO)x).getStartProbationDate()));
                    pst.setDate(2, Date.valueOf(((ProbationEmployeeDTO)x).getEndProbationDate()));
                    pst.setDouble(3, ((ProbationEmployeeDTO)x).getProbationSalary());
                    pst.setString(4, x.getEmployeeId());
                    pst.executeUpdate();
                }
                // tài khoản
                AccountDAO.updateTAIKHOAN(x.getAccount());
                connection.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void deleteNHANVIEN(EmployeeDTO x) {
            Connection con = connection.getConnection();
            try {
                // xóa tài khoảno
                PreparedStatement pst = con.prepareStatement("delete TAIKHOAN where username = ? ");
                pst.setString(1, x.getAccount().getUsername()); 
                pst.executeUpdate();
                // xóa nhân viên
                pst = con.prepareStatement("delete NHANVIEN where maNhanVien = ? ");
                pst.setString(1, x.getEmployeeId());
                pst.executeUpdate();
                // xóa con người
                pst = con.prepareStatement("delete CONNGUOI where CMND = ? ");
                pst.setString(1, x.getIdentityCard().getId());
                pst.executeUpdate();
                // xóa CMND
                pst = con.prepareStatement("delete CMND where soCMND = ? ");
                pst.setString(1, x.getIdentityCard().getId());
                pst.executeUpdate();
                // xóa trình độ
                pst = con.prepareStatement("delete TRINHDO where maTrinhDo = ? ");
                pst.setString(1, x.getQualification().getEducationLevelId());
                pst.executeUpdate();
                // xóa chức vụ
                pst = con.prepareStatement("delete CHUCVU where maChucVu = ? ");
                pst.setString(1, x.getPosition().getPositionId());
                pst.executeUpdate();
                if(x instanceof PermanentEmployeeDTO) {
                    pst = con.prepareStatement("delete HOPDONGLAODONG where maHopDong = ? ");
                    pst.setString(1, ((PermanentEmployeeDTO)x).getContract().getContractId());
                    pst.executeUpdate();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connection.closeConnection(con);
        }
        public static ArrayList<EmployeeDTO>  getListFilterAndSort(String tenPhong,String gioitinh,String doTuoi,String loaiHinh,String mucLuong) {
    //		"2","001 - Le Quan Phat","09/2022","26","0","0","12","25,000,000","0","2,500,000","1,200,000","30,000,000"
            Connection con = connection.getConnection();
            ArrayList<EmployeeDTO> list = new ArrayList<>();
            try {
                String sql = "select * from CONNGUOI join NHANVIEN on CONNGUOI.CMND = NHANVIEN.CMND join CMND on CMND.soCMND = CONNGUOI.CMND join TRINHDO on NHANVIEN.maTrinhDo = TRINHDO.maTrinhDo join CHUCVU on NHANVIEN.maChucVu = CHUCVU.maChucVu join TAIKHOAN on TAIKHOAN.username = NHANVIEN.maNhanVien left join HOPDONGLAODONG on HOPDONGLAODONG.maHopDong = NHANVIEN.maHopDong join PHONGBAN on NHANVIEN.maPhong = PHONGBAN.maPhong where NHANVIEN.trangThai=1";
                // filter
                if(!tenPhong.equalsIgnoreCase("Phòng ban")) {
                    sql+="and tenPhong = N'"+tenPhong+"' ";
                }
                if(!gioitinh.equalsIgnoreCase("Giới tính")) {
                    sql+="and gioiTinh = N'"+gioitinh+"' ";
                }if(!doTuoi.equalsIgnoreCase("Độ tuổi")) {
                    String arrdoTuoi[] = doTuoi.split("-");
                    sql+="and year(getdate()) - year(ngaySinh) >= "+arrdoTuoi[0] +" and year(getdate()) - year(ngaySinh) <= "+arrdoTuoi[1]+" ";
                }
                if(!loaiHinh.equalsIgnoreCase("Loại hình")) {
                    if(loaiHinh.equalsIgnoreCase("Chính thức") || loaiHinh.equalsIgnoreCase("Nhân viên chính thức")) {
                        sql+="and NHANVIEN.maHopDong is not null ";
                    }else {
                        sql+="and NHANVIEN.maHopDong is null ";
                    }
                }
                if(!mucLuong.equalsIgnoreCase("Mức lương")) {
                    String arr[] = mucLuong.split(" - ");
                    String min = arr[0].trim().substring(0,2);
                    String max = arr[1].trim().substring(0,2);
                    
                    int i_min = Integer.valueOf(min)*1000000;
                    int i_max = Integer.valueOf(max)*1000000;
                    sql+=" and (( NHANVIEN.luongThuViec >= "+i_min+" and NHANVIEN.luongThuViec <= "+i_max+" ) or ( HOPDONGLAODONG.luongCoBan >= "+i_min+" and HOPDONGLAODONG.luongCoBan <= "+i_max+" )) ";
                    
                }
                PreparedStatement st = con.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                while(rs.next()) {
                    EmployeeDTO x = null;
                    String str = rs.getString("diaChi");
                    String arr[] = str.split(",");
                    String arr2[] = arr[0].split(" ");
                    String temp ="";
                    for(int i=1;i<arr2.length;i++) {
                        temp+=arr2[i]+" ";
                    }
                    temp = temp.trim();
                    if(rs.getString("maHopDong")==null) {
                        x = new ProbationEmployeeDTO();
                        ((ProbationEmployeeDTO)x).setStartProbationDate(rs.getDate("ngayBatDauThuViec").toLocalDate());
                        ((ProbationEmployeeDTO)x).setEndProbationDate(rs.getDate("ngayKetThucThuViec").toLocalDate());
                        ((ProbationEmployeeDTO)x).setProbationSalary(rs.getDouble("luongThuViec"));
                    } else {
                        x = new PermanentEmployeeDTO();
                        ((PermanentEmployeeDTO)x).setContract(new LaborContractDTO(rs.getString("maHopDong"), rs.getDate("tuNgay").toLocalDate(), rs.getDate("denNgay").toLocalDate(), rs.getString("loaiHopDong"), rs.getDouble("luongCoBan")));
                    }
                    x.setEmployeeId(rs.getString("maNhanVien"));
                    x.setName(rs.getString("hoTen"));
                    x.setGender(rs.getString("gioiTinh"));
                    x.setDateOfBirth(rs.getDate("ngaySinh").toLocalDate().plusDays(2));
                    x.setAddress(new AddressDTO(arr2[0],temp, arr[1], arr[2], arr[3]));
                    x.setMaritalStatus(rs.getString("tinhTrangHonNhan"));
                    x.setEthnicGroup(rs.getString("danToc"));
                    x.setReligion(rs.getString("tonGiao"));
                    x.setEmail(rs.getString("email"));
                    x.setPhoneNumber(rs.getString("SDT"));
                    x.setIdentityCard(new IdentityCardDTO(rs.getString("soCMND"),rs.getString("noiCap"),rs.getDate("ngayCap").toLocalDate().plusDays(2)));
                    
                    
                    x.setAccount(new AccountDTO(rs.getString("username"), rs.getString("pass"), rs.getString("maNhomQuyen"), rs.getString("avatar")));
                    x.setPosition(new PositionDTO(rs.getString("maChucVu"), rs.getString("tenChucVu"), rs.getDouble("phuCapChucVu"), rs.getDate("ngayNhanChuc").toLocalDate().plusDays(2)));
                    x.setDepartmentId(rs.getString("maPhong"));
                    x.setQualification(new QualificationDTO(rs.getString("maTrinhDo"), rs.getString("trinhDoHocVan"), rs.getString("trinhDoChuyenMon"), rs.getString("chuyenNganh")));
                    list.add(x);
                }
                con.close();
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        public static void dieuChinhLuong(String maNhanVien,double mucLuong) {
            String sql = " select * from NHANVIEN where maNhanVien = N'"+maNhanVien+"'";
            Connection con = connection.getConnection();
             try {
                 Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()) {
                    Statement st1 =con.createStatement();
                    if(rs.getString("maHopDong")==null) {
                        st1.executeUpdate("update NHANVIEN set luongThuViec = "+mucLuong+" where maNhanVien = N'"+maNhanVien+"'");
                    }else {
                        st1.executeUpdate("update HOPDONGLAODONG set luongCoBan = "+mucLuong+" where maHopDong = N'"+rs.getString("maHopDong")+"'");
                    }
                }
                connection.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static Object[][] getDanhSachNhanVienThuViecToRender() {
            Connection con = connection.getConnection();
            Object[][] obj =null;
            try {
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select count(maNhanVien) from CONNGUOI cn join NHANVIEN nv on cn.CMND = nv.CMND\r\n"
                        + "join PHONGBAN pb on nv.maPhong = pb.maPhong where nv.maHopDong is null and nv.trangThai=1");
                while(rs.next()) {
                    obj = new Object[rs.getInt(1)][];
                }
                rs = st.executeQuery("select * from CONNGUOI cn join NHANVIEN nv on cn.CMND = nv.CMND\r\n"
                        + "join PHONGBAN pb on nv.maPhong = pb.maPhong where nv.maHopDong is null and nv.trangThai=1");
                int count = 0;
                while(rs.next()) {
                    obj[count] = new Object[] {
                            count+1+"", rs.getString("maNhanVien")+" - "+rs.getString("hoTen"), rs.getString("tenPhong"),SUPPORT.LocalDateToString(rs.getDate("ngayBatDauThuViec").toLocalDate())
                    };
                    count++;
                }
                connection.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return obj;
        }
        public static Object[][] getDanhSachNhanVienThuViecToRender(String tenPhong, int sort_by, int sort_mode) {
            Connection con = connection.getConnection();
             Object[][] obj =null;
             try {
                 String sql = "from CONNGUOI cn join NHANVIEN nv on cn.CMND = nv.CMND"
                                     + " join PHONGBAN pb on nv.maPhong = pb.maPhong where nv.maHopDong is null and nv.trangThai=1";
                 if(!tenPhong.equals("Phòng ban")) {
                     sql += " and pb.tenPhong = N'"+tenPhong+"' ";
                 }
                 
                 Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select count(maNhanVien) "+sql);
                while(rs.next()) {
                    obj = new Object[rs.getInt(1)][];
                }
                if(sort_by==0) {
                     sql+= " order by nv.maNhanVien ";
                     if(sort_mode==0) {
                         sql+= "asc";
                     }else {
                         sql+= "desc";
                     }
                 }else {
                     sql+= " order by nv.ngayBatDauThuViec ";
                     if(sort_mode==0) {
                         sql+= " asc";
                     }else {
                         sql+= " desc";
                     }
                 }
                rs = st.executeQuery("select * "+sql);
                int count = 0;
                while(rs.next()) {
                    obj[count] = new Object[] {
                            count+1+"", rs.getString("maNhanVien")+" - "+rs.getString("hoTen"), rs.getString("tenPhong"),SUPPORT.LocalDateToString(rs.getDate("ngayBatDauThuViec").toLocalDate())
                    };
                    count++;
                }
                connection.closeConnection(con);
            } catch (SQLException e) {
                e.printStackTrace();
            }
             return obj;
        }
        public static String[] getThongTinNhanVienKiHopDong(String maNhanVien) {
            Connection con = connection.getConnection();
            String []data = null;
            try {
                String sql = "select * from CONNGUOI cn join NHANVIEN nv on cn.CMND = nv.CMND\r\n"
                        + "join CMND cmnd on cmnd.soCMND = cn.CMND\r\n"
                        + "join PHONGBAN pb on nv.maPhong = pb.maPhong\r\n"
                        + "join CHUCVU cv on cv.maChucVu = nv.maChucVu\r\n"
                        + "join TRINHDO td on td.maTrinhDo = nv.maTrinhDo where maNhanVien = N'"+maNhanVien+"'";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql);
                while(rs.next()) {
                    data = new String[] {
                        rs.getString("maNhanVien")+" - "+rs.getString("hoTen"),SUPPORT.LocalDateToString(rs.getDate("ngaySinh").toLocalDate()),rs.getString("gioiTinh"),
                        rs.getString("diaChi"),rs.getString("SDT"),rs.getString("email"),rs.getString("soCMND")+" - "+SUPPORT.LocalDateToString(rs.getDate("ngayCap").toLocalDate())+" - "+rs.getString("noiCap"),
                        rs.getString("trinhDoHocVan"),rs.getString("trinhDoChuyenMon"),rs.getString("chuyenNganh"),
                        rs.getString("tenPhong"),rs.getString("tenChucVu"),SUPPORT.changeSalaryToFormatString(rs.getDouble("luongThuViec")*1.25)
                    };
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return data;
        }
        public static void updateMaHopDong(String maNhanVien, String maHopDong) {
            Connection con = connection.getConnection();
            try {
                String sql = "update NHANVIEN set maHopDong = N'"+maHopDong+"' where maNhanVien = N'"+maNhanVien+"'";
                Statement st = con.createStatement();
                st.executeUpdate(sql);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static void hiddenNhanVien(String maNhanVien) {
            Connection con = connection.getConnection();
            try {
                String sql = "update NHANVIEN set trangThai=0 where maNhanVien = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1, maNhanVien);
                st.executeUpdate();
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        public static String getTen(String maNhanVien) {
            Connection con = connection.getConnection();
    
            try {
                String ten = "";
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("select * from CONNGUOI join NHANVIEN on CONNGUOI.CMND = NHANVIEN.CMND where NHANVIEN.maNhanVien = '" + maNhanVien + "' ");
    
                while (rs.next()) {
                    ten = rs.getString("maNhanVien") + " - " + rs.getString("hoTen");
                }
                connection.closeConnection(con);
                return ten;
            } catch (SQLException e) {
            }
            return null;
        }
    	public static double[] getLuongNhanVien(String maNhanVien) {
            Connection con = connection.getConnection();
            String sql = "select * from NHANVIEN nv left join HOPDONGLAODONG hd on nv.maHopDong = hd.maHopDong join CHUCVU cv on nv.maChucVu = cv.maChucVu where nv.maNhanVien=?";
            
            try {
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, maNhanVien);
                ResultSet rs = ps.executeQuery();
                while(rs.next()) {
                    if(rs.getString("maHopDong")==null) {
                        return new double[] {rs.getDouble("luongThuViec"), rs.getDouble("phuCapChucVu")};
                    }
                    return new double[] {rs.getDouble("luongCoBan"), rs.getDouble("phuCapChucVu")};
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
    }