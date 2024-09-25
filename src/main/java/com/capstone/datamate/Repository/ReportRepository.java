package com.capstone.datamate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.datamate.Entity.ReportEntity;

@Repository
public interface ReportRepository extends JpaRepository<ReportEntity, Integer>{

    List<ReportEntity> findByUserUserId(int userId);
    ReportEntity findByReportNameAndUserUserId(String name, int id);
}
