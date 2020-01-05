package ir.ac.kntu.controller;

import ir.ac.kntu.exception.AnswerNotExistedException;
import ir.ac.kntu.exception.NotEnoughAccessLevelException;
import ir.ac.kntu.model.AnswerSubmission;
import ir.ac.kntu.util.FileTransitionUtil;
import ir.ac.kntu.util.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/download/file")
public class FileController {
    @Autowired
    private UserTokenUtil tokenUtil;

    @Autowired
    private FileTransitionUtil fileUtil;

    @GetMapping("/{address}")
    public void sendFileToUser(
            @PathVariable(name = "address") String address,
            HttpServletResponse response) throws IOException {

        String requesterUsername = tokenUtil.token2Username();

        String fileName = copyFileTo(requesterUsername, address, response.getOutputStream());

        response.addHeader("Content-Disposition",
                "attachment; filename=" + fileName);
    }

    private String copyFileTo(String requesterUsername, String fileAddress, OutputStream outputStream)
            throws IOException {
        if(fileAddress.contains(requesterUsername)){
            fileUtil.copyFile(fileAddress, outputStream);
            int size = fileAddress.split(File.separator).length;
            return fileAddress.split(File.separator)[size -1];
        }else{
            throw new NotEnoughAccessLevelException();
        }
    }
}
