package com.chu.dao;

import com.chu.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDAO extends JpaRepository<User, String> {
    User findByUsername(String username);
}
