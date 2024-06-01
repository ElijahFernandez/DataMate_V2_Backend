package com.capstone.datamate.Service;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.datamate.Entity.TemplateEntity;
import com.capstone.datamate.Repository.TemplateRepository;



@Service
public class TemplateService {
    @Autowired
    TemplateRepository tempRepo;

    public TemplateEntity store(MultipartFile template) throws IOException {
        String tempName = StringUtils.cleanPath(template.getOriginalFilename());
        TemplateEntity Template = new TemplateEntity(tempName, template.getBytes());
    
        return tempRepo.save(Template);
    }

    public TemplateEntity getTemplate(int id) {
        return tempRepo.findById(id).get();
      }
      
    public Stream<TemplateEntity> getAllTemplates() {
        return tempRepo.findAll().stream();
      }
    
    
    public ResponseEntity<byte[]> downloadFile(int id) {
        TemplateEntity temp = tempRepo.findById(id).orElse(null);
        if (temp != null) {
            temp.setDownloaded(true); 
            tempRepo.save(temp);
            return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + temp.getTemplateName() + "\"")
                .body(temp.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    public List<TemplateEntity> getRecentDownloads(int limit) {
        return tempRepo.findAll()
            .stream()
            .filter(TemplateEntity::isDownloaded)
            .sorted(Comparator.comparing(TemplateEntity::getTemplateId).reversed())
            .limit(limit)
            .collect(Collectors.toList());
    }



}
