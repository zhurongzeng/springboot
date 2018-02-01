package com.chu.controller;

import com.alibaba.fastjson.JSONObject;
import com.chu.common.utils.ReturnMessageUtil;
import com.chu.dto.ReturnMsg;
import com.chu.po.User;
import com.chu.service.UserService;
import com.chu.common.utils.UpdateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public String view(@PathVariable("index") String index, String id, Model model) {
        if ("edit".equals(index)) {
            User user = userService.getUser(id);
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
            retMsg = ReturnMessageUtil.returnMessage(userService.save(user) != null, null);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("保存用户异常！\n", e);
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
        User targetUser = userService.getUser(user.getId());
        UpdateUtil.copyNonNullProperties(user, targetUser);
        try {
            retMsg = ReturnMessageUtil.returnMessage(userService.save(targetUser) != null, null);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("修改用户异常！\n", e);
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
            retMsg = ReturnMessageUtil.returnMessage(count > 0, count);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("删除用户异常！\n", e);
        }
        return retMsg;
    }

    @RequestMapping(value = "/service/validate", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject validate(@RequestParam String username) {
        JSONObject result = new JSONObject();
        User user = userService.getUserByUsername(username);
        if (user != null) {
            result.put("valid", false);
        } else {
            result.put("valid", true);
        }
        return result;
    }
}
