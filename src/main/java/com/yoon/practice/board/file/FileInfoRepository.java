package com.yoon.practice.board.file;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, Long> {

}
