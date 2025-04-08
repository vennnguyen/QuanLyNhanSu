package BUS;


import java.util.ArrayList;

import DAO.RoleGroupDAO;
import DTO.RoleGroupDTO;

public class RoleGroupBUS {
	private ArrayList<RoleGroupDTO> list;
	public RoleGroupBUS() {
		this.list = new ArrayList<>();
	}
	public  ArrayList<RoleGroupDTO> getList(){
		return this.list;
	}
	public void getDataFormDatabase() {
		this.list = RoleGroupDAO.getRoleGroupList();
	}
	public Object[][] getObjectToRender(){
		Object[][] ob = new Object[list.size()][];
		for(int i=0;i<list.size();i++) {
			ob[i] = new Object[] { i+"", list.get(i).getRoleGroupId()+" - "+list.get(i).getRoleGroupName()};
		}
		return ob;
	}
	public String[] getMaNhomQuyenForCBB(){
		String[] ob = new String[list.size()];
		for(int i=0;i<list.size();i++) {
			ob[i] = list.get(i).getRoleGroupId();
		}
		return ob;
	} 
}
