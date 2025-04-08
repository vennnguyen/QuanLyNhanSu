package BUS;

import java.util.ArrayList;

import DAO.CandidateDAO;
import DTO.Format;
import DTO.CandidateDTO;



public class CandidateBUS {
	private ArrayList<CandidateDTO> list;
	public CandidateBUS() {
		list = new ArrayList<>();
	}
	public CandidateBUS(ArrayList<CandidateDTO> list) {
		this.list = list;
	}
	public ArrayList<CandidateDTO> getList(){
		return this.list;
	}
	public void getDataFromDatabase() {
		list=CandidateDAO.getList();
	}
	public Object[][] getObject(){
		Object[][] data = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
        	CandidateDTO obj = list.get(i);
            data[i] = new Object[]{obj.getRecruitmentCode(), obj.getCandidateCode()+" - "+obj.getName(),obj.getPhoneNumber(),obj.getEmail(),obj.getPosition(),obj.getEducationLevel().getAcademicLevel(),Format.vnd(obj.getDesiredSalary()),obj.getStatus()};
        }
        return data;
	}
	public ArrayList<CandidateDTO> getList(String maBCTD){
		ArrayList<CandidateDTO> temp = new ArrayList<>();
		for(CandidateDTO i : list) {
			if(i.getRecruitmentCode().equals(maBCTD)) {
				temp.add(i);
			}
		}
		return temp;
	}
	public CandidateDTO getUngVien(String maUngVien) {
		for(CandidateDTO i : list) {
			if(i.getCandidateCode().equals(maUngVien)) {
				return i;
			}
		}
		return null;
	}
}
