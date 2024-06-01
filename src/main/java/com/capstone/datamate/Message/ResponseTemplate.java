package com.capstone.datamate.Message;

public class ResponseTemplate {

    private int templateId;
    private String templateName;

    public ResponseTemplate(){}

    public ResponseTemplate(int templateId, String templateName) {
        this.templateId = templateId;
        this.templateName = templateName;
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
    
}
