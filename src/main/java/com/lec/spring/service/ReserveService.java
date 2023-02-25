package com.lec.spring.service;


import com.lec.spring.domain.ReserveWrite;
import com.lec.spring.domain.LeisureWrite;
import com.lec.spring.repository.ReserveRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReserveService {

    private ReserveRepository reserveRepository;
//    private UserRepoistory userRepoistory;  ******

    @Autowired
    public ReserveService(SqlSession sqlSession) {
        reserveRepository = sqlSession.getMapper(ReserveRepository.class);
//        userRepoistory = sqlSession.getMapper(UserRepository.class);  ******
        System.out.println("ReserveService() 생성");
    }
    public ReserveService(){
        System.out.println("ReserveService 생성");
    }


    public int write(ReserveWrite reserveWrite){
//        User user = U.getLoggedUser();    ********** 현재 로그인한 작성자 정보
//        user = userRepository.findById(user_id.getId());   *****
//        reserveWrite.setUser(user_id); 글 작성자   *******
        return reserveRepository.save(reserveWrite);
    }





    @Transactional
    public List<LeisureWrite> findLeis(Long id) {

        List<LeisureWrite> list = new ArrayList<>();
        LeisureWrite leisureWrite = reserveRepository.findLeis(id);

        if (leisureWrite != null) {
            list.add(leisureWrite);
        }
        return list;

//        return reserveRepository.findLeis(id);
    }
    @Transactional
    public List<ReserveWrite> detail(Long id) {
        List<ReserveWrite> list = new ArrayList<>();
        ReserveWrite reserveWrite = reserveRepository.findById(id);

        if (reserveWrite != null) {
            list.add(reserveWrite);
        }
        return list;

    }

    public int deleteById(Long id){
        int result = 0;
        ReserveWrite reserveWrite = reserveRepository.findById(id);
        if (reserveWrite != null) {
            result = reserveRepository.delete(reserveWrite);
        }

        return result;
    }

    public List<ReserveWrite> selectById(long id) {
        List<ReserveWrite> list = new ArrayList<>();
        ReserveWrite reserveWrite = reserveRepository.findById(id);

        if (reserveWrite != null) {
            list.add(reserveWrite);
        }
        return list;

    }

    public int update(ReserveWrite reserveWrite) {
        return reserveRepository.update(reserveWrite);
    }

    public List<ReserveWrite> list(Long user_id) {
            return reserveRepository.findAll(user_id);
    }

    public List<LeisureWrite> listCompany(Long user_id) {
        return reserveRepository.findAllCompany(user_id);
    }






}
