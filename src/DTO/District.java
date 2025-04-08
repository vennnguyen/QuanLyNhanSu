package DTO;

import java.util.ArrayList;

public class District {
    private String districtName;
    private ArrayList<Ward> wardList;

    public District() {
        districtName = "";
        wardList = new ArrayList<>();
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public ArrayList<Ward> getWardList() {
        return wardList;
    }

    public void setWardList(ArrayList<Ward> wardList) {
        this.wardList = wardList;
    }

    public String[] getWardListAsStrings() {
        String[] list = new String[wardList.size()];
        for (int i = 0; i < wardList.size(); i++) {
            list[i] = wardList.get(i).getWardName();
        }
        return list;
    }
}