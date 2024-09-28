package com.capstone.datamate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.datamate.Entity.ReportEntity;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {

    List<ReportEntity> findByUserId(int userId); // Updated to use userId directly
    ReportEntity findByReportNameAndUserId(String name, int userId); // Updated to use userId directly
    @Query("SELECT r.reportCode FROM ReportEntity r WHERE r.reportId = :reportId")
    String findReportCodeByReportId(@Param("reportId") int reportId);
}
