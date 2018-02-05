package com.chu.dao;

import com.chu.entity.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDAO extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor {
    User findByUsername(String username);
}
