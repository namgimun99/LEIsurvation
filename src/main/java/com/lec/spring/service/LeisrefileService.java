package com.lec.spring.service;

import com.lec.spring.domain.LeisureFileDTO;
import com.lec.spring.repository.LeisureFileRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;

public class LeisrefileService {
    private LeisureFileRepository leisureFileRepository;

    @Autowired
    public LeisrefileService(SqlSession sqlSession){
        leisureFileRepository = sqlSession.getMapper(LeisureFileRepository.class);
    }

    public LeisureFileDTO findById(Long id) {return leisureFileRepository.findById(id);}
}
