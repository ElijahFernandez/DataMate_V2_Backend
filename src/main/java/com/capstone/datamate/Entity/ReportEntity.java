package com.capstone.datamate.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="tbl_report")
public class ReportEntity {

    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int reportId;

    @Setter
    @Getter
    @Column
    private String reportName;

    @Setter
    @Getter
    @Column(name = "report_code")
    private String reportCode;

    @Setter
    @Getter
    @Column(name = "user_id")
    private int userId;  // Changed from UserEntity to int for user_id

    public ReportEntity(){}

    public ReportEntity(int reportId, String reportName, String reportCode, int userId) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.reportCode = reportCode;
        this.userId = userId;
    }

}