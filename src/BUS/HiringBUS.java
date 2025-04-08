package BUS;

import java.util.ArrayList;
import DAO.HiringDAO;
import DTO.HiringReportDTO;
import DTO.Format;

public class HiringBUS {
    private ArrayList<HiringReportDTO> list = new ArrayList<>();

    public HiringBUS() {
        list = new ArrayList<>();
    }

    public HiringBUS(ArrayList<HiringReportDTO> list) {
        this.list = list;
    }

    public ArrayList<HiringReportDTO> getList(){
        return this.list;
    }

    public void setList(ArrayList<HiringReportDTO> list) {
        this.list = list;
    }

    public void getDataFromDatabase() {
        list = HiringDAO.getList();
    }
    
    public Object[][] getObject(){
        Object[][] data = new Object[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            HiringReportDTO obj = list.get(i);
            data[i] = new Object[]{
                i+1,
                obj.getRecruitmentId(), 
                obj.getPosition(), 
                obj.getQualification(),
                obj.getGender(),
                obj.getAgeRange(),
                obj.getRequiredPositions(),
                obj.getApplicationDeadline(),
                Format.vnd(obj.getMinimumSalary()),
                Format.vnd(obj.getMaximumSalary()),
                obj.getApplicationsReceived(),
                obj.getPositionsFilled()
            };
        }
        return data;
    }
    
    public ArrayList<String> setMaTuyenDung(){
        ArrayList<String> a = new ArrayList<>();
        for(HiringReportDTO i: list) {
            a.add(i.getRecruitmentId());
        }
        return a;
    }
    
    public HiringReportDTO getBaoCaoTuyenDung(String maBaoCao) {
        for(HiringReportDTO i : list) {
            if(i.getRecruitmentId().equals(maBaoCao)) {
                return i;
            }
        }
        return null;
    }
}