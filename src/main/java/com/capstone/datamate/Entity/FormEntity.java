package com.capstone.datamate.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_form")
public class FormEntity {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int formId;

    @Setter
    @Getter
    @Column(name = "db_name")
    private String dbName;

    @Setter
    @Getter
    @Column(name = "tbl_name")
    private String tblName;

    @Setter
    @Getter
    @Column(name = "form_name")
    private String formName;

    @Setter
    @Getter
    @Column(name = "headers")
    private String headers;  // Assuming this is a JSON or comma-separated string

    @Setter
    @Getter
    @Column(name = "custom_settings")
    private String customSettings;  // Assuming this is a JSON or serialized data

    @Setter
    @Getter
    @Column(name = "user_id")
    private int userId;

    @Setter
    @Getter
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Default constructor
    public FormEntity() {}

    // Constructor with all fields
    public FormEntity(int formId, String dbName, String formName, String headers, String customSettings, int userId, LocalDateTime createdAt) {
        this.formId = formId;
        this.dbName = dbName;
        this.formName = formName;
        this.headers = headers;
        this.customSettings = customSettings;
        this.userId = userId;
        this.createdAt = createdAt;
    }
}
