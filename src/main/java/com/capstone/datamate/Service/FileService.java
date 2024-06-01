package com.capstone.datamate.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

// import java.nio.file.Path;
// import java.util.stream.Stream;
import org.springframework.web.multipart.MultipartFile;

import com.capstone.datamate.Entity.FileEntity;

public interface FileService {
        public void init();
      
        public FileEntity updateFile(int id, MultipartFile file) throws IOException;
        public FileEntity getFile(int id);
        public Stream<FileEntity> getAllFiles();
        public List<FileEntity> getDeletedFiles();
        public void permanentlyDeleteFile(int id);
        public void deleteFile(int id);
        public FileEntity restoreFile(int id);
        public List<FileEntity> getFilesByUserId(int userId);
        public List<FileEntity> getDeletedFilesById(int userId);
        FileEntity store(MultipartFile file, int userId) throws IOException;


}
