package BUS;

import java.util.ArrayList;

import DAO.SalaryDAO;
import DTO.SalaryDTO;

public class SalaryBUS {
	private ArrayList<SalaryDTO> list;
	public SalaryBUS() {
		list = new ArrayList<>();
	}
	public void getDataFromDatabase() {
		list = SalaryDAO.getList();
	}
	public ArrayList<SalaryDTO> getList() {
		return this.list;
	}
	public void getObjectToRender() {
	}
}
