package com.lec.spring.repository;


import com.lec.spring.domain.ReserveWrite;
import com.lec.spring.domain.LeisureWrite;
import com.lec.spring.domain.User;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ReserveRepository {
    int save(ReserveWrite reserveWrite);

    LeisureWrite findLeis(Long id);

    ReserveWrite findById(Long id);

    int delete(ReserveWrite reserveWrite);

    int update(ReserveWrite reserveWrite);

    List<ReserveWrite> findAll(Long user_id);

    List<LeisureWrite> findAllCompany(Long company_id);


    List<User> findAll2(Long user_id);

}
