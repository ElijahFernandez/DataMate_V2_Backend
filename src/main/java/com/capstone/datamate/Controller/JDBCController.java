package com.capstone.datamate.Controller;

//import com.capstone.datamate.Entity.InsertValuesRequest;
import com.capstone.datamate.Entity.RequestEntities;
//import com.capstone.datamate.Entity.UpdateValuesRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.capstone.datamate.Config.JdbcTemplateImpl;
import com.capstone.datamate.Entity.SqlRequestEntity;

import java.util.List;
import java.util.Map;

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
    public void insertValues(@RequestBody RequestEntities.InsertValuesRequest request) {
        jdbc.insertValues(request.getTableName(), request.getHeaders(), request.getValues());
    }

    // Update values
    @PostMapping("/modify")
    public void updateValues(@RequestBody RequestEntities.UpdateValuesRequest request) {
        jdbc.updateValues(request.getTableName(), request.getHeaders(), request.getValues(), request.getConditions());
    }

    @GetMapping("/check-id")
    public boolean checkIfIdExists(
            @RequestParam String tableName,
            @RequestParam String idColumn,
            @RequestParam String idValue) {
        return jdbc.checkIfIdExists(tableName, idColumn, idValue);
    }

    @GetMapping("/getAllIds")
    public List<String> getAllIds(@RequestParam String idKeyColumn, @RequestParam String tableName) {
        return jdbc.getAllIds(idKeyColumn,tableName);
    }

    @PostMapping("/getRowData")
    public ResponseEntity<Map<String, Object>> getRowData(@RequestBody RequestEntities.RowDataRequest request) {
        Map<String, Object> rowData = jdbc.getRowData(request.getTableName(), request.getIdColumn(), request.getIdValue());
        if (rowData != null && !rowData.isEmpty()) {
            return ResponseEntity.ok(rowData);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/deleteRow")
    public ResponseEntity<String> deleteRow(@RequestBody RequestEntities.DeleteRowRequest request) {
        boolean deleted = jdbc.deleteRow(request.getTableName(), request.getIdColumn(), request.getIdValue());
        if (deleted) {
            return ResponseEntity.ok("Row deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
