package com.capstone.datamate.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.datamate.Entity.DatabaseEntity;

@Repository
public interface DatabaseRepository extends JpaRepository<DatabaseEntity, Integer>{
    
    List<DatabaseEntity> findByUserUserId(int userId);
    DatabaseEntity findByDatabaseNameAndUserUserId(String name, int id);
}
