package com.chu.controller;

import com.alibaba.fastjson.JSONObject;
import com.chu.common.utils.ContextHolderEx;
import com.chu.common.utils.ReturnMessageUtil;
import com.chu.common.utils.UpdateUtil;
import com.chu.dto.ReturnMsg;
import com.chu.entity.Menu;
import com.chu.entity.User;
import com.chu.service.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据菜单Controller类
 *
 * @author zhurongzeng
 */
@Slf4j
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 页面展示
     *
     * @param index
     * @return
     */
    @RequestMapping(value = "/view/{index}", method = RequestMethod.GET)
    public String view(@PathVariable("index") String index, String id, Model model) {
        if ("edit".equals(index)) {
            Menu menu = menuService.getMenu(id);
            model.addAttribute("menu", menu);
        }
        return "/menu/" + index;
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
        Menu menu = JSONObject.parseObject(queryParams, Menu.class);
        List<Menu> menuList = menuService.list(limit, offset, menu);
        long count = menuService.count(menu);
        result.put("total", count);
        result.put("rows", menuList);
        return result;
    }

    /**
     * 新增
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/service/add", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsg add(Menu menu) {
        ReturnMsg retMsg = new ReturnMsg();
        try {
            retMsg = ReturnMessageUtil.returnMessage(menuService.save(menu) != null, null);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("保存菜单异常！\n", e);
        }
        return retMsg;
    }

    /**
     * 修改
     *
     * @param menu
     * @return
     */
    @RequestMapping(value = "/service/edit", method = RequestMethod.POST)
    @ResponseBody
    public ReturnMsg edit(Menu menu) {
        ReturnMsg retMsg = new ReturnMsg();
        try {
            Menu targetMenu = menuService.getMenu(menu.getId());
            UpdateUtil.copyNonNullProperties(menu, targetMenu);
            retMsg = ReturnMessageUtil.returnMessage(menuService.save(targetMenu) != null, null);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("修改菜单异常！\n", e);
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
            long count = menuService.delete(ids);
            retMsg = ReturnMessageUtil.returnMessage(count > 0, count);
        } catch (Exception e) {
            retMsg.setRetCode("9999");
            retMsg.setRetMsg("系统异常");
            log.error("删除菜单异常！\n", e);
        }
        return retMsg;
    }

    /**
     * 校验
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/service/validate", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject validate(@RequestParam String id) {
        JSONObject result = new JSONObject();
        Menu menu = menuService.getMenu(id);
        if (menu != null) {
            result.put("valid", false);
        } else {
            result.put("valid", true);
        }
        return result;
    }

    /**
     * 查询列表
     *
     * @return
     */
    @RequestMapping(value = "/service/menuList", method = RequestMethod.GET)
    @ResponseBody
    public List<Menu> menuList() {
        User user = ContextHolderEx.getUserInfo();
        List<Menu> allMenus = menuService.getUserMenus(user);
        return menuService.listMenus(allMenus);
    }
}
