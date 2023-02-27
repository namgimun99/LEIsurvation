package com.lec.spring.repository;

import com.lec.spring.domain.CompanyWrite;


public interface CompanyWriteRepository {
    int save(CompanyWrite companyWrite);

    CompanyWrite findById(long id);

    int update(CompanyWrite companywrite);

    int delete(CompanyWrite companywrite);

    CompanyWrite findBycompanyname(String companyname);
}
