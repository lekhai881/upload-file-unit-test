package spring_boot.test070322.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import spring_boot.test070322.models.ResponseProduct;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface IStorageService {
    public ResponseProduct storeFile(MultipartFile[] file);
    public Stream< Path >loadAll();
    public  byte[] readFileContent(String fileName);
    public void deleteAllFiles();

}
