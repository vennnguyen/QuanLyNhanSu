package DTO;

public class AddressDTO {
    private String houseNumber;
    private String street;
    private String ward;
    private String district;
    private String city;
    
    public AddressDTO(){
        this.houseNumber = "";
        this.street = "";
        this.ward = "";
        this.district = "";
        this.city = "";
    }
    
    public AddressDTO(String houseNumber, String street, String ward, String district, String city){
        this.houseNumber = houseNumber;
        this.street = street;
        this.ward = ward;
        this.district = district;
        this.city = city;
    }
    
    public String getHouseNumber() {
        return this.houseNumber;
    }
    
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    
    public String getStreet() {
        return this.street;
    }
    
    public void setStreet(String street) {
        this.street = street;
    }
    
    public String getWard() {
        return this.ward;
    }
    
    public void setWard(String ward) {
        this.ward = ward;
    }
    
    public String getDistrict() {
        return this.district;
    }
    
    public void setDistrict(String district) {
        this.district = district;
    }
    
    public String getCity() {
        return this.city;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public String toString() {
        return houseNumber + " " + street + ", " + ward + ", " + district + ", " + city;
    }
}
