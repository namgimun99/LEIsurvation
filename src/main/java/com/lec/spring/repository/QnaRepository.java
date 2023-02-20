package com.lec.spring.repository;

import com.lec.spring.domain.Qna;
import com.lec.spring.domain.ReserveWrite;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QnaRepository {

    List<Qna> findAll();

    int save(Qna qna);

    Qna findById(Long id);

    int update(Qna qna);

    int delete(Qna qna);


    //페이징
    // from 부터 rows 개 만큼 select
    List<Qna> selectFromRow(int from,int rows);

    //전체 글의 갯수
    int countAll();


}
