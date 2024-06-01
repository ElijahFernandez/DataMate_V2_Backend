package com.capstone.datamate.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;

@Entity
@Table(name="tbl_template")
public class TemplateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int templateId;
    
    @Column
    private String templateName;

    @Column(length=1000000)
    @Lob
    private byte[] data;
    
    @Column
    private boolean isDownloaded;

    public TemplateEntity(){}

    public TemplateEntity( String templateName, byte[] data) {
        this.templateName = templateName;
        this.data = data;
    }

    public TemplateEntity(int templateId, String templateName, byte[] data) {
        this.templateId = templateId;
        this.templateName = templateName;
        this.data = data;
    }

    public int getTemplateId() {
        return templateId;
    }

    public void setTemplateId(int templateId) {
        this.templateId = templateId;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

	public boolean isDownloaded() {
		return isDownloaded;
	}

	public void setDownloaded(boolean isDownloaded) {
		this.isDownloaded = isDownloaded;
	}
    

}
