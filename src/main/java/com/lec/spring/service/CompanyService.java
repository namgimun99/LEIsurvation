package com.lec.spring.service;


import com.lec.spring.domain.Authority;
import com.lec.spring.domain.CompanyWrite;
import com.lec.spring.domain.LeisureWrite;
import com.lec.spring.repository.AuthorityRepository;
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
    private AuthorityRepository authorityRepository;

    @Autowired
    public CompanyService(SqlSession sqlSession) {
        companyWriteRepository = sqlSession.getMapper(CompanyWriteRepository.class);
        authorityRepository = sqlSession.getMapper(AuthorityRepository.class);
    }

    public int companyWrite(CompanyWrite companyWrite) {
        return companyWriteRepository.save(companyWrite);
    }

    public int companyJoin(CompanyWrite companyWrite){

        // 컴퍼니 권한부여
        Authority auth = authorityRepository.findByName("ROLE_COMPANY");

        String user_id = companyWrite.getUser_id();
        Long auth_id = auth.getId();
        authorityRepository.addAuthority(Long.valueOf(user_id), auth_id);

        return 1;
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
