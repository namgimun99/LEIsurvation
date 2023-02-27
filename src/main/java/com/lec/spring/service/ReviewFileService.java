package com.lec.spring.service;

import com.lec.spring.domain.ReviewFileDTO;
import com.lec.spring.repository.ReviewFileRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewFileService {

    private ReviewFileRepository fileRepository;

    @Autowired
    public ReviewFileService(SqlSession sqlSession){
        fileRepository = sqlSession.getMapper(ReviewFileRepository.class);
        System.out.println("ReviewFileService() 생성");
    }

    public ReviewFileDTO findById(Long id) {
        return fileRepository.findById(id);
    }
}
