package com.zkrallah.students_api.service.storage;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile multipartFile);
}
