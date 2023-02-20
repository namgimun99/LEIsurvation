package com.lec.spring.service;

import com.lec.spring.domain.Authority;
import com.lec.spring.domain.User;
import com.lec.spring.repository.AuthorityRepository;
import com.lec.spring.repository.UserRepository;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private AuthorityRepository authorityRepository;

    @Autowired
    public UserService(SqlSession sqlSession){
        userRepository = sqlSession.getMapper(UserRepository.class);
        authorityRepository = sqlSession.getMapper(AuthorityRepository.class);
        System.out.println(getClass().getName() + "() 생성");
    }

    // 해당 아이디의 유저정보 읽기
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    // 회원 중복 체크
    public boolean isExist(String username){
        User user = findByUsername(username);
        return (user != null) ? true : false;
    }

    // 회원 등록
    public int join(User user){
        user.setUsername(user.getUsername().toUpperCase());
        user.setPassword(passwordEncoder.encode(user.getPassword()));   // 비밀번호는 암호화해서 db에 저장
        userRepository.save(user);

        // 새로운 회원은 기본적으로 USER권한 부여
        Authority auth = authorityRepository.findByName("ROLE_USER");

        Long user_id = user.getId();
        Long auth_id = auth.getId();
        authorityRepository.addAuthority(user_id, auth_id);

        return 1;
    }

    //  특정 유저가 갖고 있는 권한들
    public List<Authority> selectAuthoritiesById(Long id){
        User user = userRepository.findById(id);
        return authorityRepository.findByUser(user);
    }
}
