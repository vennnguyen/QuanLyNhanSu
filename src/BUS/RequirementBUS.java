package BUS;

import java.util.ArrayList;

import DTO.Requirement;

public class RequirementBUS {
	private ArrayList<Requirement> list;
	public RequirementBUS() {
		this.list = new ArrayList<>();
	}
	public RequirementBUS(ArrayList<Requirement> list) {
		this.list = list;
	}
	public ArrayList<Requirement> getList(){
		return this.list;
	}
}
