package com.chu.service;

import com.chu.entity.User;

import java.util.List;

/**
 * 用户业务处理接口
 */
public interface UserService {
    List<User> list(int limit, int offset, User user);

    User getUser(String userId);

    User save(User user) throws Exception;

    long count(User user);

    long delete(List<String> ids) throws Exception;

    User getUserByUsername(String username);
}
