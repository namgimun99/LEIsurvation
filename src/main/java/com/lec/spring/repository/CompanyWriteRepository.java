package com.lec.spring.repository;

import com.lec.spring.domain.CompanyWrite;

public interface CompanyWriteRepository {
    int save(CompanyWrite companyWrite);

    CompanyWrite findById(long id);

}
