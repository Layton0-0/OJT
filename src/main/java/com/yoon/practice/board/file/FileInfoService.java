package com.yoon.practice.board.file;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class FileInfoService {
    @Autowired
    private FileInfoRepository fileInfoRepository;

    public FileInfo save(FileInfo fileInfo){
        return fileInfoRepository.save(fileInfo);
    }

    public FileInfo getReferenceById(String fileCode){
        return fileInfoRepository.getReferenceById(fileCode);
    };

    public long count(){
        return fileInfoRepository.count();
    };

    public FileInfo findByFilePath(String filePath){
        return fileInfoRepository.findByFilePath(filePath);
    };


}
