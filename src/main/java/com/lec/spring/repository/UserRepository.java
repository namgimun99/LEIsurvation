package com.lec.spring.repository;

import com.lec.spring.domain.User;

public interface UserRepository {
    // 해당 id의 user 리턴
    User findById(Long id);

    // 해당 username의 user 리턴
    User findByUsername(String username);

    // 새로운 유저 등록
    int save(User user);
    int c_update(User user);
}
