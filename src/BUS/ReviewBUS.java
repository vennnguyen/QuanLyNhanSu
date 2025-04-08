package BUS;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

import DAO.ReviewDAO;
import DTO.ReviewDTO;
import DTO.SUPPORT;

public class ReviewBUS {
    private ArrayList<ReviewDTO> list = new ArrayList<>();

    public ReviewBUS() {
        list  = new ArrayList<>();
    }

    public ReviewBUS(ArrayList<ReviewDTO> list) {
        this.list = list;
    }

    public ArrayList<ReviewDTO> getList(){
        return this.list;
    }

    public void setList(ArrayList<ReviewDTO> list) {
        this.list = list;
    }

    public void getDataFromDatabase() {
        list = ReviewDAO.getList();
    }
    
    public ReviewBUS getDANHSACHDANHGIATheoMaNhanVien(String maNhanVien){
        ReviewBUS data = new ReviewBUS();
        for(ReviewDTO r : list) {
            if(r.getEmployeeId().equals(maNhanVien)) {
                data.getList().add(r);
            }
        }
        return data;
    }
    
    public Object[][] getObjectToRender(){
        Object[][] obj = new Object[list.size()][];
        for(int i = 0; i < list.size(); i++) {
            ReviewDTO r = list.get(i);
            obj[i] = new Object[] {
                i + 1,
                r.getReviewId(),
                r.getEmployeeId() + " - " + r.getEmployeeName(),
                SUPPORT.LocalDateToString(r.getReviewDate()),
                r.getReviewerId() + " - " + r.getReviewerName(),
                r.getReviewScore(),
                r.getClassification(),
                r.getReviewType()
            };
        }
        return obj;
    }
    
    public ReviewDTO getDanhGia(String reviewId) {
        for(ReviewDTO r : list) {
            if(r.getReviewId().equals(reviewId)) {
                return r;
            }
        }
        return null;
    }
    
    public ReviewBUS getDANHSACHBANGDANHGIA(String reviewType, LocalDate batDau, LocalDate ketThuc) {
        ArrayList<ReviewDTO> arr = new ArrayList<>();
        for(ReviewDTO r : list) {
            if(r.getReviewType().equalsIgnoreCase(reviewType) &&
               r.getReviewDate().compareTo(batDau) >= 0 &&
               r.getReviewDate().compareTo(ketThuc) <= 0) {
                arr.add(r);
            }
        }
        return new ReviewBUS(arr);
    }
    
    public ReviewBUS getDANHSACHBANGDANHGIA(LocalDate batDau, LocalDate ketThuc) {
        ArrayList<ReviewDTO> arr = new ArrayList<>();
        for(ReviewDTO r : list) {
            if(r.getReviewDate().compareTo(batDau) >= 0 &&
               r.getReviewDate().compareTo(ketThuc) <= 0) {
                arr.add(r);
            }
        }
        return new ReviewBUS(arr);
    }
    
    public void sortByPoint(int type) {
        list.sort((r1, r2) -> r1.getReviewScore() - r2.getReviewScore());
        if(type == 0) {
            Collections.reverse(list);
        }
    }
    
    public void sortByDay(int type) {
        list.sort((r1, r2) -> r1.getReviewDate().compareTo(r2.getReviewDate()));
        if(type == 0) {
            Collections.reverse(list);
        }
    }
}