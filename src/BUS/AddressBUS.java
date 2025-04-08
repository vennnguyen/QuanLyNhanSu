package BUS;

import java.util.ArrayList;

import DAO.AddressDAO;
import DTO.City;

public class AddressBUS {
	private ArrayList<City> list;
	public AddressBUS() {
		list = new ArrayList<>();
	}
	public ArrayList<City> getList(){
		return this.list;
	}
	public void getDataFromDataBase() {
		this.list = AddressDAO.getList();
	}
	public String[] getDanhSachTinhThanhPhoString() {
		String str[] = new String[list.size()];
		for(int i=0;i<list.size();i++) {
			str[i] = list.get(i).getCityName();
		}
		return str;
	}
}
