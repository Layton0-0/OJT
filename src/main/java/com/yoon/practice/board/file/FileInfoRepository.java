package com.yoon.practice.board.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
    @Query(nativeQuery = true, value =
            "select * from file_info where file_path = :filePath")
    FileInfo findByFilePath(@Param("filePath") String filePath);

}
