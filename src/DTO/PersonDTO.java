package DTO;

import java.time.LocalDate;

public class PersonDTO {
    private IdentityCardDTO identityCard;
    private String name;
    private String gender;
    private LocalDate dateOfBirth;
    private AddressDTO address;
    private String phoneNumber;
    private String maritalStatus;
    private String ethnicGroup; 
    private String religion;
    private String email;

    public PersonDTO() {
        this.name = null;
        this.gender = null;
        this.dateOfBirth = LocalDate.now();
        this.address = new AddressDTO();
        this.identityCard = new IdentityCardDTO();
        this.phoneNumber = null;
        this.maritalStatus = null;
        this.religion = null;
        this.email = null;
    }

    public PersonDTO(IdentityCardDTO identityCard, String name, String gender, LocalDate dateOfBirth, AddressDTO address, String phoneNumber, String ethnicGroup, String marriageStatus, String religion, String email) {
        this.name = name;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.identityCard = identityCard;
        this.phoneNumber = phoneNumber;
        this.maritalStatus = marriageStatus;
        this.religion = religion;
        this.ethnicGroup = ethnicGroup;
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public void setIdentityCard(IdentityCardDTO identityCard) {
        this.identityCard = identityCard;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setMaritalStatus(String marriageStatus) {
        this.maritalStatus = marriageStatus;
    }

    public void setReligion(String religion) {
        this.religion = religion;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getGender() {
        return gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public IdentityCardDTO getIdentityCard() {
        return identityCard;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public String getEthnicGroup() {
        return ethnicGroup;
    }

    public void setEthnicGroup(String ethnicGroup) {
        this.ethnicGroup = ethnicGroup;
    }
    public String getMaritalStatus() {
        return maritalStatus;
    }

    public String getReligion() {
        return religion;
    }

    public String getEmail() {
        return email;
    }
    public int getTuoi() {
        LocalDate hienTai = LocalDate.now();
        if(hienTai.getMonthValue() > dateOfBirth.getMonthValue()) {
            return hienTai.getYear() - dateOfBirth.getYear();
        } else if(hienTai.getMonthValue() == dateOfBirth.getMonthValue()) {
            if(hienTai.getDayOfMonth() >= dateOfBirth.getDayOfMonth()) {
                return hienTai.getYear() - dateOfBirth.getYear();
            } else {
                return hienTai.getYear() - dateOfBirth.getYear() - 1;
            }
        } else {
            return hienTai.getYear() - dateOfBirth.getYear() - 1;
        }
    }
    
}