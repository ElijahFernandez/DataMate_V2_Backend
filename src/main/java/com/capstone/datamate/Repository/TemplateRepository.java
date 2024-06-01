package com.capstone.datamate.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.capstone.datamate.Entity.TemplateEntity;

@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity,Integer> {
    
}