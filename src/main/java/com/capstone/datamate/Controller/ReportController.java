package com.capstone.datamate.Controller;

import java.util.List;
import java.util.Map;

import com.capstone.datamate.Service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.datamate.Entity.ReportEntity;
import com.capstone.datamate.Entity.FileEntity;
import com.capstone.datamate.Service.ReportService;

@CrossOrigin("http://localhost:3000/")
@RestController
public class ReportController {

    @Autowired
    ReportService reportServ;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private OpenAIService openAIService;

    @PostMapping("/postReports")
    public ReportEntity postReport(@RequestBody ReportEntity rprt){
        return reportServ.postReport(rprt);
    }

    @GetMapping("/getReports")
    public ReportEntity getReport(@RequestParam int rprtId){
        return reportServ.getReport(rprtId);
    }

    @GetMapping("/getReportsByNameAndID")
    public ReportEntity getReport(@RequestParam String rprtName, @RequestParam int userid){
        return reportServ.getReportByNameAndUserId(rprtName, userid);
    }

    @GetMapping("/getUserReports/{userId}")
    public List<ReportEntity> getUserReports(@PathVariable int userId){
        return reportServ.getReportByUser(userId);
    }


    @DeleteMapping("/deleteReport")
    public String deleteReport(@RequestParam int rprtId){
        return reportServ.deleteReport(rprtId);
    }

}
