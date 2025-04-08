package BUS;


import java.util.ArrayList;

import DAO.EmployeeDAO;
import DAO.DepartmentDAO;
import DTO.DepartmentDTO;
import DTO.SUPPORT;

public class DepartmentBUS {
	private ArrayList<DepartmentDTO> list;
	public DepartmentBUS() {
		this.list = new ArrayList<>();
		
	}
	public DepartmentBUS(ArrayList<DepartmentDTO> list) {
		this.list = list;
	}
	public ArrayList<DepartmentDTO> getList(){
		return this.list;
	}
	public Object[][] getObjectToRender() {
		Object[][] ob = new Object[list.size()][];
		for(int i=0;i<list.size();i++) {
			DepartmentDTO temp = list.get(i);
			// lấy tên trưởng phòng
			String tenTruongPhong = "Chưa có";
			if(!temp.getManagerId().equals("Chưa có") && !temp.getManagerId().equals("")) {
				tenTruongPhong = EmployeeDAO.getTenNhanVien(temp.getManagerId());
			}
			// lấy ngày nhận chức
			String ngayNhanChuc = "";
			try {
				ngayNhanChuc = SUPPORT.LocalDateToString(EmployeeDAO.getNhanVien(temp.getManagerId()).getPosition().getAppointmentDate());
			}catch(Exception e) {
				ngayNhanChuc = "Chưa có";
			}
			ob[i] = new Object[]{"  "+(i+1),temp.getDepartmentId()+" - "+temp.getDepartmentName(),"  "+SUPPORT.LocalDateToString(temp.getEstablishmentDate()),"  "+tenTruongPhong,"  "+ngayNhanChuc,"  "+DepartmentDAO.getSoLuongNhanVien(temp.getDepartmentId()),"  "+SUPPORT.changeSalaryToFormatString(temp.getAverageSalaryDepartment())};
		}
		return ob;
	}
	public void getDataFromDatabase() {
		this.list = DepartmentDAO.getList();
	}
	
	public String[] getDanhSachTenPhongBan() {
		String []data = new String[this.list.size()];
		for(int i=0;i<list.size();i++) {
			data[i] = list.get(i).getDepartmentName();
		}
		return data;
	}
	public String[] getDanhSachTenPhongBanDeLoc() {
		String []data = new String[this.list.size()+1];
		data[0] = "Phòng ban";
		for(int i=0;i<list.size();i++) {
			data[i+1] = list.get(i).getDepartmentName();
		}
		return data;
	}
	public DepartmentDTO getPhongBan(String maPhong) {
		for(DepartmentDTO i : list) {
			if(i.getDepartmentId().equals(maPhong)) {
				return i;
			}
		}
		return null;
	}
	public String getMaPhong(String tenPhong) {
		for(DepartmentDTO i : list) {
			if(i.getDepartmentName().equalsIgnoreCase(tenPhong)) {
				return i.getDepartmentId();
			}
		}
		return "";
	}
}
