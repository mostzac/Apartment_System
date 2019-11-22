package io.ascending.training.controller;

import io.ascending.training.service.FileService;
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

@RestController
@RequestMapping(value = {"/files"})
public class FileController {
    @Autowired
    private FileService fileService;

    @RequestMapping(value = {"/upload"},method = RequestMethod.POST,consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity uploadFile(@RequestParam("file")MultipartFile file){
        String msg = String.format("The file name = %s,size = %d could not be uploaded.",file.getOriginalFilename(),file.getSize());

        File f = new File("/home/runlai/Downloads/log4j.properties");
        try {
            file.transferTo(f);
            fileService.putObject(f);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);
    }

    @RequestMapping(value = {"/getURL"},method = RequestMethod.GET, produces = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity getFile(@RequestParam("filename")String fileName){
        String msg = String.format("The file name = %s is with url = %s",fileName,fileService.getObjectURL(fileName));

        return ResponseEntity.status(HttpServletResponse.SC_OK).body(msg);

    }

}
