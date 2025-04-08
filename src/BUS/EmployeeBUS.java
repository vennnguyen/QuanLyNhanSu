package BUS;

import java.util.ArrayList;
import java.util.Collections;

import DAO.CompanyPositionDAO;
import DAO.EmployeeDAO;
import DAO.DepartmentDAO;
import DTO.PositionDTO;
import DTO.IdentityCardDTO;
import DTO.AddressDTO;
import DTO.LaborContractDTO;
import DTO.EmployeeDTO;
import DTO.PermanentEmployeeDTO;
import DTO.ProbationEmployeeDTO;
import DTO.SUPPORT;
import DTO.AccountDTO;
import DTO.QualificationDTO;

public class EmployeeBUS {
	private ArrayList<EmployeeDTO> list;
	public EmployeeBUS() {
		list  = new ArrayList<EmployeeDTO>();
	}
	public EmployeeBUS(ArrayList<EmployeeDTO> list) {
		this.list = list;
	}
	public ArrayList<EmployeeDTO> getList(){
		return this.list;
	}
	
	
	public Object[][] getObjectToRender() {
		int n = list.size();
		Object[][] ob = new Object[n][];
		for(int i=0;i<n;i++) {
			EmployeeDTO temp = list.get(i);
			String modeEmployee = "";
			double salary = 0;
			if(temp instanceof ProbationEmployeeDTO) {
				modeEmployee = "Nhân viên thử việc";
				salary = ((ProbationEmployeeDTO) temp).getProbationSalary();
			}else {
				modeEmployee = "Nhân viên chính thức";
				salary = ((PermanentEmployeeDTO)temp).getContract().getBaseSalary();
			}
			ob[i] = new Object[] {i+1+"",temp.getAccount().getAvatarImg(),temp.getEmployeeId()+","+temp.getName()+","+modeEmployee,temp.getGender(),temp.getDateOfBirth().toString(),temp.getAddress().toString(),temp.getPhoneNumber(),DepartmentDAO.getTenTuMaSo(temp.getDepartmentId()),temp.getPosition().getPositionName(),SUPPORT.changeSalaryToFormatString(salary)};
		}
		return ob;
	}
	public Object[][] getObjectToExportFile() {
		int n = list.size();
		Object[][] ob = new Object[n][];
		for(int i=0;i<n;i++) {
			EmployeeDTO temp = list.get(i);
			String modeEmployee = "";
			double salary = 0;
			if(temp instanceof ProbationEmployeeDTO) {
				modeEmployee = "Nhân viên thử việc";
				salary = ((ProbationEmployeeDTO) temp).getProbationSalary();
			}else {
				modeEmployee = "Nhân viên chính thức";
				salary = ((PermanentEmployeeDTO)temp).getContract().getBaseSalary();
			}
			ob[i] = new Object[] {i+1+"",temp.getEmployeeId(),temp.getName(),temp.getGender(),SUPPORT.LocalDateToString(temp.getDateOfBirth()),temp.getAddress().toString(),temp.getEthnicGroup(),temp.getReligion(),temp.getMaritalStatus(),temp.getPhoneNumber(),temp.getEmail(),DepartmentDAO.getTenTuMaSo(temp.getDepartmentId()),temp.getPosition().getPositionName(),SUPPORT.LocalDateToString(temp.getPosition().getAppointmentDate()),SUPPORT.changeSalaryToFormatString(salary),modeEmployee};
		}
		return ob;
	}
	public ArrayList<EmployeeDTO> findEmployee(String find) {
		ArrayList<EmployeeDTO> arr = new ArrayList<>();
		find = find.trim().toLowerCase();
		for(EmployeeDTO i : list) {
			if(i.getName().toLowerCase().contains(find) || i.getEmployeeId().contains(find) || i.getAddress().toString().contains(find)) {
				arr.add(i);
			}
		}
		return arr;
	}
	public void deleteByID(String id) {
		for(EmployeeDTO i : list) {
			if(i.getEmployeeId().equalsIgnoreCase(id)) {
				list.remove(i);
				return;
			}
		}
	}
	public EmployeeDTO getNhanVien(String maNhanVien) {
		maNhanVien = maNhanVien.trim();
		for(EmployeeDTO i : list) {
			if(i.getEmployeeId().equalsIgnoreCase(maNhanVien)) {
				return i;
			}
		}
		return null;
	}
	public void getDataFromDatabase() {
		list = EmployeeDAO.getList();
	}
	
