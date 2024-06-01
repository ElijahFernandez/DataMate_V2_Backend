package com.capstone.datamate.Message;

import java.time.LocalDateTime;

public class ResponseFile {

    private int fileId;
    private String fileName;
    private float fileSize;
    private LocalDateTime uploadDate;
    private LocalDateTime latestDateModified;
    private boolean isdeleted;
    private String url;

    public ResponseFile(){}

    public ResponseFile(int fileId, String fileName, float fileSize, LocalDateTime uploadDate,
            LocalDateTime latestDateModified, boolean isdeleted, String url) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.latestDateModified = latestDateModified;
        this.isdeleted = isdeleted;
        this.url = url;
    }
    public int getFileId() {
        return fileId;
    }
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    public float getFileSize() {
        return fileSize;
    }
    public void setFileSize(float fileSize) {
        this.fileSize = fileSize;
    }
    public LocalDateTime getUploadDate() {
        return uploadDate;
    }
    public void setUploadDate(LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }
    public LocalDateTime getLatestDateModified() {
        return latestDateModified;
    }
    public void setLatestDateModified(LocalDateTime latestDateModified) {
        this.latestDateModified = latestDateModified;
    }
    public boolean isIsdeleted() {
        return isdeleted;
    }
    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }

    
    
}
