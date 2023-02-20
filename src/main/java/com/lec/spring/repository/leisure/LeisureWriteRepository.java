package com.lec.spring.repository.leisure;

import com.lec.spring.domain.leisure.LeisureWrite;

import java.util.List;

public interface LeisureWriteRepository {
    int save(LeisureWrite leisureWrite);

    LeisureWrite findById(long id);

    List<LeisureWrite> findprice();

    List<LeisureWrite> findstar();
}
