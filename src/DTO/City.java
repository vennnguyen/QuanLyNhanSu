package DTO;

import java.util.ArrayList;

public class City {
    private String cityName;
    private ArrayList<District> districtList;
    
    public City() {
        cityName = "";
        districtList = new ArrayList<>();
    }
    
    public String getCityName() {
        return cityName;
    }
    
    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public ArrayList<District> getDistrictList() {
        return districtList;
    }
    
    public void setDistrictList(ArrayList<District> districtList) {
        this.districtList = districtList;
    }
    
    public String[] getDistrictListAsStrings() {
        String[] list = new String[districtList.size()];
        for(int i = 0; i < districtList.size(); i++) {
            list[i] = districtList.get(i).getDistrictName();
        }
        return list;
    }
}