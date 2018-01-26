package com.chu.dao;

import com.chu.po.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDAO extends PagingAndSortingRepository<User, String> {
    User findByUsername(String username);

    User findById(long userId);
}