	public EmployeeDTO addEmployeeFromStringData(String arr[]) {
		EmployeeDTO x;
		if(arr[23].equalsIgnoreCase("Nhân viên chính thức") || arr[23].equalsIgnoreCase("Chính thức")) {
			x = new PermanentEmployeeDTO();
			((PermanentEmployeeDTO)x).setContract(new LaborContractDTO("HD"+arr[0], SUPPORT.toLocalDate(arr[24]), SUPPORT.toLocalDate(arr[24]).plusYears(Integer.valueOf(arr[25].split(" ")[0])), arr[25], Double.valueOf(arr[27])));
		}else {
			x = new ProbationEmployeeDTO();
			((ProbationEmployeeDTO)x).setStartProbationDate(SUPPORT.toLocalDate(arr[24]));
			((ProbationEmployeeDTO)x).setEndProbationDate(SUPPORT.toLocalDate(arr[26]));
			((ProbationEmployeeDTO)x).setProbationSalary(Double.valueOf(arr[27]));
		}
		x.setEmployeeId(arr[0].trim());
		x.setName(SUPPORT.convertHoTen(arr[1].trim()));
		x.setGender(arr[2]);
		x.setDateOfBirth(SUPPORT.toLocalDate(arr[3]));
		x.setPhoneNumber(arr[4]);
		x.setEmail(arr[5]);
		AddressDTO diaChi = new AddressDTO(arr[6].trim(), arr[7].trim(), arr[8].trim(), arr[9].trim(), arr[10].trim());
		x.setAddress(diaChi);
		QualificationDTO trinhDo = new QualificationDTO("TD"+arr[0].trim(), arr[11].trim(), arr[12].trim(), arr[13].trim());
		x.setQualification(trinhDo);
		IdentityCardDTO cmnd = new IdentityCardDTO(arr[14], arr[16],SUPPORT.toLocalDate(arr[15]));
		x.setIdentityCard(cmnd);
		x.setEthnicGroup(arr[17]);
		x.setReligion(arr[18]);
		x.setMaritalStatus(arr[19]);
		x.setDepartmentId(DepartmentDAO.getMaSoTuTen(arr[20]));
		PositionDTO cv =new PositionDTO("CV"+arr[0], arr[21], CompanyPositionDAO.getPhuCapChucVuCongTy(arr[21]), SUPPORT.toLocalDate(arr[22]));
		x.setPosition(cv);
		
		AccountDTO tk = new AccountDTO(arr[0].trim(), arr[0].trim(), null, arr[28].trim());
		x.setAccount(tk);
		list.add(x);
		return x;
		
	}
	public void sortByName(int type) {
		list.sort((o1, o2) -> SUPPORT.soSanhChuoiUTF8(SUPPORT.convertNameToSort(o1.getName()),SUPPORT.convertNameToSort(o2.getName())));
		if(type!=0) {
			Collections.reverse(list);
		}
    }
	public void sortByID(int type) {
		list.sort((o1,o2) -> o1.getEmployeeId().compareTo(o2.getEmployeeId()) );
		if(type!=0) {
			Collections.reverse(list);
		}
	}
	public void sortByAge(int type) {
		list.sort((o1,o2) -> o1.getDateOfBirth().compareTo(o2.getDateOfBirth()));
		if(type==0) {
			Collections.reverse(list);
		}
	}
	public void sortBySalary(int type) {
		list.sort((o1,o2) -> SUPPORT.compareDouble(o1.getBaseSalary(), o2.getBaseSalary()));
		if(type!=0) {
			Collections.reverse(list);
		}
	}
	public ArrayList<EmployeeDTO> getNhanVienPhongBan(String maPhong){
		ArrayList<EmployeeDTO> arr = new ArrayList<>();
		for(EmployeeDTO i : list) {
			if(i.getDepartmentId().equalsIgnoreCase(maPhong)) {
				arr.add(i);
			}
		}
		return arr;
	}
	public ArrayList<EmployeeDTO> getNhanVienTheoGioiTinh(String gioiTinh){
		ArrayList<EmployeeDTO> arr = new ArrayList<>();
		for(EmployeeDTO i : list) {
			if(i.getGender().equalsIgnoreCase(gioiTinh)) {
				arr.add(i);
			}
		}
		return arr;
	}
	public ArrayList<EmployeeDTO> getNhanVienTheoDoTuoi(int min, int max){
		ArrayList<EmployeeDTO> arr = new ArrayList<>();
		for(EmployeeDTO i : list) {
			if(i.getTuoi()>=min && i.getTuoi()<=max) {
				arr.add(i);
			}
		}
		return arr;
	}
	public ArrayList<EmployeeDTO> getNhanVienTheoLoaiHinh(String loaiHinh){
		if(loaiHinh.equalsIgnoreCase("Chính thức") || loaiHinh.equalsIgnoreCase("Nhân viên chính thức")) {
			ArrayList<EmployeeDTO> arr = new ArrayList<>();
			for(EmployeeDTO i : list) {
				if(i instanceof PermanentEmployeeDTO) {
					arr.add(i);
				}
			}
			return arr;
		}else {
			ArrayList<EmployeeDTO> arr = new ArrayList<>();
			for(EmployeeDTO i : list) {
				if(i instanceof ProbationEmployeeDTO) {
					arr.add(i);
				}
			}
			return arr;
		}
		
	}
	public Object[][] getObjectseToRender_Them() {
        int n = this.list.size();
        Object[][] obj = new Object[n][];
        for (int i = 0; i < n; i++) {
            EmployeeDTO temp = list.get(i);
            obj[i] = new Object[]{i + 1 + "", EmployeeDAO.getTen(temp.getEmployeeId()),"Chưa chấm công"};
        }
        return obj;
    }
}
