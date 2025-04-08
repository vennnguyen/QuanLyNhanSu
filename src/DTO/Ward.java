package DTO;

import java.util.ArrayList;

public class Ward {
    private String wardName;
    private ArrayList<String> streetList;
    
    public Ward() {
        wardName = "";
        streetList = new ArrayList<>();
    }
    
    public String getWardName() {
        return wardName;
    }
    
    public void setWardName(String wardName) {
        this.wardName = wardName;
    }
    
    public ArrayList<String> getStreetList() {
        return streetList;
    }
    
    public void setStreetList(ArrayList<String> streetList) {
        this.streetList = streetList;
    }
    
    public String[] getStreetListAsArray() {
        String list[] = new String[streetList.size()];
        for(int i = 0; i < streetList.size(); i++) {
            list[i] = streetList.get(i);
        }
        return list;
    }
}