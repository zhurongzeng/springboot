package com.chu.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chu.dao.UserDAO;
import com.chu.dto.ReturnMsg;
import com.chu.po.User;
import com.chu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 用户Controller类
 *
 * @author zhurongzeng
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 页面展示
     *
     * @param index
     * @return
     */
    @RequestMapping(value = "/view/{index}", method = RequestMethod.GET)
    public String view(@PathVariable("index") String index, String userId, Model model) {
        if ("edit".equals(index)) {
            User user = userService.getUser(userId);
            model.addAttribute("user", user);
        }
        return "/user/" + index;
    }

    /**
     * 查询列表
     *
     * @param limit
     * @param offset
     * @param queryParams
     * @return
     */
    @RequestMapping(value = "/service/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(int limit, int offset, String queryParams) {
        JSONObject result = new JSONObject();
        JSONObject params = JSONObject.parseObject(queryParams);
        List<User> userList = userService.list(limit, offset, params);
        long count = userService.count(params);
        result.put("total", count);
        result.put("rows", userList);
        return result;
    }

    /**
     * 新增
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/service/add", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsg add(User user) {
        ReturnMsg retMsg = new ReturnMsg();
        try {
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            String password = user.getPassword();
            password = encoder.encode(password);
            user.setPassword(password);
            if (userService.save(user) != null) {
                retMsg.setRetCode("0000");
                retMsg.setRetMsg("保存成功");
            } else {
                retMsg.setRetCode("0001");
                retMsg.setRetMsg("保存失败");
            }
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
        }
        return retMsg;
    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/service/edit", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsg edit(User user) {
        ReturnMsg retMsg = new ReturnMsg();
        try {
            if (userService.save(user) != null) {
                retMsg.setRetCode("0000");
                retMsg.setRetMsg("修改成功");
            } else {
                retMsg.setRetCode("0001");
                retMsg.setRetMsg("修改失败");
            }
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
        }
        return retMsg;
    }

    /**
     * 删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/service/delete", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsg delete(@RequestBody List<String> ids) {
        ReturnMsg retMsg = new ReturnMsg();
        try {
            long count = userService.delete(ids);
            if (count > 0) {
                retMsg.setRetCode("0000");
                retMsg.setRetMsg("删除成功");
                retMsg.setRetData(count);
            } else {
                retMsg.setRetCode("0001");
                retMsg.setRetMsg("删除失败");
            }
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("********** 删除用户异常！ **********\n", e);
        }
        return retMsg;
    }
}
