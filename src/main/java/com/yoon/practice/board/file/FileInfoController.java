package com.yoon.practice.board.file;

import com.yoon.practice.board.DataResponse;
import com.yoon.practice.board.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/file")
public class FileInfoController {
    @Autowired
    private FileInfoService fileInfoService;

    @GetMapping
    public void getFileData(){

    }

    @PostMapping(value = "/upload")
    public ResponseEntity<DataResponse> uploadFile(MultipartFile file, String boardCode) throws IOException {
        FileInfo newFile = new FileInfo();
        if(!file.isEmpty()){
            newFile.setFileName(file.getOriginalFilename());
            newFile.setFileExtention(file.getContentType());
        }

        DataResponse dataResponse = new DataResponse();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        dataResponse.setStatus(StatusEnum.OK.statusCode);
        dataResponse.setMessage(StatusEnum.OK.code);
        dataResponse.setData(newFile);

        return new ResponseEntity<>(dataResponse, httpHeaders, HttpStatus.OK);
    }
}
