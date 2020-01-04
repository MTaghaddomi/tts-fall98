package ir.ac.kntu.service;

import ir.ac.kntu.util.FileTransitionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ExerciseFileService {
    @Value("${spring.servlet.multipart.location}")
    private String prefixAddress;

    public String alaki(MultipartFile file) throws IOException {
        String filePath = "F:\\term5_981\\SystemAnalyzeAndDesign\\project\\backend\\tts-fall98\\src\\main\\resources\\dynamic\\temp\\" + file.getOriginalFilename();
        //todo: restrict file format
        Path path = Paths.get(filePath);

        if (path != null) {
            System.out.println("yeessss");


        } else {
            System.out.println("noooooo");
        }

        System.out.println("1111->hereeeee path: " + path.toString());
        System.out.println("checkkkkkkkk" + Files.isWritable(path));
        Files.write(path, file.getBytes());

        System.out.println("1111->hereeeee writes complete");

        return path.toString();
    }

    public String alaki(OutputStream outputStream) throws IOException {
//        String fileName = "alaki.txt";
        String fileName = "alaki.png";
        String filePath = "F:\\term5_981\\SystemAnalyzeAndDesign\\project\\backend\\tts-fall98\\src\\main\\resources\\dynamic\\temp\\" + fileName;
        Path path = Paths.get(filePath);

        if (path != null) {
            System.out.println("yeessss");
        } else {
            System.out.println("noooooo");
        }

        System.out.println("2222->hereeeee path: " + path.toString());

        Files.copy(path, outputStream);
        outputStream.flush();

        System.out.println("2222->hreeeeee writes complete");

//        String fileName = fileTransitionUtil.getExistingFileNames(filePath)[0];//null:|
        return "springe koofti";
    }
}
