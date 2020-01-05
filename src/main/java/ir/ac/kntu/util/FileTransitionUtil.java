package ir.ac.kntu.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileTransitionUtil {
    private final static String FILE_SEPARATOR = File.separator;

    public String saveFile(MultipartFile file, String address) throws IOException {
        String fileId = generateUniqueIdInDirectory(address);
        String folderAddress = address + FILE_SEPARATOR + fileId;
        Path folderPath = Paths.get(folderAddress);

        Files.createDirectories(folderPath);

        Path filePath = Paths.get(folderAddress + FILE_SEPARATOR + file.getOriginalFilename());

        Files.write(filePath, file.getBytes());

        return fileId;
    }

    public void copyFile(String address, OutputStream outputStream)
            throws IOException {

        Path filePath = Paths.get(address);
        if(! Files.exists(filePath)){
            throw new FileNotFoundException();
        }

        File fileDirectory = new File(address);
        if(! fileDirectory.isDirectory()){
            throw new FileNotFoundException();
        }
        File[] files = fileDirectory.listFiles();
        if(files != null){
            for(File file : files){
                Files.copy(file.toPath(), outputStream);
            }
        }

        outputStream.flush();
    }

    private String generateUniqueIdInDirectory(String directoryPath){
        String uniqueId;
        do{
            uniqueId = UUID.randomUUID().toString();
        }while(isExist(directoryPath + FILE_SEPARATOR + uniqueId));

        return uniqueId;
    }

    private boolean isExist(String directoryPath){
        return Files.exists(Paths.get(directoryPath));
    }
}
