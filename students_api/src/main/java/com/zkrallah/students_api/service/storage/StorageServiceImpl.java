package com.zkrallah.students_api.service.storage;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.zkrallah.students_api.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageServiceImpl implements StorageService {

    private final UserService userService;
    private static final String CREDENTIALS_FILE_PATH = "spring-students-system-firebase-adminsdk-e56uv-78baed1398.json";

    private String uploadFile(File file, String fileName) throws IOException {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CREDENTIALS_FILE_PATH)) {
            if (inputStream == null) {
                throw new IOException("Credentials file not found: " + CREDENTIALS_FILE_PATH);
            }

            Credentials credentials = GoogleCredentials.fromStream(inputStream);
            Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();

            BlobId blobId = BlobId.of("spring-students-system.appspot.com", fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();

            storage.create(blobInfo, Files.readAllBytes(file.toPath()));

            String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/spring-students-system.appspot.com/o/%s?alt=media";
            return String.format(DOWNLOAD_URL, URLEncoder.encode(fileName, StandardCharsets.UTF_8));
        }
    }

    private File convertToFile(MultipartFile multipartFile, String fileName) throws IOException {
        File tempFile = new File(fileName);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(multipartFile.getBytes());
        }
        return tempFile;
    }

    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    @Async
    @Override
    public CompletableFuture<String> upload(MultipartFile multipartFile, Long userId) {
        try {
            log.info("Uploading on " + Thread.currentThread().getName() + " for userId " + userId.toString());
            String fileName = multipartFile.getOriginalFilename();
            if (fileName == null) {
                throw new IllegalArgumentException("File name is null");
            }
            fileName = UUID.randomUUID().toString().concat(getExtension(fileName));

            File file = convertToFile(multipartFile, fileName);
            String url = uploadFile(file, fileName);
            if (!file.delete()) {
                log.warn("Failed to delete temporary file: {}", file.getName());
            }

            userService.updateUserPhoto(userId, url);

            return CompletableFuture.completedFuture(url);
        } catch (IOException e) {
            log.error("Failed to upload image to Firebase Storage", e);
            return CompletableFuture.completedFuture("Image couldn't upload, Something went wrong");
        } catch (IllegalArgumentException e) {
            log.error("Invalid file name", e);
            return CompletableFuture.completedFuture("Invalid file name");
        }
    }
}
