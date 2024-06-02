package com.capstone.datamate.Controller;

import com.capstone.datamate.Entity.InsertValuesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.capstone.datamate.Config.JdbcTemplateImpl;
import com.capstone.datamate.Entity.SqlRequestEntity;

import java.util.List;

@CrossOrigin("http://localhost:3000/")
@RestController
public class JDBCController {
    @Autowired
    JdbcTemplateImpl jdbc;
    // @PostMapping("/convert")
    // public void executeSQL(@RequestParam String tblName, @RequestParam String vals, @RequestParam int op){
    //     jdbc.executeSQL(tblName, vals, op);
    // }

    @PostMapping("/convert")
    public void executeSQL(@RequestBody SqlRequestEntity sql){
        jdbc.executeSQL(sql.getTblName(), sql.getVals(), sql.getOperation());
    }

    @GetMapping("/columns")
    public List<String> getColumnHeaders(@RequestParam String tableName) {
        return jdbc.getColumnHeaders(tableName);
    }

    @PostMapping("/insert")
    public void insertValues(@RequestBody InsertValuesRequest request) {
        jdbc.insertValues(request.getTableName(), request.getHeaders(), request.getValues());
    }
}
