package com.lec.spring.repository;

import org.apache.ibatis.session.SqlSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReserveRepositoryTest {

    @Autowired
    private SqlSession sqlSession;

    @Test
    void save() {
    }

    @Test
    void findLeis() {
    }

    @Test
    void findById() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void findAll2() {
        ReserveRepository reserveRepository = sqlSession.getMapper(ReserveRepository.class);
        System.out.println(reserveRepository.findAll2(1L));
        System.out.println("테스트 완료");
    }
}