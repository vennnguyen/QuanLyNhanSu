package DTO;

import java.time.LocalDate;

public class Requirement {

    private String requestId;
    private String senderId;
    private String requestInfo;
    private String reason;
    private LocalDate sentDate;
    private String confirmer;
    private STATUS status;

    public Requirement() {
        this.requestId = "";
        this.senderId = "";
        this.requestInfo = "";
        this.reason = "";
        this.sentDate = null;
        this.confirmer = "";
        this.status = STATUS.PENDING;
    }

    public Requirement(String requestId, String senderId, String requestInfo, String reason, LocalDate sentDate, String confirmer, STATUS status) {
        this.requestId = requestId;
        this.senderId = senderId;
        this.requestInfo = requestInfo;
        this.reason = reason;
        this.sentDate = sentDate;
        this.confirmer = confirmer;
        this.status = status;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public LocalDate getSentDate() {
        return sentDate;
    }

    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    public String getConfirmer() {
        return confirmer;
    }

    public void setConfirmer(String confirmer) {
        this.confirmer = confirmer;
    }

    public STATUS getStatus() {
        return status;
    }

    public void setStatus(STATUS status) {
        this.status = status;
    }

    public enum STATUS {
        APPROVED,
        PENDING
    }

}