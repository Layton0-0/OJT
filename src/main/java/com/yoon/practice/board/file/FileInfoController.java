package com.yoon.practice.board.file;

import com.yoon.practice.board.Board;
import com.yoon.practice.board.BoardService;
import com.yoon.practice.board.DataResponse;
import com.yoon.practice.board.StatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/file")
public class FileInfoController {
    @Autowired
    private FileInfoService fileInfoService;

    @Autowired
    private BoardService boardService;

    // 저장 기본 경로(application.properties에 저장해놓은 경로)
    private String baseDir = "C:\\Temp\\spring_file";

    // 이미지 경로
    private String imagePath = "\\image\\";

    // 파일 정보 읽어오기
    @GetMapping("/{type}/{file_name}")
    public ResponseEntity<DataResponse> getFileData(@PathVariable("type") String type, @PathVariable("file_name") String fileName){
        String filePath = "\\" + type + "\\" + fileName;
        FileInfo fileInfo = fileInfoService.findByFilePath(filePath);

        DataResponse dataResponse = new DataResponse();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        dataResponse.setStatus(StatusEnum.OK.statusCode);
        dataResponse.setMessage(StatusEnum.OK.code);
        dataResponse.setData(fileInfo);

        return new ResponseEntity<>(dataResponse, httpHeaders, HttpStatus.OK);
    }

    // MultipartFile 파라미터 이름은 request의 body의 key이름과 같아야 함.
    @PostMapping(value = "/upload")
    public ResponseEntity<DataResponse> uploadFile(MultipartFile file, String boardCode) throws IOException {
        DataResponse dataResponse = new DataResponse();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        dataResponse.setStatus(StatusEnum.OK.statusCode);
        dataResponse.setMessage(StatusEnum.OK.code);

        FileInfo newFile = fileInfoService.findByFilePath(imagePath + file.getOriginalFilename());
        if(newFile != null) {
            dataResponse.setData(newFile);
            return new ResponseEntity<>(dataResponse, httpHeaders, HttpStatus.OK);
        }
        newFile = new FileInfo();

        if(!file.isEmpty()){
            newFile.setFileName(file.getOriginalFilename());
            newFile.setFileExtention(file.getContentType());
            newFile.setFilePath(imagePath + file.getOriginalFilename());
            // fileInfo에 boardCode 같이 저장 -> boardCode 기준으로 Board 탐색 후 그 Board를 fileInfo에 저장
            Board board = boardService.getReferenceById(boardCode);
            newFile.setBoard(board);

            File path = new File(baseDir + imagePath +file.getOriginalFilename());
            // 경로가 없을 경우 만들기
            if(!path.exists()){
                path.mkdirs();
            }
            // application.properties에 저장해놓은 경로에 실제 파일 저장
            file.transferTo(path);
            newFile = fileInfoService.save(newFile);

        }


        dataResponse.setData(newFile);

        return new ResponseEntity<>(dataResponse, httpHeaders, HttpStatus.OK);
    }
}
