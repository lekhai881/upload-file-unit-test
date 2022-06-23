package spring_boot.test070322.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spring_boot.test070322.models.ResponseProduct;
import spring_boot.test070322.services.IStorageService;

@Controller
@RequestMapping("api/FileUpload")
public class FileUploadController {
    //Inject Storage Service here
    @Autowired
    private IStorageService storageService;

    @PostMapping("")
    public ResponseEntity < ResponseProduct > uploadFile(@RequestBody() MultipartFile[] file) {
        System.out.println("Chạy cai nay");
        return ResponseEntity.status(HttpStatus.OK).body(storageService.storeFile(file));
    }

    @GetMapping("/files/{fileName:.+}")

    public ResponseEntity < byte[] > readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

}