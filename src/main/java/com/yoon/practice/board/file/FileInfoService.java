package com.yoon.practice.board.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileInfoService {
    @Autowired
    private FileInfoRepository fileInfoRepository;

    public FileInfo save(FileInfo fileInfo){
        return fileInfoRepository.save(fileInfo);
    }
}
