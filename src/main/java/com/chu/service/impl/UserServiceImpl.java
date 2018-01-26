package com.chu.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chu.dao.UserDAO;
import com.chu.po.User;
import com.chu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDAO.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }
        return user;
    }

    @Override
    public List<User> list(int limit, int offset, JSONObject params) {
        PageRequest pageRequest = new PageRequest(offset / limit, limit, Sort.Direction.DESC, "username");
        List<User> userList = userDAO.findAll(pageRequest).getContent();
        return userList;
    }

    @Override
    public User getUser(String userId) {
        return userDAO.findById(Long.parseLong(userId));
    }

    @Override
    @Transactional
    public User save(User user) throws Exception {
        return userDAO.save(user);
    }

    @Override
    public long count(JSONObject params) {
        return userDAO.count();
    }


    @Override
    @Transactional
    public long delete(List<String> ids) throws Exception {
        List<User> userList = new ArrayList<>();
        for (String id : ids) {
            User user = new User();
            user.setId(Long.parseLong(id));
            userList.add(user);
        }
        userDAO.delete(userList);
        return ids.size();
    }
}
