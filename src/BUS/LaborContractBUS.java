package BUS;


import java.util.ArrayList;
import java.util.Collections;

import DAO.LaborContractDAO;

import DTO.LaborContractDTO;
import DTO.PermanentEmployeeDTO;
import DTO.SUPPORT;

public class LaborContractBUS {

	private ArrayList<LaborContractDTO> list;

	public void getDataFromDatabase() {
		list = LaborContractDAO.getList();
	}

	public LaborContractBUS() {
		this.list = new ArrayList<LaborContractDTO>();
	}

	public LaborContractBUS(ArrayList<LaborContractDTO> list) {
		this.list = list;
	}

	public ArrayList<LaborContractDTO> getList() {
		return this.list;
	}
	

    public Object[][] getObjectToRender() {
        int n = list.size();
        Object[][] ob = new Object[n][];
        for (int i = 0; i < n; i++) {
            LaborContractDTO temp = list.get(i);
            if (!temp.getContractType().contains("năm")) {
                temp.setContractType(temp.getContractType() + " năm");
            }
            ob[i] = new Object[] {
                (i + 1) + "",
                temp.getEmployeeId() + " - " + temp.getEmployeeName(),
                temp.getDepartment(),
                SUPPORT.LocalDateToString(temp.getStartDate()),
                SUPPORT.LocalDateToString(temp.getEndDate()),
                temp.getContractType(),
                "  " + SUPPORT.changeSalaryToFormatString(temp.getBaseSalary())
            };
        }
        return ob;
    }

	public ArrayList<LaborContractDTO> findEmployee(String find) {
		ArrayList<LaborContractDTO> arr = new ArrayList<>();
		find = find.trim().toLowerCase();
		for (LaborContractDTO i : list) {
			if (i.getContractId().toLowerCase().contains(find) || i.getEmployeeName().toLowerCase().contains(find) || i.getEmployeeId().contains(find) || i.getContractType().contains(find)) {
				arr.add(i);
			}
		}
		return arr;
	}

	public PermanentEmployeeDTO getNhanVienByMaNV(String maNhanVien) {
		ArrayList<PermanentEmployeeDTO> arr = new ArrayList<>();
		maNhanVien = maNhanVien.trim();
		for (PermanentEmployeeDTO i : arr) {
			if (i.getEmployeeId().equalsIgnoreCase(maNhanVien)) {
				return i;
			}
		}
		return null;
	}

	public void sortByName(int type) {
		list.sort((o1, o2) -> SUPPORT.soSanhChuoiUTF8(SUPPORT.convertNameToSort(o1.getEmployeeName()),
				SUPPORT.convertNameToSort(o2.getEmployeeName())));
		if (type != 0) {
			Collections.reverse(list);
		}
	}

	public void sortByID(int type) {
		list.sort((o1, o2) -> o1.getContractId().compareTo(o2.getContractId()));
		if (type != 0) {
			Collections.reverse(list);
		}
	}

	public void sortByLoaiHopDong(int type) {
		list.sort((o1, o2) -> SUPPORT.compareDouble(Integer.valueOf(o1.getContractType().split(" ")[0]),Integer.valueOf(o2.getContractType().split(" ")[0])));
		if (type != 0) {
			Collections.reverse(list);
		}
	}
	public void sortByTuNgay(int type) {
		list.sort((o1, o2) -> (o1.getStartDate().compareTo(o2.getStartDate())));
		if (type != 0) {
			Collections.reverse(list);
		}
	}
	public void sortByDenNgay(int type) {
		list.sort((o1, o2) -> (o1.getEndDate().compareTo(o2.getEndDate())));
		if (type != 0) {
			Collections.reverse(list);
		}
	}
	

	public void sortBySalary(int type) {
		list.sort((o1, o2) -> SUPPORT.compareDouble(o1.getBaseSalary(), o2.getBaseSalary()));
		if (type != 0) {
			Collections.reverse(list);
		}
	}
//	public ArrayList<HOPDONGLAODONG> getNhanVienByMaHD(String maHopDong){
//		ArrayList<HOPDONGLAODONG> arr = new ArrayList<>();
//		for(HOPDONGLAODONG i : list) {
//			if(i.getMaHopDong().equalsIgnoreCase(maHopDong)) {
//				arr.add(i);
//			}
//		}
//		return arr;
//	}
//	
	public LaborContractDTO getHopDongTheoMaNhanVien(String maNhanVien) {
		
		for (LaborContractDTO i : list) {
			if (i.getEmployeeId().equals(maNhanVien)) {
				return i;
			}
		}
		return null;
	}
	public ArrayList<LaborContractDTO> getNhanVienByMinLuong(double minSalary) {
		ArrayList<LaborContractDTO> arr = new ArrayList<>();
		for (LaborContractDTO i : list) {
			if (i.getBaseSalary() >= minSalary) {
				arr.add(i);
			}
		}
		return arr;
	}

	public ArrayList<LaborContractDTO> getNhanVienByMaxLuong(double maxSalary) {
		ArrayList<LaborContractDTO> arr = new ArrayList<>();
		for (LaborContractDTO i : list) {
			if (i.getBaseSalary() <= maxSalary) {
				arr.add(i);
			}
		}
		return arr;
	}
	public ArrayList<LaborContractDTO> getHopDongTheoTenPhong(String tenPhong) {
		ArrayList<LaborContractDTO> arr = new ArrayList<>();
		for (LaborContractDTO i : list) {
			if (i.getDepartment().equals(tenPhong) ) {
				arr.add(i);
			}
		}
		return arr;
	}

	public ArrayList<LaborContractDTO> getHopDongTheoLoaiHopDong(String loaiHopDong) {
		if(loaiHopDong.equalsIgnoreCase("trên 10 năm")) {
			return getHopDongTren10Nam();
		}
		ArrayList<LaborContractDTO> arr = new ArrayList<>();
		for (LaborContractDTO i : list) {
			if (i.getContractType().equalsIgnoreCase(loaiHopDong)) {
				arr.add(i);
			} 
		}
		return arr;
	}
	public ArrayList<LaborContractDTO> getHopDongTren10Nam() {
		ArrayList<LaborContractDTO> arr = new ArrayList<>();
		for (LaborContractDTO i : list) {
			int thoiHan = Integer.valueOf(i.getContractType().split(" ")[0]);
			if (thoiHan>10) {
				arr.add(i);
			} 
		}
		return arr;
	}
}
