package com.capstone.datamate.Service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.capstone.datamate.Entity.ReportEntity;
import com.capstone.datamate.Entity.FileEntity;
import com.capstone.datamate.Repository.ReportRepository;

@Service
public class ReportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ReportRepository reportRepo;

    public ReportEntity postReport(ReportEntity db){
        return reportRepo.save(db);
    }

    public ReportEntity getReport(int id){
        return reportRepo.findById(id).get();
    }

    public ReportEntity getReportByNameAndUserId(String name, int id){
        return reportRepo.findByReportNameAndUserUserId(name, id);
    }

    //fetch
    public List<ReportEntity> getReportByUser(int userId){
        return reportRepo.findByUserUserId(userId);
    }


    public String deleteReport(int id) {
        String msg;
        if(reportRepo.findById(id) != null) {
            ReportEntity rprt = reportRepo.findById(id).get();
            reportRepo.delete(rprt);
            msg = "Report ID number " + id + " deleted successfully!";
        }else {
            msg = "Report ID number " + id + " is NOT found!";
        }
        return msg;
    }
}
