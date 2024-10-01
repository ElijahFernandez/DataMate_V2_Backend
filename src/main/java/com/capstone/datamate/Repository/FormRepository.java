package com.capstone.datamate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.capstone.datamate.Entity.FormEntity;

@Repository
public interface FormRepository extends JpaRepository<FormEntity, Integer> {

    // Find all forms by userId
    List<FormEntity> findByUserId(int userId);

    // Find a form by formName and userId
    FormEntity findByFormNameAndUserId(String formName, int userId);

    // Query to find dbName by formId
    @Query("SELECT f.dbName FROM FormEntity f WHERE f.formId = :formId")
    String findDbNameByFormId(@Param("formId") int formId);

    // Query to find all headers by formId
    @Query("SELECT f.headers FROM FormEntity f WHERE f.formId = :formId")
    String findHeadersByFormId(@Param("formId") int formId);
}
