package io.ascending.training.controller;

import io.ascending.training.service.FileService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/api/files"})
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = {"/upload"}, method = RequestMethod.POST, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadFile(@RequestParam("file") MultipartFile file) {
        //MultipartFile file is the selected file from Front-end, when the file is being transferred, the file is tear down as multipart,
        // when the transfer is ready, the server will create a File with /homedir/filename.extension in the server side, and the put it onto the s3
        String msg = String.format("The file name = %s,size = %d is uploaded.", file.getOriginalFilename(), file.getSize());
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String homeDir = System.getProperty("catalina.base") !=null ? System.getProperty("catalina.base"):"/tmp/";
        String s3Key = FilenameUtils.getBaseName(file.getOriginalFilename()+"_"+ UUID.randomUUID()+"."+extension);


        File f = new File(homeDir+s3Key);
        try {
            file.transferTo(f);
            msg = msg.concat(" URL = "+ fileService.putObject(f));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);
    }

    @RequestMapping(value = {"/getURL"}, method = RequestMethod.GET, produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity getFile(@RequestParam("filename") String fileName) {
        String msg = String.format("The file name = %s is with url = %s", fileName, fileService.getObjectURL(fileName));

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);

    }

}
