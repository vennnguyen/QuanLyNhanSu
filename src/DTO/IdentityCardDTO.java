package DTO;

import java.time.LocalDate;

public class IdentityCardDTO {
    
    private String id;
    private String placeOfIssue;
    private LocalDate dateOfIssue;

    public IdentityCardDTO () {
    }
    
    public IdentityCardDTO(String id, String placeOfIssue, LocalDate dateOfIssue){
        this.id = id;
        this.placeOfIssue = placeOfIssue;
        this.dateOfIssue = dateOfIssue;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    @Override
    public String toString() {
        return "IdentityCardDTO{" +
                "id='" + id + '\'' +
                ", placeOfIssue='" + placeOfIssue + '\'' +
                ", dateOfIssue=" + dateOfIssue +
                '}';
    }
}