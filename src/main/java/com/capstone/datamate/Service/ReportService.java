package com.capstone.datamate.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.capstone.datamate.Entity.ReportEntity;
import com.capstone.datamate.Repository.ReportRepository;

@Service
public class ReportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    ReportRepository reportRepo;

    public ReportEntity postReport(ReportEntity db) {
        try {
            return reportRepo.save(db);
        } catch (DataIntegrityViolationException e) {
            // Log or handle the error appropriately
            throw new RuntimeException("User ID does not exist in tbl_user: " + db.getUserId());
        }
    }

    public ReportEntity getReport(int id) {
        return reportRepo.findById(id).orElse(null); // Updated to handle Optional
    }

    public ReportEntity getReportByNameAndUserId(String name, int userId) {
        return reportRepo.findByReportNameAndUserId(name, userId); // Updated to use userId directly
    }

    public List<ReportEntity> getReportByUser(int userId) {
        return reportRepo.findByUserId(userId); // Updated to use userId directly
    }

    public String deleteReport(int id) {
        String msg;
        if (reportRepo.findById(id).isPresent()) { // Updated to handle Optional
            ReportEntity rprt = reportRepo.findById(id).get();
            reportRepo.delete(rprt);
            msg = "Report ID number " + id + " deleted successfully!";
        } else {
            msg = "Report ID number " + id + " is NOT found!";
        }
        return msg;
    }

    public String getReportCodeById(int reportId) {
        return reportRepo.findReportCodeByReportId(reportId);
    }

//    public List<Map<String, Object>> executeSQLQuery(String reportCode) {
//        // Use JdbcTemplate to execute the SQL query
//        return jdbcTemplate.queryForList(reportCode); // This assumes your query is safe to execute
//    }
    public List<Map<String, Object>> executeSQLQuery(String reportCode) {
        try {
            // Validate the report code before executing the query
            if (reportCode == null || reportCode.isEmpty()) {
                throw new IllegalArgumentException("Invalid report code provided.");
            }
            return jdbcTemplate.queryForList(reportCode);
        } catch (DataAccessException e) {
            System.err.println("SQL execution error: " + e.getMessage());
            throw new RuntimeException("Error executing SQL query: " + e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            System.err.println("Invalid argument: " + e.getMessage());
            throw e;  // Re-throwing to let the controller handle it
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            throw new RuntimeException("Unexpected error: " + e.getMessage(), e);
        }
    }

    public ReportEntity setReportName(int reportId, String newReportName) {
        Optional<ReportEntity> reportOpt = reportRepo.findById(reportId); // Fetch the report by ID
        if (reportOpt.isPresent()) {
            ReportEntity report = reportOpt.get();
            report.setReportName(newReportName); // Set the new report name
            return reportRepo.save(report); // Save the updated report entity
        } else {
            throw new RuntimeException("Report ID " + reportId + " not found.");
        }
    }
}
