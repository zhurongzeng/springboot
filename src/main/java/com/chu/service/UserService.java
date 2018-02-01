package com.chu.service;

import com.alibaba.fastjson.JSONObject;
import com.chu.po.User;

import java.util.List;

/**
 * 用户业务处理接口
 */
public interface UserService {
    List<User> list(int limit, int offset, JSONObject params);

    User getUser(String userId);

    User save(User user) throws Exception;

    long count(JSONObject params);

    long delete(List<String> ids) throws Exception;

    User getUserByUsername(String username);
}
