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
    @Column
    private String reportCode;

    @Setter
    @Getter
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    UserEntity user;

    public ReportEntity(){}

    public ReportEntity(int reportId, String reportName, String reportCode, UserEntity user) {
        this.reportId = reportId;
        this.reportName = reportName;
        this.reportCode = reportCode;
        this.user = user;
    }

}
