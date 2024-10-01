package com.capstone.datamate.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import com.capstone.datamate.Entity.ReportEntity;
import com.capstone.datamate.Service.ReportService;

@CrossOrigin("http://localhost:3000/")
@RestController
public class ReportController {

    @Autowired
    ReportService reportServ;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/postReports")
    public ReportEntity postReport(@RequestBody ReportEntity rprt) {
        return reportServ.postReport(rprt);
    }

    @GetMapping("/getReports")
    public ReportEntity getReport(@RequestParam int rprtId) {
        return reportServ.getReport(rprtId);
    }

    @GetMapping("/getReportsByNameAndID")
    public ReportEntity getReport(@RequestParam String rprtName, @RequestParam int userId) {
        return reportServ.getReportByNameAndUserId(rprtName, userId); // Updated to use userId directly
    }

    @GetMapping("/getUserReports/{userId}")
    public List<ReportEntity> getUserReports(@PathVariable int userId) {
        return reportServ.getReportByUser(userId); // Updated to use userId directly
    }

    @DeleteMapping("/deleteReport")
    public String deleteReport(@RequestParam int rprtId) {
        return reportServ.deleteReport(rprtId);
    }

    @GetMapping("/getReportCode/{reportId}")
    public String getReportCodeById(@PathVariable int reportId) {
        String reportCode = reportServ.getReportCodeById(reportId);
        return reportCode;
    }

//    @GetMapping("/executeReportQueryById/{reportId}")
//    public ResponseEntity<List<Map<String, Object>>> executeReportQueryById(@PathVariable int reportId) {
//        // Fetch the report code from the database based on reportId
//        String reportCode = reportServ.getReportCodeById(reportId);  // Fetch report code from DB
//        System.out.println("Executing SQL Query: " + reportCode);
//
//        // Execute the SQL query
//        List<Map<String, Object>> result = reportServ.executeSQLQuery(reportCode);
//        return ResponseEntity.ok(result);
//    }
    @GetMapping("/executeReportQueryById/{reportId}")
    public ResponseEntity<?> executeReportQueryById(@PathVariable int reportId) {
        String reportCode;
        try {
            // Fetch the report code from the database based on reportId
            reportCode = reportServ.getReportCodeById(reportId);
            if (reportCode == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Report code not found for ID: " + reportId);
            }

            System.out.println("Executing SQL Query: " + reportCode);

            // Execute the SQL query
            List<Map<String, Object>> result = reportServ.executeSQLQuery(reportCode);
            return ResponseEntity.ok(result);
        } catch (DataAccessException e) {
            System.err.println("Database access error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Database access error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request: " + e.getMessage());
        }
    }

    @PutMapping("/rename/{reportId}")
    public ResponseEntity<?> renameReport(@PathVariable int reportId, @RequestBody Map<String, String> body) {
        String newReportName = body.get("newReportName");
        try {
            ReportEntity updatedReport = reportServ.setReportName(reportId, newReportName);
            return ResponseEntity.ok(updatedReport); // Return the updated report entity
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
