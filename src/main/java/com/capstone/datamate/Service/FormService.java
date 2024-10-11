package com.capstone.datamate.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.capstone.datamate.Entity.FormEntity;
import com.capstone.datamate.Repository.FormRepository;

@Service
public class FormService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    FormRepository formRepo;

    // Save a form to the database
    public FormEntity postForm(FormEntity form) {
        try {
            return formRepo.save(form);
        } catch (DataIntegrityViolationException e) {
            // Handle error when userId does not exist in related table
            throw new RuntimeException("User ID does not exist in tbl_user: " + form.getUserId());
        }
    }

    // Retrieve a form by its ID
    public FormEntity getForm(int formId) {
        return formRepo.findById(formId).orElse(null);
    }

    // Retrieve a form by its name and userId
    public FormEntity getFormByNameAndUserId(String formName, int userId) {
        return formRepo.findByFormNameAndUserId(formName, userId);
    }

    // Retrieve all forms by userId
    public List<FormEntity> getFormsByUser(int userId) {
        return formRepo.findByUserId(userId);
    }

    // Delete a form by its ID
    public String deleteForm(int formId) {
        Optional<FormEntity> formOptional = formRepo.findById(formId);
        if (formOptional.isPresent()) {
            formRepo.delete(formOptional.get());
            return "Form ID number " + formId + " deleted successfully!";
        } else {
            return "Form ID number " + formId + " is NOT found!";
        }
    }

    // Retrieve the dbName by formId
    public String getDbNameByFormId(int formId) {
        return formRepo.findDbNameByFormId(formId);
    }

    // Retrieve the headers by formId
    public String getHeadersByFormId(int formId) {
        return formRepo.findHeadersByFormId(formId);
    }

    public String getCustomSettingsByFormId(int formId) {
        return formRepo.findCustomSettingsByFormID((formId));
    }

    public boolean updateCustomSettingsByFormId(int formId, String newCustomSettings) {
        FormEntity form = formRepo.findById(formId).orElse(null);
        if (form != null) {
            form.setCustomSettings(newCustomSettings);  // Update the customSettings field
            formRepo.save(form);  // Save the updated form
            return true;
        }
        return false;
    }

    public boolean deleteCustomSettingsByFormId(int formId) {
        FormEntity form = formRepo.findById(formId).orElse(null);
        if (form != null) {
            form.setCustomSettings(null);  // Set customSettings to null to "delete" it
            formRepo.save(form);  // Save the updated form
            return true;
        }
        return false;
    }

    // Execute a raw SQL query (e.g., based on report code or custom query)
    public List<Map<String, Object>> executeSQLQuery(String sqlQuery) {
        try {
            // Validate the SQL query before execution
            if (sqlQuery == null || sqlQuery.isEmpty()) {
                throw new IllegalArgumentException("Invalid SQL query provided.");
            }
            return jdbcTemplate.queryForList(sqlQuery);
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
}
