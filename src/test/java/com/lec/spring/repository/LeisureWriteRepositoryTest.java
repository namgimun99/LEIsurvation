package com.lec.spring.repository;

import com.lec.spring.domain.LeisureWrite;
import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LeisureWriteRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void test1(){
        LeisureWriteRepository leisureWriteRepository = sqlSession.getMapper(LeisureWriteRepository.class);

    }
    @Test
    void selectAvgStar() {
        LeisureWriteRepository leisureWrite = sqlSession.getMapper(LeisureWriteRepository.class);
        System.out.println(leisureWrite.selectAvgStar(1L));
        System.out.println("테스트 완료");
    }

}