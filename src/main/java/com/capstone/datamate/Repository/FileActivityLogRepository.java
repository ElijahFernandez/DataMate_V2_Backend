package com.capstone.datamate.Repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.capstone.datamate.Entity.FileActivityLogEntity;

public interface FileActivityLogRepository extends JpaRepository<FileActivityLogEntity, Integer>{
	
	FileActivityLogEntity getFileActivityLogsByLogId(int logId);
	List<FileActivityLogEntity> findByUserUserId(int userId);

}
