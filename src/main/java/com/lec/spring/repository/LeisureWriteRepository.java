package com.lec.spring.repository;

import com.lec.spring.domain.CompanyWrite;
import com.lec.spring.domain.LeisureWrite;
import com.lec.spring.domain.Review;

import java.util.List;

public interface LeisureWriteRepository {
    int save(LeisureWrite leisureWrite);

    LeisureWrite findById(long id);

    List<LeisureWrite> findprice();

    List<LeisureWrite> findstar();

    int delete(LeisureWrite leisureWrite);

    int update(LeisureWrite leisureWrite);

}