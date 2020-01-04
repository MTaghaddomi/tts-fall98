package ir.ac.kntu.controller;

import ir.ac.kntu.service.ExerciseFileService;
import ir.ac.kntu.util.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin
@RequestMapping("/file/exercise")
public class ExerciseFileController {
    @Autowired
    private UserTokenUtil tokenUtil;

    @Autowired
    private ExerciseFileService exerciseFileService;

    @PostMapping("/alaki")
    public String alaki(
            @RequestParam("file") MultipartFile file) throws IOException {

        String fileId = exerciseFileService.alaki(file);

        return fileId;
    }

    @GetMapping("/alaki")
    public void alakiSend(HttpServletResponse response) throws IOException {
        String fileName = exerciseFileService.alaki(response.getOutputStream());

//        response.setContentType("application/text");
//        response.setContentType("application/picture");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
//        response.setContentType("application/pdf");////todo
//        response.setContentType(MediaType.APPLICATION_PDF);////todo
//        response.addHeader(new HttpHeaders());
        response.addHeader("Content-Disposition",
                "attachment; filename=" + fileName);
    }
}
