package com.example.toj.service.storage;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class TempFileStorageService implements StorageService{

    @Override
    public String store(MultipartFile file) {
        if(file.isEmpty()){

        }
        String originalFileName = file.getOriginalFilename();
        String extension = "";
        if(originalFileName != null && originalFileName.lastIndexOf(".") != -1){
            extension = originalFileName.substring(originalFileName.lastIndexOf("."));
            extension = extension.toLowerCase();
        }
        UUID uuid = UUID.randomUUID();
        Path dest = Path.of("temp/" + uuid.toString() + extension);

        try {
            Files.copy(file.getInputStream(), dest.toAbsolutePath(), StandardCopyOption.REPLACE_EXISTING);
            return uuid.toString() + extension;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void copyToAvatar(String fileUuid, String outputId){
        File input = new File("temp/" + fileUuid);

        try {
            BufferedImage bufferedImage = ImageIO.read(input.getAbsoluteFile());
            File outputFile = new File("D:/toj/avatar/" + outputId + ".png");
            ImageIO.write(bufferedImage, "PNG", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
