package ir.ac.kntu.controller;

import ir.ac.kntu.exception.NotEnoughAccessLevelException;
import ir.ac.kntu.util.FileTransitionUtil;
import ir.ac.kntu.util.UserTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

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

    @Value("${spring.servlet.multipart.location}")
    private String prefixAddress;

    private final static String FILE_SEPARATOR = File.separator;

    @GetMapping("/{address}")
    public void sendFileToUser(
            @PathVariable(name = "address") String address,
            HttpServletResponse response) throws IOException {

        String requesterUsername = tokenUtil.token2Username();

        String fileName = copyFileTo(requesterUsername, address, response.getOutputStream());

        response.addHeader("Content-Disposition",
                "attachment; filename=" + fileName);
    }

    @GetMapping("/{username}/{classId}/answers/{fileId}")
    public void sendFileToUser(
            @PathVariable(name = "username") String username,
            @PathVariable(name = "classId") String classId,
            @PathVariable(name = "fileId") String fileId,
            HttpServletResponse response) throws IOException {

        String requesterUsername = tokenUtil.token2Username();

        String address = getAddress(username, classId, "answers", fileId);

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

    private String getAddress(String userId, String classId, String part, String folderId){
        return prefixAddress + FILE_SEPARATOR + "users" + FILE_SEPARATOR + userId +
                FILE_SEPARATOR + "classes" + FILE_SEPARATOR + classId + FILE_SEPARATOR +
                part + FILE_SEPARATOR + folderId;
    }
}
