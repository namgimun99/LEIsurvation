package com.lec.spring.service;


import com.lec.spring.domain.CompanyWrite;
import com.lec.spring.domain.LeisureWrite;
import com.lec.spring.repository.CompanyWriteRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CompanyService {
    private CompanyWriteRepository companyWriteRepository;

    @Autowired
    public CompanyService(SqlSession sqlSession) {
        companyWriteRepository = sqlSession.getMapper(CompanyWriteRepository.class);
    }

    public int companyWrite(CompanyWrite companyWrite) {
        return companyWriteRepository.save(companyWrite);
    }

    @Transactional
    public List<CompanyWrite> companyDetail(long id) {
        List<CompanyWrite> list = new ArrayList<>();

        CompanyWrite companyWrite = companyWriteRepository.findById(id);

        if(companyWrite != null){
            list.add(companyWrite);
        }
        return list;
    }
}
