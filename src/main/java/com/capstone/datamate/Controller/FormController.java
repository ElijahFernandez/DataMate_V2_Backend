package com.capstone.datamate.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
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

import com.capstone.datamate.Entity.FormEntity;
import com.capstone.datamate.Service.FormService;

@CrossOrigin("http://localhost:3000/")
@RestController
public class FormController {

    @Autowired
    FormService formServ;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Endpoint to create a new form
    @PostMapping("/postForms")
    public FormEntity postForm(@RequestBody FormEntity form) {
        return formServ.postForm(form);
    }

    // Endpoint to retrieve a form by its ID
    @GetMapping("/getForms/{formId}")
    public ResponseEntity<FormEntity> getForm(@PathVariable int formId) {
        FormEntity form = formServ.getForm(formId);
        if (form != null) {
            return ResponseEntity.ok(form);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // Endpoint to retrieve a form by its name and userId
    @GetMapping("/getFormsByNameAndUser")
    public ResponseEntity<FormEntity> getFormByNameAndUserId(@RequestParam String formName, @RequestParam int userId) {
        FormEntity form = formServ.getFormByNameAndUserId(formName, userId);
        if (form != null) {
            return ResponseEntity.ok(form);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    // Endpoint to retrieve all forms created by a specific user
    @GetMapping("/getUserForms/{userId}")
    public List<FormEntity> getUserForms(@PathVariable int userId) {
        return formServ.getFormsByUser(userId);
    }

    // Endpoint to delete a form by its ID
    @DeleteMapping("/deleteForm")
    public String deleteForm(@RequestParam int formId) {
        return formServ.deleteForm(formId);
    }

    // Endpoint to retrieve the database name by form ID
    @GetMapping("/getDbNameByFormId/{formId}")
    public ResponseEntity<?> getDbNameByFormId(@PathVariable int formId) {
        String dbName = formServ.getDbNameByFormId(formId);
        if (dbName != null) {
            return ResponseEntity.ok(dbName);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Database name not found for Form ID: " + formId);
        }
    }

    // Endpoint to retrieve the headers by form ID
    @GetMapping("/getHeadersByFormId/{formId}")
    public ResponseEntity<?> getHeadersByFormId(@PathVariable int formId) {
        String headers = formServ.getHeadersByFormId(formId);
        if (headers != null) {
            return ResponseEntity.ok(headers);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Headers not found for Form ID: " + formId);
        }
    }

    // Endpoint to execute a raw SQL query based on the provided SQL
    @GetMapping("/executeFormQuery")
    public ResponseEntity<?> executeFormQuery(@RequestParam String sqlQuery) {
        try {
            List<Map<String, Object>> result = formServ.executeSQLQuery(sqlQuery);
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
}
