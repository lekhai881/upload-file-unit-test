package spring_boot.test070322.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import spring_boot.test070322.models.ResponseProduct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class ImageStorageSevice implements IStorageService {
    private final Path storageFolder = Paths.get("uploads");

    public ImageStorageSevice() {
        try {
            Files.createDirectories(storageFolder);
        } catch (IOException exception) {
            throw new RuntimeException("Không thể khởi tạo", exception);
        }
    }

    private boolean isImageFile(MultipartFile file) {
//        Cài đăt <dependency> FileNameUtils
        String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
        return Arrays.asList(new String[]{"png", "jpg", "jpeg", "bmp"})
                .contains(fileExtension.trim().toLowerCase());
    }

    @Override
    public ResponseProduct storeFile(MultipartFile[] file) {
        List < String > url = new ArrayList <>();
        try {
            for (int i = 0; i < file.length; i++) {
                if (file[i].isEmpty()) {
                    return new ResponseProduct("False", "Chưa chọn File", "");
                }
                //kiểm tra xem có là file ảnh không ?
                if (!isImageFile(file[i])) {
                    return new ResponseProduct("False", "Vui lòng chọn đúng loại file ảnh (jpg,png,jpeg)!", "");
                }
                // Yêu cầu file ảnh phải nhỏ hơn 5mb
                try {
                        float sizeImg = file[i].getSize() / 1_000_000.0f;
                        System.out.println("xem size: " + sizeImg);
                        if (sizeImg > 1.0f) {
                            return new ResponseProduct("false", "File ảnh không vượt quá 1mb!", "");
                        }
                } catch (Exception e) {
                    e.printStackTrace();
                }
//            Tạo tên random cho file ảnh khi gửi lên:
                String fileExtension = FilenameUtils.getExtension(file[i].getOriginalFilename());
                String generatedFileName = UUID.randomUUID().toString().replace("-", "");
                generatedFileName = generatedFileName + "." + fileExtension;
                Path destinationFilePath = this.storageFolder.resolve(Paths.get(generatedFileName)).normalize().toAbsolutePath();
                if (!destinationFilePath.getParent().equals(this.storageFolder.toAbsolutePath())) {
                    throw new RuntimeException("Không thể lưu trữ tệp bên ngoài thư mục hiện tại!");
                }
//            Coppy file vừa tạo ra vào destinationFilePath
                try (InputStream inputStream = file[i].getInputStream()) {
                    Files.copy(inputStream, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
                }
                url.add("http://localhost:8080/api/FileUpload/" + generatedFileName);
            }
        } catch (IOException exception) {
            throw new RuntimeException("Lỗi file!", exception);
        }
        return new ResponseProduct("ok", "Upload file thành công!", url);
    }

    @Override
    public Stream < Path > loadAll() {
        return null;
    }

    @Override

    // Đọc dữ liệu từng file và trả về danh sách các byte
    public byte[] readFileContent(String fileName) {
        try {
            Path file = storageFolder.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                byte[] bytes = StreamUtils.copyToByteArray(resource.getInputStream());
                return bytes;
            }
            else {
                throw new RuntimeException(
                        "Could not read file: " + fileName);
            }
        }
        catch (IOException exception) {
            throw new RuntimeException("Could not read file: " + fileName, exception);
        }
    }

    @Override
    public void deleteAllFiles() {

    }




}
