package com.chu.controller;

import com.chu.dao.UserDAO;
import com.chu.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDAO userDAO;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String userList() {
        return "/user/list";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        return "/user/add";
    }

    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    public String addUser(User user) {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String password = user.getPassword();
        password = encoder.encode(password);
        user.setPassword(password);
        userDAO.save(user);
        return "redirect:/user/add";
    }
}
